package org.mohaan.services;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class PublisherService {

    private final Publisher publisher;

    public PublisherService(Publisher publisher) {
        this.publisher = publisher;
    }

    public void publishMessage(String message) throws Exception {
        try {
            ByteString data = ByteString.copyFromUtf8(message);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
            var result = publisher.publish(pubsubMessage).get();
            System.out.println("Message published with ID: " + result);
        } catch (ExecutionException e) {
            System.err.println("Error publishing message: " + e.getMessage());
        } finally {
            if (publisher != null) {
                publisher.shutdown();
            }
        }
    }
}
