package org.apache.kafka.clients.producer;

import io.iguaz.v3io.container.*;
import io.iguaz.v3io.daemon.client.api.consts.V3ioResultCode;
import io.iguaz.v3io.fs.client.HashMethod;
import io.iguaz.v3io.kafka.KafkaPropertiesAdapter;
import io.iguaz.v3io.kafka.OperationUtils;
import io.iguaz.v3io.kafka.SendHandler;
import io.iguaz.v3io.streaming.StreamingOperations;
import io.iguaz.v3io.streaming.StreamingOperationsFactory;
import io.iguaz.v3io.streaming.client.api.Topic;
import io.iguaz.v3io.streaming.client.api.V3IOPutRecordCallback;
import org.apache.kafka.clients.producer.internals.FutureRecordMetadata;
import org.apache.kafka.clients.producer.internals.ProduceRequestResult;
import org.apache.kafka.clients.producer.internals.ProducerInterceptors;
import org.apache.kafka.common.*;
import org.apache.kafka.common.record.CompressionType;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

/**
 * Created by oded on 05/10/16.
 */
public class KafkaProducer<K, V> implements Producer<K, V> {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    private String clientId;
    private final int maxRequestSize;
    private Serializer<K> keySerializer;
    private Serializer<V> valueSerializer;
    private StreamingOperations operations;
    private Container container;
    private Properties containerConfig;
    private Partitioner partitioner;
    private static final AtomicInteger PRODUCER_CLIENT_ID_SEQUENCE = new AtomicInteger(1);
    private ProducerInterceptors<K, V> interceptors;
    private ProducerConfig producerConfig;
    private Cluster cluster;
    private OperationUtils operationUtils;
    private String acks;


    /**
     * A producer is instantiated by providing a set of key-value pairs as configuration. Valid configuration strings
     * are documented <a href="http://kafka.apache.org/documentation.html#producerconfigs">here</a>. Values can be
     * either strings or Objects of the appropriate type (for example a numeric configuration would accept either the
     * string "42" or the integer 42).
     * @param configs   The producer configs
     *
     */
    public KafkaProducer(Map<String, Object> configs) {
        this(new ProducerConfig(configs), null, null);
    }

    /**
     * A producer is instantiated by providing a set of key-value pairs as configuration, a key and a value {@link Serializer}.
     * Valid configuration strings are documented <a href="http://kafka.apache.org/documentation.html#producerconfigs">here</a>.
     * Values can be either strings or Objects of the appropriate type (for example a numeric configuration would accept
     * either the string "42" or the integer 42).
     * @param configs   The producer configs
     * @param keySerializer  The serializer for key that implements {@link Serializer}. The configure() method won't be
     *                       called in the producer when the serializer is passed in directly.
     * @param valueSerializer  The serializer for value that implements {@link Serializer}. The configure() method won't
     *                         be called in the producer when the serializer is passed in directly.
     */
    public KafkaProducer(Map<String, Object> configs, Serializer<K> keySerializer, Serializer<V> valueSerializer) {
        this(new ProducerConfig(ProducerConfig.addSerializerToConfig(configs, keySerializer, valueSerializer)),
                keySerializer, valueSerializer);
    }

    /**
     * A producer is instantiated by providing a set of key-value pairs as configuration. Valid configuration strings
     * are documented <a href="http://kafka.apache.org/documentation.html#producerconfigs">here</a>.
     * @param properties   The producer configs
     */
    public KafkaProducer(Properties properties) {
        this(new ProducerConfig(properties), null, null);
    }

    /**
     * A producer is instantiated by providing a set of key-value pairs as configuration, a key and a value {@link Serializer}.
     * Valid configuration strings are documented <a href="http://kafka.apache.org/documentation.html#producerconfigs">here</a>.
     * @param properties   The producer configs
     * @param keySerializer  The serializer for key that implements {@link Serializer}. The configure() method won't be
     *                       called in the producer when the serializer is passed in directly.
     * @param valueSerializer  The serializer for value that implements {@link Serializer}. The configure() method won't
     *                         be called in the producer when the serializer is passed in directly.
     */
    public KafkaProducer(Properties properties, Serializer<K> keySerializer, Serializer<V> valueSerializer) {
       this(new ProducerConfig(ProducerConfig.addSerializerToConfig(properties, keySerializer, valueSerializer)),
                keySerializer, valueSerializer);
    }

