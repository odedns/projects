package io.iguaz.v3io.kafka;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * Created by oded on 19/10/16.
 */
public class TestInterceptor implements ProducerInterceptor<byte[],byte[]> {

    private boolean ackCalled = false;
    private boolean sendCalled = false;
    @Override
    public void configure(Map<String, ?> configs) {
        System.out.println("TestInterceptor.configure()");

    }

    @Override
    public ProducerRecord<byte[], byte[]> onSend(ProducerRecord<byte[], byte[]> record) {
        System.out.println("TestInterceptor.onSend()");
        this.sendCalled = true;
        return (record);
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        System.out.println("TestInterceptor.onAcknowledgement()");
        this.ackCalled = true;
    }

    @Override
    public void close() {
        System.out.println("TestInterceptor.close()");

    }

    public boolean isAckCalled() {
        return ackCalled;
    }

    public void setAckCalled(boolean ackCalled) {
        this.ackCalled = ackCalled;
    }

    public boolean isSendCalled() {
        return sendCalled;
    }

    public void setSendCalled(boolean sendCalled) {
        this.sendCalled = sendCalled;
    }
}
