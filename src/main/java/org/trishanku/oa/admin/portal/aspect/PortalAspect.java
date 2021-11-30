package org.trishanku.oa.admin.portal.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trishanku.oa.admin.notification.entity.NotificationEvent;
import org.trishanku.oa.admin.notification.service.NotificationService;
import org.trishanku.oa.admin.portal.service.PortalService;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class PortalAspect {

    @Autowired
    PortalService portalService;


    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut(

           "within(org.trishanku.oa.admin.service.CustomerService+) && execution(* authoriseCustomer(..))" +
                   "|| within(org.trishanku.oa.admin.service.RMService+) && execution(* authoriseRMUser(..))" +
                   "|| within(org.trishanku.oa.admin.service.CustomerAdminService+) && execution(* authoriseCustomerAdmin(..))" +
                   "|| within(org.trishanku.oa.admin.service.CustomerUserService+) && execution(* authoriseCustomerUser(..))" +
                   "|| within(org.trishanku.oa.admin.service.AgreementService+) && execution(* authoriseAgreement(..))" +
                   "|| within(org.trishanku.oa.admin.service.SBRService+) && execution(* authoriseSBR(..))" +
                   "|| within(org.trishanku.oa.admin.service.BankUserService+) && execution(* authoriseBankUser(..))"

             )

    public void portalPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }


    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("portalPointcut()")
    public Object recordPortalEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object result = joinPoint.proceed();




                switch (joinPoint.getSignature().getName()) {

                    case  "authoriseCustomer":
                        portalService.addCustomer(result);
                        break;

                    case  "authoriseRMUser":
                        portalService.addRM(result);
                        break;

                    case  "authoriseCustomerAdmin":
                        portalService.addCustomerAdmin(result);
                        break;

                    case  "authoriseCustomerUser":
                        portalService.addCustomerUser(result);
                        break;

                    case  "authoriseAgreement":
                        portalService.addAgreement(result);
                        break;

                    case  "authoriseSBR":
                        portalService.addSBR(result);
                        break;

                    case  "authoriseBankUser":
                        portalService.addBankUser(result);
                        break;



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