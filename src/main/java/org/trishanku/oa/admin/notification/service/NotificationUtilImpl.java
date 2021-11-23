package org.trishanku.oa.admin.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.notification.entity.NotificationEvent;
import org.trishanku.oa.admin.repository.RoleRepository;
import org.trishanku.oa.admin.repository.UserRepository;

import java.util.Optional;

@Service
@Slf4j
public class NotificationUtilImpl implements NotificationUtil {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public String getEventDefaultToAddresses(NotificationEvent notificationEvent, String userId) {
        String emailAddressList= "";
        switch (notificationEvent)
        {
            case SUPER_ADMIN_APPROVAL:
                emailAddressList = getApprovalEmailAddressList("SUPER_ADMIN",userId);
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
            case BANK_ADMIN_CREATION:
                log.info("get BANK admin mail id's");
                emailAddressList = getEmailAddressList("BANK_ADMIN");
                break;
            case BANK_ADMIN_DELETION:
                log.info("get BANK admin mail id's");
                emailAddressList = getEmailAddressList("BANK_ADMIN");
                break;
            case BANK_ADMIN_MODIFICATION:
                log.info("get BANK admin mail id's");
                emailAddressList = getEmailAddressList("BANK_ADMIN");
                break;
            case CUSTOMER_CREATION:
                log.info("get BANK admin mail id's");
                emailAddressList = getEmailAddressList("BANK_ADMIN");
                break;
            case CUSTOMER_MODIFICATION:
                log.info("get BANK admin mail id's");
                emailAddressList = getEmailAddressList("BANK_ADMIN");
                break;
            case CUSTOMER_DELETION:
                log.info("get BANK admin mail id's");
                emailAddressList = getEmailAddressList("BANK_ADMIN");
                break;
            case CUSTOMER_ADMIN_CREATION:
                log.info("get BANK admin mail id's");
                emailAddressList = getEmailAddressList("BANK_ADMIN");
                break;
            case CUSTOMER_ADMIN_DELETION:
                log.info("get BANK admin mail id's");
                emailAddressList = getEmailAddressList("BANK_ADMIN");
                break;
            case CUSTOMER_ADMIN_MODIFICATION:
                log.info("get BANK admin mail id's");
                emailAddressList = getEmailAddressList("BANK_ADMIN");
                break;
            case CUSTOMER_ADMIN_APPROVAL:
                //IMPLEMENTATION TO BE CHANGED AFTER CREATING BANK_ADMIN_CHECKER
                log.info("get BANK admin mail id's");
                emailAddressList = getEmailAddressList("BANK_ADMIN");
                break;
            case BANK_ADMIN_APPROVAL:
                //IMPLEMENTATION TO BE CHANGED AFTER CREATING BANK_ADMIN_CHECKER
                log.info("get BANK admin mail id's");
                emailAddressList = getEmailAddressList("BANK_ADMIN");
                break;
            case CUSTOMER_APPROVAL:
                //IMPLEMENTATION TO BE CHANGED AFTER CREATING BANK_ADMIN_CHECKER
                log.info("get BANK admin mail id's");
                emailAddressList = getEmailAddressList("BANK_ADMIN");
                break;

            case BANK_USER_CREATION:
                log.info("get BANK user checker id's");
                emailAddressList = getEmailAddressList("BANK_USER_CHECKER");
                break;
            case BANK_USER_DELETION:
                log.info("get BANK user checker id's");
                emailAddressList = getEmailAddressList("BANK_USER_CHECKER");
                break;
            case BANK_USER_MODIFICATION:
                log.info("get BANK user checker id's");
                emailAddressList = getEmailAddressList("BANK_USER_CHECKER");
                break;
            case BANK_USER_APPROVAL:
                log.info("get BANK user maker id");
                // TO BE CHANGED TO RETRIEVE THE MAKER USER EMAIL ADDRESS
                emailAddressList = getEmailAddressList("BANK_USER_MAKER");
                break;
            case CUSTOMER_USER_CREATION:
                log.info("get customer admin mail id's");
                emailAddressList = getEmailAddressList("CORPORATE_USER_CHECKER");
                break;
            case CUSTOMER_USER_DELETION:
                log.info("get customer admin mail id's");
                emailAddressList = getEmailAddressList("CORPORATE_USER_CHECKER");
                break;
            case CUSTOMER_USER_MODIFICATION:
                log.info("get customer admin mail id's");
                emailAddressList = getEmailAddressList("CORPORATE_USER_CHECKER");
                break;
            case CUSTOMER_USER_APPROVAL:
                log.info("get customer user mail id's");
                // TO BE CHANGED TO RETRIEVE THE MAKER USER EMAIL ADDRESS
                emailAddressList = getEmailAddressList("CORPORATE_USER_MAKER");
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

    private String getApprovalEmailAddressList(String roleName,String userId)
    {
        Optional<User> user = userRepository.findByUserId(userId);
        if(user.isEmpty()) return "";
        else if (user.get().getModifiedUser().equalsIgnoreCase("")) return user.get().getCreatedUser();
        else return user.get().getModifiedUser();
    }
}
