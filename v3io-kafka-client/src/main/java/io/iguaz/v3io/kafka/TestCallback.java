package io.iguaz.v3io.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * Created by oded on 20/12/16.
 */
public class TestCallback implements Callback {

    private boolean completed = false;
    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        System.out.println("OnCompletion: " + metadata.toString());
        if(exception != null) {
            System.out.println("got Exception: " + exception.getMessage());
        }
        this.completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
