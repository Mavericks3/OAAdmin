package org.trishanku.oa.admin.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.trishanku.oa.admin.model.EmailDTO;
import org.trishanku.oa.admin.notification.entity.Notification;
import org.trishanku.oa.admin.notification.entity.NotificationEvent;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TemplateUtilImpl implements TemplateUtil {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    NotificationUtil notificationUtil;

    @Override
    public EmailDTO getProcessedInfo(Notification notification) {
        EmailDTO emailDTO = new EmailDTO();
        try {

            log.info("Notification Event is " + notification.getNotificationEvent());
            String templateFileContent = FileUtils.readFileToString(ResourceUtils.getFile("classpath:EmailTemplates\\" + notification.getNotificationEvent() + ".xml"), StandardCharsets.UTF_8);
            log.info("template file content is " + templateFileContent);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(ResourceUtils.getFile("classpath:EmailTemplates\\" + notification.getNotificationEvent() + ".xml"));
            log.info("to address values is " + document.getElementsByTagName("toAddress").item(0).getTextContent());
            emailDTO.setToAddress(replaceHolders(new StringBuilder(document.getElementsByTagName("toAddress").item(0).getTextContent()), notification.getTransactionInformation(),notification.getNotificationEvent()));
            emailDTO.setCcAddress(replaceHolders(new StringBuilder(document.getElementsByTagName("ccAddress").item(0).getTextContent()), notification.getTransactionInformation(),notification.getNotificationEvent()));
            emailDTO.setBccAddress(replaceHolders(new StringBuilder(document.getElementsByTagName("bccAddress").item(0).getTextContent()), notification.getTransactionInformation(),notification.getNotificationEvent()));
            emailDTO.setSubject(replaceHolders(new StringBuilder(document.getElementsByTagName("mailSubject").item(0).getTextContent()), notification.getTransactionInformation(),notification.getNotificationEvent()));
            emailDTO.setContent(replaceHolders(new StringBuilder(document.getElementsByTagName("mailContent").item(0).getTextContent()), notification.getTransactionInformation(),notification.getNotificationEvent()));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return emailDTO;
    }

    private String replaceHolders(StringBuilder templateText, String transactionInformation, NotificationEvent notificationEvent) {


        log.info("Initial value is ==>" + templateText);

        List<String> placeHolders = getAllMatchesAsList(templateText.toString());


        placeHolders.stream().forEach((item) -> {

            try {
                String fieldFromTransaction = item.substring(2, item.length() - 2);

                String valueFromTransaction = "";
                if(fieldFromTransaction.equalsIgnoreCase("DEFAULT"))
                {
                    //implementation to get email addresses as per the event

                    valueFromTransaction = notificationUtil.getEventDefaultToAddresses(notificationEvent,transactionInformation);
                }
                else if (objectMapper.readTree(transactionInformation).findValue(fieldFromTransaction) != null)
                    valueFromTransaction = objectMapper.readTree(transactionInformation).findValue(fieldFromTransaction).asText();
                log.info(item + " will be replaced with " + valueFromTransaction);
                if (valueFromTransaction != null)

                    replaceAll(templateText, item, valueFromTransaction);
            } catch (JsonProcessingException e) {

                e.printStackTrace();
            }
        });


        log.info(" After processing value is ==> " + templateText);

        return templateText.toString();
    }

    public void replaceAll(StringBuilder sb, String find, String replace) {

        //compile pattern from find string
        Pattern p = Pattern.compile(find);

        //create new Matcher from StringBuilder object
        Matcher matcher = p.matcher(sb);

        //index of StringBuilder from where search should begin
        int startIndex = 0;

        while (matcher.find(startIndex)) {

            sb.replace(matcher.start(), matcher.end(), replace);

            //set next start index as start of the last match + length of replacement
            startIndex = matcher.start() + replace.length();
        }
    }

    //all regex matches to a List
    private static List<String> getAllMatchesAsList(String str) {
        //pattern to match <%anystring%>
        Pattern pattern = Pattern.compile("<%[a-zA-Z_]*%>");
        Matcher matcher = pattern.matcher(str);
        return matcher.results().map(MatchResult::group).collect(Collectors.toList());
    }
}
