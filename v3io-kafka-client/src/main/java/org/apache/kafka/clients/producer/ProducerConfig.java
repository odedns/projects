/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.apache.kafka.clients.producer;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.common.serialization.Serializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static org.apache.kafka.common.config.ConfigDef.Range.atLeast;
import static org.apache.kafka.common.config.ConfigDef.Range.between;
import static org.apache.kafka.common.config.ConfigDef.ValidString.in;

/**
 * Configuration for the Kafka Producer. Documentation for these configurations can be found in the <a
 * href="http://kafka.apache.org/documentation.html#producerconfigs">Kafka documentation</a>
 */
public class ProducerConfig extends AbstractConfig {

    /*
     * NOTE: DO NOT CHANGE EITHER CONFIG STRINGS OR THEIR JAVA VARIABLE NAMES AS THESE ARE PART OF THE PUBLIC API AND
     * CHANGE WILL BREAK USER CODE.
     */

    private static final ConfigDef CONFIG;


    /** <code>batch.size</code> */
    public static final String BATCH_SIZE_CONFIG = "batch.size";
    private static final String BATCH_SIZE_DOC = "The producer will attempt to batch records together into fewer requests whenever multiple records are being sent"
                                                 + " to the same partition. This helps performance on both the client and the server. This configuration controls the "
                                                 + "default batch size in bytes. "
                                                 + "<p>"
                                                 + "No attempt will be made to batch records larger than this size. "
                                                 + "<p>"
                                                 + "Requests sent to brokers will contain multiple batches, one for each partition with data available to be sent. "
                                                 + "<p>"
                                                 + "A small batch size will make batching less common and may reduce throughput (a batch size of zero will disable "
                                                 + "batching entirely). A very large batch size may use memory a bit more wastefully as we will always allocate a "
                                                 + "buffer of the specified batch size in anticipation of additional records.";

    /** <code>acks</code> */
    public static final String ACKS_CONFIG = "acks";
    private static final String ACKS_DOC = "The number of acknowledgments the producer requires the leader to have received before considering a request complete. This controls the "
                                           + " durability of records that are sent. The following settings are allowed: "
                                           + " <ul>"
                                           + " <li><code>acks=0</code> If set to zero then the producer will not wait for any acknowledgment from the"
                                           + " server at all. The record will be immediately added to the socket buffer and considered sent. No guarantee can be"
                                           + " made that the server has received the record in this case, and the <code>retries</code> configuration will not"
                                           + " take effect (as the client won't generally know of any failures). The offset given back for each record will"
                                           + " always be set to -1."
                                           + " <li><code>acks=1</code> This will mean the leader will write the record to its local log but will respond"
                                           + " without awaiting full acknowledgement from all followers. In this case should the leader fail immediately after"
                                           + " acknowledging the record but before the followers have replicated it then the record will be lost."
                                           + " <li><code>acks=all</code> This means the leader will wait for the full set of in-sync replicas to"
                                           + " acknowledge the record. This guarantees that the record will not be lost as long as at least one in-sync replica"
                                           + " remains alive. This is the strongest available guarantee. This is equivalent to the acks=-1 setting.";




    /** <code>linger.ms</code> */
    public static final String LINGER_MS_CONFIG = "linger.ms";
    private static final String LINGER_MS_DOC = "The producer groups together any records that arrive in between request transmissions into a single batched request. "
                                                + "Normally this occurs only under load when records arrive faster than they can be sent out. However in some circumstances the client may want to "
                                                + "reduce the number of requests even under moderate load. This setting accomplishes this by adding a small amount "
                                                + "of artificial delay&mdash;that is, rather than immediately sending out a record the producer will wait for up to "
                                                + "the given delay to allow other records to be sent so that the sends can be batched together. This can be thought "
                                                + "of as analogous to Nagle's algorithm in TCP. This setting gives the upper bound on the delay for batching: once "
                                                + "we get <code>" + BATCH_SIZE_CONFIG + "</code> worth of records for a partition it will be sent immediately regardless of this "
                                                + "setting, however if we have fewer than this many bytes accumulated for this partition we will 'linger' for the "
                                                + "specified time waiting for more records to show up. This setting defaults to 0 (i.e. no delay). Setting <code>" + LINGER_MS_CONFIG + "=5</code>, "
                                                + "for example, would have the effect of reducing the number of requests sent but would add up to 5ms of latency to records sent in the absense of load.";

    /** <code>client.id</code> */
    public static final String CLIENT_ID_CONFIG = CommonClientConfigs.CLIENT_ID_CONFIG;


    /** <code>max.request.size</code> */
    public static final String MAX_REQUEST_SIZE_CONFIG = "max.request.size";
    private static final String MAX_REQUEST_SIZE_DOC = "The maximum size of a request in bytes. This is also effectively a cap on the maximum record size. Note that the server "
                                                       + "has its own cap on record size which may be different from this. This setting will limit the number of record "
                                                       + "batches the producer will send in a single request to avoid sending huge requests.";


