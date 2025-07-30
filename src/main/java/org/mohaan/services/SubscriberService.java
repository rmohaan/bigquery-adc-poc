package org.mohaan.services;

import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.AckReplyConsumer;

@Service
public class SubscriberService {

    @Value("${spring.cloud.gcp.project-id}")
    private String projectId;

    @Value("${spring.pubsub.subscriber.subscription-name}")
    private String subscriptionId;

    private Subscriber subscriber;

    public void startSubscriber() {
        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);

        MessageReceiver receiver = (PubsubMessage message, AckReplyConsumer consumer) -> {
            System.out.println("Received message: " + message.getData().toStringUtf8());
            consumer.ack();
        };

        subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
        subscriber.startAsync().awaitRunning();
        System.out.println("Subscriber started for: " + subscriptionId);
    }

    public void stopSubscriber() {
        if (subscriber != null) {
            subscriber.stopAsync();
            System.out.println("Subscriber stopped.");
        }
    }
}