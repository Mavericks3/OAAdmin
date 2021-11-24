package org.trishanku.oa.admin.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.Customer;
import org.trishanku.oa.admin.entity.RM;
import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.notification.entity.NotificationEvent;
import org.trishanku.oa.admin.repository.CustomerRepository;
import org.trishanku.oa.admin.repository.RMRepository;
import org.trishanku.oa.admin.repository.RoleRepository;
import org.trishanku.oa.admin.repository.UserRepository;

import java.util.Optional;

@Service
@Slf4j
public class NotificationUtilImpl implements NotificationUtil {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    RMRepository rmRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public String getEventDefaultToAddresses(NotificationEvent notificationEvent, String transactionInformation) {
        String emailAddressList= "";
        switch (notificationEvent)
        {
            case SUPER_ADMIN_APPROVAL:
                emailAddressList = getApprovalEmailAddressList(notificationEvent,transactionInformation);
                break;
            case SUPER_ADMIN_CREATION:
                emailAddressList = getEmailAddressList("SUPER_ADMIN");
                break;
            case SUPER_ADMIN_DELETION:
                emailAddressList = getEmailAddressList("SUPER_ADMIN");
                break;
            case SUPER_ADMIN_MODIFICATION:
                emailAddressList = getEmailAddressList("SUPER_ADMIN");
                break;
            case BANK_ADMIN_APPROVAL:
                emailAddressList = getApprovalEmailAddressList(notificationEvent,transactionInformation);
                break;
            case BANK_ADMIN_CREATION:
                emailAddressList = getEmailAddressList("BANK_ADMIN_CHECKER");
                break;
            case BANK_ADMIN_DELETION:
                emailAddressList = getEmailAddressList("BANK_ADMIN_CHECKER");
                break;
            case BANK_ADMIN_MODIFICATION:
                emailAddressList = getEmailAddressList("BANK_ADMIN_CHECKER");
                break;
            case CUSTOMER_APPROVAL:
                emailAddressList = getApprovalEmailAddressList(notificationEvent,transactionInformation);
                break;
            case CUSTOMER_CREATION:
                emailAddressList = getEmailAddressList("BANK_ADMIN_CHECKER");
                break;
            case CUSTOMER_MODIFICATION:
                emailAddressList = getEmailAddressList("BANK_ADMIN_CHECKER");
                break;
            case CUSTOMER_DELETION:
                emailAddressList = getEmailAddressList("BANK_ADMIN_CHECKER");
                break;
            case CUSTOMER_ADMIN_APPROVAL:
                emailAddressList = getApprovalEmailAddressList(notificationEvent,transactionInformation);
                break;
            case CUSTOMER_ADMIN_CREATION:
                emailAddressList = getEmailAddressList("BANK_ADMIN_CHECKER");
                break;
            case CUSTOMER_ADMIN_DELETION:
                emailAddressList = getEmailAddressList("BANK_ADMIN_CHECKER");
                break;
            case CUSTOMER_ADMIN_MODIFICATION:
                emailAddressList = getEmailAddressList("BANK_ADMIN_CHECKER");
                break;
            case BANK_USER_CREATION:
                emailAddressList = getEmailAddressList("BANK_ADMIN_CHECKER");
                break;
            case BANK_USER_DELETION:
                emailAddressList = getEmailAddressList("BANK_ADMIN_CHECKER");
                break;
            case BANK_USER_MODIFICATION:
                emailAddressList = getEmailAddressList("BANK_ADMIN_CHECKER");
                break;
            case BANK_USER_APPROVAL:
                emailAddressList = getApprovalEmailAddressList(notificationEvent,transactionInformation);
                break;
            case CUSTOMER_USER_CREATION:
                emailAddressList = getEmailAddressList("CORPORATE_ADMIN_CHECKER");
                break;
            case CUSTOMER_USER_DELETION:
                emailAddressList = getEmailAddressList("CORPORATE_ADMIN_CHECKER");
                break;
            case CUSTOMER_USER_MODIFICATION:
                emailAddressList = getEmailAddressList("CORPORATE_ADMIN_CHECKER");
                break;
            case CUSTOMER_USER_APPROVAL:
                emailAddressList = getApprovalEmailAddressList(notificationEvent,transactionInformation);
                break;
            case RM_CREATION:
                emailAddressList = getEmailAddressList("BANK_ADMIN_CHECKER");
                break;
            case RM_DELETION:
                emailAddressList = getEmailAddressList("BANK_ADMIN_CHECKER");
                break;
            case RM_MODIFICATION:
                emailAddressList = getEmailAddressList("BANK_ADMIN_CHECKER");
                break;
            case RM_APPROVAL:
                emailAddressList = getApprovalEmailAddressList(notificationEvent,transactionInformation);
                break;


            default:

        }
        return emailAddressList;
    }

    @Override
    public String getEventDefaultCcAddresses(NotificationEvent notificationEvent) {
        return null;
    }

    @Override
    public String getEventDefaultBccAddresses(NotificationEvent notificationEvent) {
        return null;
    }

    private String getEmailAddressList(String roleName)
    {
        StringBuilder emailAddressList = new StringBuilder("");
        userRepository.findByRoles(roleRepository.findByName(roleName)).stream().forEach(user -> {
            emailAddressList.append(user.getEmailAddress()).append(";");
        });
        return emailAddressList.toString();
    }

    private String getApprovalEmailAddressList(NotificationEvent notificationEvent,String transactionInformation) {
        switch (notificationEvent) {
            case SUPER_ADMIN_APPROVAL:
                try {
                String userId = objectMapper.readTree(transactionInformation).findValue("userId").asText();
                Optional<User> user = userRepository.findByUserId(userId);
                if (user.isEmpty()) return "";
                else if (user.get().getModifiedUser().equalsIgnoreCase("")) return user.get().getCreatedUser();
                else return user.get().getModifiedUser();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                break;
            case BANK_ADMIN_APPROVAL:
                try {
                    String userId = objectMapper.readTree(transactionInformation).findValue("userId").asText();
                    Optional<User> user = userRepository.findByUserId(userId);
                    if (user.isEmpty()) return "";
                    else if (user.get().getModifiedUser().equalsIgnoreCase("")) return user.get().getCreatedUser();
                    else return user.get().getModifiedUser();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                break;
            case CUSTOMER_APPROVAL:
                try {
                    String customerId = objectMapper.readTree(transactionInformation).findValue("customerId").asText();
                    Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
                    if (customer.isEmpty()) return "";
                    else if (customer.get().getModifiedUser().equalsIgnoreCase("")) return customer.get().getCreatedUser();
                    else return customer.get().getModifiedUser();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                break;
            case RM_APPROVAL:
                try {
                    String rmId = objectMapper.readTree(transactionInformation).findValue("rmId").asText();
                    Optional<RM> rm = rmRepository.findByRmId(rmId);
                    if (rm.isEmpty()) return "";
                    else if (rm.get().getModifiedUser().equalsIgnoreCase("")) return rm.get().getCreatedUser();
                    else return rm.get().getModifiedUser();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                break;

        }
        return "";
    }
}
