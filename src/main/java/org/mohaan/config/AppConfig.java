package org.mohaan.config;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.ProjectTopicName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${spring.cloud.gcp.project-id}")
    private String projectId;

    @Value("${spring.pubsub.publisher.topic-name}")
    private String topicId;

    @Bean
    public Publisher publisher() throws Exception {
        ProjectTopicName topicName = ProjectTopicName.of(projectId, topicId);
        return Publisher.newBuilder(topicName).build();
    }
}
