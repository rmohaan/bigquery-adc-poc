package org.mohaan.controllers;

import org.mohaan.services.PublisherService;
import org.mohaan.services.SubscriberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pubsub")
public class PubSubController {

    private final PublisherService publisherService;
    private final SubscriberService subscriberService;

    public PubSubController(PublisherService publisherService, SubscriberService subscriberService) {
        this.publisherService = publisherService;
        this.subscriberService = subscriberService;
    }

    @GetMapping
    public String publishMessage() throws Exception {
        publisherService.publishMessage("Hello, World!");
        return "Message published successfully!";
    }

    @GetMapping("/startSubscription")
    public void startSubscription() {
        subscriberService.startSubscriber();
    }

    @GetMapping("/stopSubscription")
    public void stopSubscription() {
        subscriberService.stopSubscriber();
    }
}
