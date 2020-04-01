package io.iguaz.v3io.kafka;

import io.iguaz.v3io.daemon.client.api.consts.V3ioResultCode;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.internals.ProduceRequestResult;
import org.apache.kafka.clients.producer.internals.ProducerInterceptors;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.TopicPartition;

/**
 * Created by oded on 21/12/16.
 */
public class SendHandler {

    private Callback callback;
    private ProducerInterceptors<?,?> interceptors;

    public SendHandler(ProducerInterceptors<?,?> interceptors, Callback callback)
    {
        this.callback = callback;
        this.interceptors = interceptors;
    }

    public SendHandler()
    {

    }

    public void onSuccess(TopicPartition topicPartition, RecordMetadata recordMetadata, ProduceRequestResult res,long sequenceId)
    {
        if(interceptors != null) {
            interceptors.onAcknowledgement( recordMetadata,null);
        }
        if(callback != null) {
            callback.onCompletion(recordMetadata, null);
        }
        res.done(topicPartition, sequenceId, null);

    }

    public void onError(TopicPartition topicPartition, RecordMetadata recordMetadata, ProduceRequestResult res,KafkaException exception)
    {

        if(interceptors != null) {
            interceptors.onSendError(null,topicPartition,exception);
        }
        if(callback != null) {
            callback.onCompletion(recordMetadata, exception);
        }
        res.error();


    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public ProducerInterceptors<?, ?> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(ProducerInterceptors<?, ?> interceptors) {
        this.interceptors = interceptors;
    }
}
