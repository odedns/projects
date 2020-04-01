package io.iguaz.v3io.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by oded on 14/12/16.
 */
public class KafkaPropertiesAdapter {
    private static final String DEFAULT_MAX_BYTES_IN_BATCH = "default-max-bytes-in-batch";
    private static final String DEFAULT_MAX_WAIT_MS_FOR_BATCH ="default-max-wait-ms-for-batch";
    private static final String V3IO_CONTAINER_FACTORY ="v3io.container.fs.client.factory";

    public static Properties convert(ProducerConfig kafkaConfig) {

        int batchSize = kafkaConfig.getInt(ProducerConfig.BATCH_SIZE_CONFIG);
        long lingerMs = kafkaConfig.getLong(ProducerConfig.LINGER_MS_CONFIG);
        String containerFactory = kafkaConfig.getString(V3IO_CONTAINER_FACTORY);

        Properties containerProps = new Properties();
        containerProps.put(DEFAULT_MAX_BYTES_IN_BATCH,batchSize);
        containerProps.put(DEFAULT_MAX_WAIT_MS_FOR_BATCH,lingerMs);

        if(containerFactory != null) {
            containerProps.put(V3IO_CONTAINER_FACTORY, kafkaConfig.getString(V3IO_CONTAINER_FACTORY));
        }

        return(containerProps);
    }
}
