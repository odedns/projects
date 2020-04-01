package test.org.apache.kafka.clients.producer;

import io.iguaz.v3io.kafka.TestCallback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by oded on 13/10/16.
 */
@RunWith(Parameterized.class)
public class ProducerTest {

    private String acks;
    private int batchSize;
    private long linger;

    KafkaProducer<String, String> producer = null;


    public ProducerTest(String acks, int batchSize, long linger)
    {
        this.batchSize = batchSize;
        this.acks = acks;
        this.linger = linger;
        System.out.println("acks = " + acks + " batchSize =" + batchSize + " linger =" + linger);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{"all",16384,1000}, {"0",200,100},{"0",10000,0}});
    }


    @Before
    public void setUp()
    {


        Properties props = new Properties();
        //Set acknowledgements for producer requests.
        props.put("acks", this.acks);
        //Specify buffer size in config
        props.put("batch.size", this.batchSize);

        //Reduce the no of requests less than 0
        props.put("linger.ms", this.linger);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("interceptor.classes","io.iguaz.v3io.kafka.TestInterceptor");
        props.put("v3io.container.fs.client.factory",
            "io.iguaz.v3io.fs.client.SimulatorFSClientFactory");
        this.producer = new KafkaProducer<String, String>(props);
    }


    @Test
    public void testSend() throws ExecutionException, InterruptedException {
        Future<RecordMetadata> future = this.producer.send(new ProducerRecord<String, String>("/testTopic","key","value1"));
        RecordMetadata meta = future.get();
        Assert.assertTrue(meta.offset() > 0);

    }

    @Test
    public void testCallbackSend() throws ExecutionException, InterruptedException {
        TestCallback callback = new TestCallback();
        Future<RecordMetadata> future = this.producer.send(new ProducerRecord<String, String>("/testTopic","key","value1"),
                                                            callback);
        RecordMetadata meta = future.get();
        Assert.assertTrue(callback.isCompleted());
    }

    @Test
    public void testMultipleSend() throws ExecutionException, InterruptedException {
        int size = 10;
        for(int i=0; i <size; ++i) {
            Future<RecordMetadata> future = this.producer.send(new ProducerRecord<String, String>("/testTopic", "key"+i, "value1" +i));
            RecordMetadata meta = future.get();
            Assert.assertTrue(meta.offset() > 0);

        }
    }

    @Test
    public void testLargeSend() throws ExecutionException, InterruptedException {
        byte value[] = createMessage(2000);
        Future<RecordMetadata> future = this.producer.send(new ProducerRecord<String, String>("/testTopic","key",new String(value)));
        RecordMetadata meta = future.get();
        Assert.assertTrue(meta.offset() > 0);

    }

    @After
    public void tearDown()
    {
        this.producer.close();
    }


    private byte[] createMessage(int size) {

        byte msg[] = new byte[size];
        for(int i =0; i < size; ++i) {
            msg[i] = 'A';
        }
        return(msg);
    }

}