    private KafkaProducer(ProducerConfig config, Serializer<K> keySerializer, Serializer<V> valueSerializer)    {
        Map<String, Object> userProvidedConfigs = config.originals();
        this.producerConfig = config;
        clientId = config.getString(ProducerConfig.CLIENT_ID_CONFIG);
        if (clientId.length() <= 0) {
            clientId = "producer-" + PRODUCER_CLIENT_ID_SEQUENCE.getAndIncrement();
        }
        if (keySerializer == null) {
            this.keySerializer = config.getConfiguredInstance(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                    Serializer.class);
            this.keySerializer.configure(config.originals(), true);
        } else {
            config.ignore(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG);
            this.keySerializer = keySerializer;
        }
        if (valueSerializer == null) {
            this.valueSerializer = config.getConfiguredInstance(VALUE_SERIALIZER_CLASS_CONFIG,
                    Serializer.class);
            this.valueSerializer.configure(config.originals(), false);
        } else {
            config.ignore(VALUE_SERIALIZER_CLASS_CONFIG);
            this.valueSerializer = valueSerializer;
        }

        this.acks = config.getString(ProducerConfig.ACKS_CONFIG);

        // load interceptors and make sure they get clientId
        userProvidedConfigs.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        List<ProducerInterceptor<K, V>> interceptorList = (List) (new ProducerConfig(userProvidedConfigs)).getConfiguredInstances(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,
                ProducerInterceptor.class);
        this.interceptors = interceptorList.isEmpty() ? null : new ProducerInterceptors<>(interceptorList);
        this.partitioner = config.getConfiguredInstance(ProducerConfig.PARTITIONER_CLASS_CONFIG, Partitioner.class);

        this.maxRequestSize = config.getInt(ProducerConfig.MAX_REQUEST_SIZE_CONFIG);

        /**
         * v3io objects.
         */
        try {
            this.containerConfig  = KafkaPropertiesAdapter.convert(this.producerConfig);
            this.container = ContainerFactory.create(containerConfig);
            this.operations = StreamingOperationsFactory.create(this.container,this.containerConfig);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot initializa container ...",e);
        }
        this.operationUtils = new OperationUtils(this.operations);
        this.cluster = new Cluster(this.operationUtils);




    }

    @Override
    public Future<RecordMetadata> send(ProducerRecord<K, V> record)  {

        return(send(record,null));
    }

    /**
     * create a message in a ByteBuffer.
     * @param key the message key.
     * @param value the message value.
     * @return ByteBuffer.
     */
    private ByteBuffer createMsg(byte key[],byte value[]) {

        int keyLen = key.length;
        int valueLen = value.length;
        int capacity = keyLen + valueLen + 8;
        ByteBuffer bf = ByteBuffer.allocateDirect(capacity);
        bf.putInt(keyLen);
        bf.putInt(valueLen);
        bf.put(key);
        bf.put(value);
        return(bf);
    }

    @Override
    public Future<RecordMetadata> send(ProducerRecord<K, V> record, final Callback callback)    {

        final String topic = record.topic();

        // call interceptors list.
        if(this.interceptors != null) {
            record = this.interceptors.onSend(record);
        }
        try {
            Topic t = operations.getTopic(topic);

        } catch (IOException e) {
            log.debug("creating topic...");
            try {
                operations.createTopic(topic, (short) 1, 2, HashMethod.MD5);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            //e.printStackTrace();
        }


        final byte key[] = this.keySerializer.serialize(topic,record.key());
        final byte value[] = this.valueSerializer.serialize(topic,record.value());
        final int partitionId = partitioner.partition(record.topic(),record.key(),key,record.value(),value,this.cluster);
        final long ts = System.currentTimeMillis();
        long nanoTs = System.nanoTime();
        final ProduceRequestResult res = new ProduceRequestResult();
        final FutureRecordMetadata futureRecordMetadata = new FutureRecordMetadata(res,1,ts,0,key.length,value.length);


        final org.apache.kafka.common.TopicPartition topicPartition = new org.apache.kafka.common.TopicPartition(topic, partitionId);


        io.iguaz.v3io.streaming.client.api.ProducerRecord prec =
            new io.iguaz.v3io.streaming.client.api.ProducerRecord(topic,(short)partitionId,new Long(ts),key,value);

        final SendHandler handler = new SendHandler(this.interceptors,callback);
        try {
            if (acks.equalsIgnoreCase("all")) {
                log.info("Using callback since ack required.");
                this.operations.putRecord(prec, new V3IOPutRecordCallback() {
                    @Override
                    public void onSuccess(long sequenceId, short partitionId) {
                        RecordMetadata recordMetadata = new RecordMetadata(topicPartition, 0, sequenceId, ts, 0, key.length, value.length);
                        handler.onSuccess(topicPartition, recordMetadata, res, sequenceId);
                    }

                    @Override
                    public void onFailure(V3ioResultCode.Errors errCode) {
                        RecordMetadata recordMetadata = new RecordMetadata(topicPartition, 0, 0, ts, 0, key.length, value.length);
                        String msg = errCode.toString();
                        KafkaException exception = new KafkaException(msg);
                        handler.onError(topicPartition, recordMetadata, res, exception);

                    }
                });
            } else {
                log.info("Not using callback since not ack it required.");
                RecordMetadata recordMetadata = new RecordMetadata(topicPartition, 0, 0, ts, 0, key.length, value.length);
                this.operations.putRecord(prec, null);
                handler.onSuccess(topicPartition, recordMetadata, res, 0);
            }
        } catch (Exception e) {
            RecordMetadata recordMetadata = new RecordMetadata(topicPartition, 0, 0, ts, 0, key.length, value.length);
            KafkaException exception = new KafkaException(e);
            handler.onError(topicPartition, recordMetadata, res, exception);

        }
        return(futureRecordMetadata);
    }




    @Override
    public void flush() {

    }

    @Override
    public List<PartitionInfo> partitionsFor(String topic) {

        List<PartitionInfo> partitions = null;
        try {
            partitions =  operationUtils.getPartitionsForTopic(topic);
        } catch (IOException e) {
            log.error("Error getting partitions for topic: " + topic,e);
            partitions = null;
        }
        return(partitions);
    }

    @Override
    public Map<MetricName, ? extends Metric> metrics() {
        throw new UnsupportedOperationException("Method metrics not supported.");
    }

    @Override
    public void close() {

        try {
            container.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error closing container",e);
        }

    }

    @Override
    public void close(long timeout, TimeUnit unit) {
        close();

    }


}
