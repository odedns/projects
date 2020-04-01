package test.io.iguaz.v3io.kafka;

import io.iguaz.v3io.kafka.SendHandler;
import io.iguaz.v3io.kafka.TestCallback;
import io.iguaz.v3io.kafka.TestInterceptor;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.internals.ProduceRequestResult;
import org.apache.kafka.clients.producer.internals.ProducerInterceptors;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.TopicPartition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by oded on 21/12/16.
 */
public class SendHandlerTest {

    private TestCallback callback;
    private TestInterceptor testInterceptor;
    private SendHandler handler;
    private TopicPartition topicPartition;
    ProduceRequestResult res;
    RecordMetadata recordMetadata;

    @Before
    public void setup()
    {
        callback = new TestCallback();
        testInterceptor = new TestInterceptor();
        List<ProducerInterceptor> list  = new LinkedList<ProducerInterceptor>();
        list.add(testInterceptor);
        ProducerInterceptors<?,?> interceptors = new ProducerInterceptors(list);
        handler = new SendHandler();
        handler.setCallback(callback);
        handler.setInterceptors(interceptors);
        topicPartition = new TopicPartition("/test",0);
        res = new ProduceRequestResult();
        recordMetadata = new RecordMetadata( topicPartition,0, 0,0,0,100,100 );


    }

    @Test
    public void testOnSuccess()
    {
        System.out.println("testOnSuccess");
        handler.onSuccess(topicPartition,recordMetadata,res,1);
        Assert.assertTrue(callback.isCompleted());
        Assert.assertTrue(testInterceptor.isAckCalled());
    }


    @Test
    public void testOnError()
    {
        System.out.println("testOnError");
        KafkaException exception = new KafkaException("Some error");
        handler.onError(topicPartition,recordMetadata,res,exception);
        Assert.assertTrue(callback.isCompleted());
        Assert.assertTrue(testInterceptor.isAckCalled());
    }

}
