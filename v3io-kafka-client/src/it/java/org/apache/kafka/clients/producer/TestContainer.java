package org.apache.kafka.clients.producer;

import io.iguaz.v3io.container.Container;
import io.iguaz.v3io.container.ContainerFactory;
import io.iguaz.v3io.daemon.client.api.consts.V3ioResultCode;
import io.iguaz.v3io.fs.client.HashMethod;
import io.iguaz.v3io.streaming.StreamingOperations;
import io.iguaz.v3io.streaming.StreamingOperationsFactory;
import io.iguaz.v3io.streaming.client.api.*;
import io.iguaz.v3io.streaming.client.api.ProducerRecord;

import java.util.List;
import java.util.Properties;

/**
 * Created by oded on 05/10/16.
 */
public class TestContainer {

    public static void main(String args[]) {


        try {
           // TopicPartition tp = new TopicPartition("aaa",(short)0);
            //StreamingContainer container = StreamingContainerFactory.createStreamingContainer(new Properties());
            Properties properties = new Properties();
            String clientFactory = System.getProperty("v3io.container.fs.client.factory",
                "io.iguaz.v3io.fs.client.SimulatorFSClientFactory");
            Properties props = new Properties();
            props.setProperty("v3io.container.fs.client.factory", clientFactory);

            Container container = ContainerFactory.create(props);
            StreamingOperations operations = StreamingOperationsFactory.create(container,props);

            String topic ="/testTopic";
            String keyStr="some key";
            String valueStr = "Some value";
            byte[] key = keyStr.getBytes();
            byte[] value = valueStr.getBytes();
            operations.createTopic(topic,(short) 1,2, HashMethod.MD5);


            short partitionId=0;
            Long ts = new Long(System.currentTimeMillis());


            io.iguaz.v3io.streaming.client.api.ProducerRecord record = new ProducerRecord(topic,partitionId,ts,0L,key,value);
                operations.putRecord(record, new V3IOPutRecordCallback() {
                    @Override
                    public void onSuccess(long sequenceId, short partitionId) {
                        System.out.println("onsuccess: " );

                    }

                    @Override
                    public void onFailure(V3ioResultCode.Errors errCode) {
                        System.out.println("onError: " + errCode);


                    }
                });



            Topic t = operations.getTopic("/testTopic");
            List<Partition> partitions = t.getPartitions();
            for(Partition p : partitions) {
                System.out.println("p=" + p.getId());
            }



            System.out.printf("Wrote record...");
            container.close();
        } catch(Exception e) {
            e.printStackTrace();
        }


    }
}
