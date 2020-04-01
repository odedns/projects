package io.iguaz.v3io.kafka;


import io.iguaz.v3io.streaming.StreamingOperations;
import io.iguaz.v3io.streaming.client.api.Partition;
import io.iguaz.v3io.streaming.client.api.Topic;
import org.apache.kafka.common.PartitionInfo;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by oded on 05/10/16.
 */
public class OperationUtils {

    private StreamingOperations operations;

    public OperationUtils(StreamingOperations operations)
    {
        this.operations = operations;
    }


    public List<PartitionInfo> getPartitionsForTopic(String topicName) throws IOException {
        LinkedList<PartitionInfo> piList = new LinkedList<PartitionInfo>();
        Topic topic = operations.getTopic(topicName);
        List<Partition> partitions = topic.getPartitions();
        for(Partition p : partitions) {
            PartitionInfo pi = new PartitionInfo(topicName,p.getId(),null,null,null);
            piList.add(pi);
        }

        return(piList);

    }



}
