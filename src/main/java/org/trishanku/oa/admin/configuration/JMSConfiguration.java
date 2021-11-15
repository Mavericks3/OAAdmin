package org.trishanku.oa.admin.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JMSConfiguration {

//    @Value("${oaadmin.notificaiton.queue}")
//    public static String notificationQueue;
      public static final String notificationQueue = "oaadmin.notificaiton.queue";

    @Bean
    public MessageConverter messageConverter() {

        MappingJackson2MessageConverter mappingJackson2MessageConverter= new MappingJackson2MessageConverter();
        mappingJackson2MessageConverter.setTargetType(MessageType.TEXT);
        mappingJackson2MessageConverter.setTypeIdPropertyName("_type");
        return mappingJackson2MessageConverter;

    }
}
