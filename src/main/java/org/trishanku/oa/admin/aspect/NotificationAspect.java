package org.trishanku.oa.admin.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.notification.entity.NotificationEvent;
import org.trishanku.oa.admin.notification.service.NotificationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class NotificationAspect {

    @Autowired
    NotificationService notificationService;


    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut(

            //Point cut on super admin management
            "within(org.trishanku.oa.admin.service.SuperAdminService+) && execution(* addSuperAdmin(..))" +
            "|| within(org.trishanku.oa.admin.service.SuperAdminService+) && execution(* modifySuperAdmin(..))" +
            "|| within(org.trishanku.oa.admin.service.SuperAdminService+) && execution(* authoriseSuperAdmin(..))" +
            "|| within(org.trishanku.oa.admin.service.SuperAdminService+) && execution(* deleteSuperAdmin(..))" +

            //Point cut on bank admin management
            "|| within(org.trishanku.oa.admin.service.BankAdminService+) && execution(* addBankAdmin(..))" +
            "|| within(org.trishanku.oa.admin.service.BankAdminService+) && execution(* modifyBankAdmin(..))" +
            "|| within(org.trishanku.oa.admin.service.BankAdminService+) && execution(* authoriseBankAdmin(..))" +
            "|| within(org.trishanku.oa.admin.service.BankAdminService+) && execution(* deleteBankAdmin(..))" +


            //Point cut on corporates management
            "|| within(org.trishanku.oa.admin.service.BankAdminService+) && execution(* addBankAdmin(..))" +
            "|| within(org.trishanku.oa.admin.service.BankAdminService+) && execution(* modifyBankAdmin(..))" +
            "|| within(org.trishanku.oa.admin.service.BankAdminService+) && execution(* authoriseBankAdmin(..))" +
            "|| within(org.trishanku.oa.admin.service.BankAdminService+) && execution(* deleteBankAdmin(..))"

    )

    public void notificationPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }


    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("notificationPointcut()")
    public Object recordNotificationEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object result = joinPoint.proceed();



            try {
                switch (joinPoint.getSignature().getName()) {
                    case  "addSuperAdmin":
                        notificationService.addMakerEvent(result, NotificationEvent.SUPER_ADMIN_CREATION);
                        break;
                    case  "modifySuperAdmin":
                        notificationService.addModificationEvent(result, NotificationEvent.SUPER_ADMIN_MODIFICATION);
                        break;
                    case  "authoriseSuperAdmin":
                        notificationService.addCheckerEvent(result, NotificationEvent.SUPER_ADMIN_APPROVAL);
                        break;
                    case  "deleteSuperAdmin":
                        notificationService.addDeleteEvent(result, NotificationEvent.SUPER_ADMIN_DELETION);
                        break;
                    case  "addBankAdmin":
                        notificationService.addMakerEvent(result, NotificationEvent.BANK_ADMIN_CREATION);
                        break;
                    case  "modifyBankAdmin":
                        notificationService.addModificationEvent(result, NotificationEvent.BANK_ADMIN_MODIFICATION);
                        break;
                    case  "authoriseBankAdmin":
                        notificationService.addCheckerEvent(result, NotificationEvent.BANK_ADMIN_APPROVAL);
                        break;
                    case  "deleteBankAdmin":
                        notificationService.addDeleteEvent(result, NotificationEvent.BANK_ADMIN_DELETION);
                        break;

                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
                log.debug("joinPoint.getSignature().getDeclaringTypeName() ==>" + joinPoint.getSignature().getDeclaringTypeName());
                log.debug("joinPoint.getSignature().getName() ==>" + joinPoint.getSignature().getName());

            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }
}