    /** <code>key.serializer</code> */
    public static final String KEY_SERIALIZER_CLASS_CONFIG = "key.serializer";
    public static final String KEY_SERIALIZER_CLASS_DOC = "Serializer class for key that implements the <code>Serializer</code> interface.";

    /** <code>value.serializer</code> */
    public static final String VALUE_SERIALIZER_CLASS_CONFIG = "value.serializer";
    public static final String VALUE_SERIALIZER_CLASS_DOC = "Serializer class for value that implements the <code>Serializer</code> interface.";


    /** <code>partitioner.class</code> */
    public static final String PARTITIONER_CLASS_CONFIG = "partitioner.class";
    private static final String PARTITIONER_CLASS_DOC = "Partitioner class that implements the <code>Partitioner</code> interface.";


    /** <code>interceptor.classes</code> */
    public static final String INTERCEPTOR_CLASSES_CONFIG = "interceptor.classes";
    public static final String INTERCEPTOR_CLASSES_DOC = "A list of classes to use as interceptors. "
                                                        + "Implementing the <code>ProducerInterceptor</code> interface allows you to intercept (and possibly mutate) the records "
                                                        + "received by the producer before they are published to the Kafka cluster. By default, there are no interceptors.";

    public static final String V3IO_CONTAINER_FACTORY ="v3io.container.fs.client.factory";
    public static final String V3IO_CONTAINER_FACTORY_DOC="internal v3io container factory";

    static {
        CONFIG = new ConfigDef()
                                .define(ACKS_CONFIG,
                                        Type.STRING,
                                        "1",
                                        in("all", "-1", "0", "1"),
                                        Importance.HIGH,
                                        ACKS_DOC)
                                .define(V3IO_CONTAINER_FACTORY, Type.STRING, null, Importance.HIGH, V3IO_CONTAINER_FACTORY_DOC)

                                .define(BATCH_SIZE_CONFIG, Type.INT, 16384, atLeast(0), Importance.MEDIUM, BATCH_SIZE_DOC)
                                .define(LINGER_MS_CONFIG, Type.LONG, 0, atLeast(0L), Importance.MEDIUM, LINGER_MS_DOC)
                                .define(CLIENT_ID_CONFIG, Type.STRING, "", Importance.MEDIUM, CommonClientConfigs.CLIENT_ID_DOC)
                                .define(MAX_REQUEST_SIZE_CONFIG,
                                        Type.INT,
                                        1 * 1024 * 1024,
                                        atLeast(0),
                                        Importance.MEDIUM,
                                        MAX_REQUEST_SIZE_DOC)
                                .define(KEY_SERIALIZER_CLASS_CONFIG,
                                        Type.CLASS,
                                        Importance.HIGH,
                                        KEY_SERIALIZER_CLASS_DOC)
                                .define(VALUE_SERIALIZER_CLASS_CONFIG,
                                        Type.CLASS,
                                        Importance.HIGH,
                                        VALUE_SERIALIZER_CLASS_DOC)
                                /* default is set to be a bit lower than the server default (10 min), to avoid both client and server closing connection at same time */

                                .define(PARTITIONER_CLASS_CONFIG,
                                        Type.CLASS,
                                        DefaultPartitioner.class,
                                        Importance.MEDIUM, PARTITIONER_CLASS_DOC)
                                .define(INTERCEPTOR_CLASSES_CONFIG,
                                        Type.LIST,
                                        null,
                                        Importance.LOW,
                                        INTERCEPTOR_CLASSES_DOC)
                                .withClientSslSupport()
                                .withClientSaslSupport();

    }

    public static Map<String, Object> addSerializerToConfig(Map<String, Object> configs,
                                                            Serializer<?> keySerializer, Serializer<?> valueSerializer) {
        Map<String, Object> newConfigs = new HashMap<String, Object>();
        newConfigs.putAll(configs);
        if (keySerializer != null)
            newConfigs.put(KEY_SERIALIZER_CLASS_CONFIG, keySerializer.getClass());
        if (valueSerializer != null)
            newConfigs.put(VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer.getClass());
        return newConfigs;
    }

    public static Properties addSerializerToConfig(Properties properties,
                                                   Serializer<?> keySerializer, Serializer<?> valueSerializer) {
        Properties newProperties = new Properties();
        newProperties.putAll(properties);
        if (keySerializer != null)
            newProperties.put(KEY_SERIALIZER_CLASS_CONFIG, keySerializer.getClass().getName());
        if (valueSerializer != null)
            newProperties.put(VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer.getClass().getName());
        return newProperties;
    }

    ProducerConfig(Map<?, ?> props) {
        super(CONFIG, props);
    }

    public static Set<String> configNames() {
        return CONFIG.names();
    }

    public static void main(String[] args) {
        System.out.println(CONFIG.toHtmlTable());
    }

}
