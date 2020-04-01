package test.io.iguaz.v3io.kafka;

import io.iguaz.v3io.kafka.TestInterceptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by oded on 27/12/16.
 */
public class TestInterceptorTest {

    TestInterceptor interceptor;
    @Before
    public void setup()
    {
        interceptor = new TestInterceptor();


    }

    @Test
    public void testOnAcknowledgement()
    {
        interceptor.onSend(null);
        Assert.assertTrue(interceptor.isSendCalled());
    }

    @Test
    public void testOnSend()
    {
        interceptor.onAcknowledgement(null,null);
        Assert.assertTrue(interceptor.isAckCalled());
    }
}
