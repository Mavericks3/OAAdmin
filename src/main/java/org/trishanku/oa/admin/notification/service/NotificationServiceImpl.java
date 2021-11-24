package org.trishanku.oa.admin.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.trishanku.oa.admin.entity.Base;
import org.trishanku.oa.admin.jwtauthentication.configuration.service.JWTUtil;
import org.trishanku.oa.admin.model.EmailDTO;
import org.trishanku.oa.admin.notification.entity.Notification;
import org.trishanku.oa.admin.notification.entity.NotificationEvent;
import org.trishanku.oa.admin.notification.entity.NotificationStatusEnum;
import org.trishanku.oa.admin.notification.repository.NotificationRepository;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
     NotificationRepository notificationRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    TemplateUtil templateUtil;

    @Override
    public void addMakerEvent(Object object, NotificationEvent notificationEvent) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeySpecException {


        Notification notification = Notification.builder()
                        .notificationStatus(NotificationStatusEnum.INITIATED)
                .uuid(UUID.randomUUID())
                .transactionInformation(objectMapper.writeValueAsString(object))
                .notificationEvent(notificationEvent)
                .build();
        Base baseDetails = objectMapper.readValue(objectMapper.writeValueAsString(object),Base.class);
        notification.setCreationDetails(jwtUtil.extractUsernameFromRequest());
      notificationRepository.save(notification);

    }

    @Override
    public void addModificationEvent(Object object, NotificationEvent notificationEvent) throws JsonProcessingException {

        Notification notification = Notification.builder()
                .notificationStatus(NotificationStatusEnum.INITIATED)
                .uuid(UUID.randomUUID())
                .transactionInformation(objectMapper.writeValueAsString(object))
                .notificationEvent(notificationEvent)
                .build();
        Base baseDetails = objectMapper.readValue(objectMapper.writeValueAsString(object),Base.class);
        notification.setModificationDetails(jwtUtil.extractUsernameFromRequest());

        notificationRepository.save(notification);

    }

    @Override
    public void addCheckerEvent(Object object, NotificationEvent notificationEvent) throws JsonProcessingException {


        Notification notification = Notification.builder()
                .notificationStatus(NotificationStatusEnum.INITIATED)
                .uuid(UUID.randomUUID())
                .transactionInformation(objectMapper.writeValueAsString(object))
                .notificationEvent(notificationEvent)
                .build();
        Base baseDetails = objectMapper.readValue(objectMapper.writeValueAsString(object),Base.class);
        notification.setAuthorizationDetails(jwtUtil.extractUsernameFromRequest());

        notificationRepository.save(notification);

    }

    @Override
    public void addDeleteEvent(Object object, NotificationEvent notificationEvent) throws JsonProcessingException {

        Notification notification = Notification.builder()
                .notificationStatus(NotificationStatusEnum.INITIATED)
                .uuid(UUID.randomUUID())
                .transactionInformation(objectMapper.writeValueAsString(object))
                .notificationEvent(notificationEvent)
                .build();
        Base baseDetails = objectMapper.readValue(objectMapper.writeValueAsString(object),Base.class);
        notification.setModificationDetails(jwtUtil.extractUsernameFromRequest());

        notificationRepository.save(notification);

    }


    public boolean prepareForSend(Notification notification) {


            EmailDTO emailDTO = templateUtil.getProcessedInfo(notification);
            notification.setTo(emailDTO.getToAddress());
            notification.setCc(emailDTO.getCcAddress());
            notification.setBcc(emailDTO.getBccAddress());
            notification.setContent(emailDTO.getContent());
            notification.setSubject(emailDTO.getSubject());
            notification.setNotificationStatus(NotificationStatusEnum.READY_TO_BE_SENT);
             notificationRepository.save(notification);


        return true;
    }


    @Override
    public boolean updateSentStatus(Notification notification) {

        notification.setNotificationStatus(NotificationStatusEnum.SENT);
        notificationRepository.save(notification);


        return true;
    }


}
