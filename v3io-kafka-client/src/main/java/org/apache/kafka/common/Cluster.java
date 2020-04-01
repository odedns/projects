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
package org.apache.kafka.common;

import io.iguaz.v3io.kafka.OperationUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A representation of a subset of the nodes, topics, and partitions in the Kafka cluster.
 */
public final class Cluster {

    private Map<String, List<PartitionInfo>> partitionsByTopic;

    private OperationUtils operationUtils;

    public Cluster()
    {
        this.setPartitionsByTopic(new HashMap<String,List<PartitionInfo>>());

    }

    public static Cluster empty() {
        return new Cluster();
    }

    public Cluster(OperationUtils operationUtils)
    {

        this.setOperationUtils(operationUtils);
        this.setPartitionsByTopic(new HashMap<String,List<PartitionInfo>>());

    }

    public List<PartitionInfo> partitionsForTopic(String topic)
    {
        List<PartitionInfo> partitionInfoList = null;
        if(getOperationUtils() == null) {
            return(partitionInfoList);
        }
        try {
            partitionInfoList = getOperationUtils().getPartitionsForTopic(topic);
            for(PartitionInfo pi : partitionInfoList) {
                this.getPartitionsByTopic().put(topic, Collections.unmodifiableList(partitionInfoList));
            }
        } catch(Exception e) {
            e.printStackTrace();
            partitionInfoList = null;
        }

        return(partitionInfoList);
    }

    public List<PartitionInfo> availablePartitionsForTopic(String topic)
    {
        return(partitionsForTopic(topic));
    }

    public Map<String, List<PartitionInfo>> getPartitionsByTopic() {
        return partitionsByTopic;
    }

    public void setPartitionsByTopic(Map<String, List<PartitionInfo>> partitionsByTopic) {
        this.partitionsByTopic = partitionsByTopic;
    }

    public OperationUtils getOperationUtils() {
        return operationUtils;
    }

    public void setOperationUtils(OperationUtils operationUtils) {
        this.operationUtils = operationUtils;
    }
}
