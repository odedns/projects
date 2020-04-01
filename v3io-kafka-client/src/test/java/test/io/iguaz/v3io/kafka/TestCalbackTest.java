package test.io.iguaz.v3io.kafka;

import io.iguaz.v3io.kafka.TestCallback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by oded on 27/12/16.
 */
public class TestCalbackTest {

    @Test
    public void testOnComplete()
    {
        TestCallback callback = new TestCallback();
        callback.onCompletion(new RecordMetadata(new TopicPartition("testTopic",1),0L,0L,0,0,0,0),null);
        Assert.assertTrue(callback.isCompleted());
    }
}
