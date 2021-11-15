package org.trishanku.oa.admin.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.trishanku.oa.admin.entity.Audit;
import org.trishanku.oa.admin.jwtauthentication.configuration.service.JWTUtil;
import org.trishanku.oa.admin.repository.AuditRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class LoggingAspect {


    @Autowired
    AuditRepository auditRepository;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    ObjectMapper objectMapper;


    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(org.trishanku.oa.admin..*)" +
            " || within(org.trishanku.oa.admin.service..*)" +
            " || within(org.trishanku.oa.admin.controller..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }


    @Around("controllerPointcut()")
    public Object logControllerAccess(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println("testing the logging aspect");

        log.info("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();


        // TO BE REPLACED WITH THE DETAILS RETRIEVED FROM JWT
        auditRepository.save(Audit.builder().accessedBy(jwtUtil.extractUsernameFromRequest()).accessedResource(joinPoint.getSignature().getDeclaringTypeName()+"-->"+joinPoint.getSignature().getName()).eventAt(new Date())
                .inputParameters(Arrays.toString(joinPoint.getArgs())).uuid(UUID.randomUUID()).eventAction("Entry").remoteAddress(request.getRemoteAddr()).remoteHost(request.getRemoteHost())
                .remoteUser(request.getRemoteUser()).build());

        try {
            Object result = joinPoint.proceed();



            log.info("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);

            auditRepository.save(Audit.builder().accessedBy(jwtUtil.extractUsernameFromRequest()).accessedResource(joinPoint.getSignature().getDeclaringTypeName()+"-->"+joinPoint.getSignature().getName()).eventAt(new Date())
                    .returnedResult(objectMapper.writeValueAsString(result)).uuid(UUID.randomUUID()).eventAction("Exit")
                    .remoteAddress(request.getRemoteAddr()).remoteHost(request.getRemoteHost())
                    .remoteUser(request.getRemoteUser()).build());

            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

            auditRepository.save(Audit.builder().accessedBy(jwtUtil.extractUsernameFromRequest()).accessedResource(joinPoint.getSignature().getDeclaringTypeName()+"-->"+joinPoint.getSignature().getName()).eventAt(new Date())
                    .inputParameters(Arrays.toString(joinPoint.getArgs())).uuid(UUID.randomUUID()).eventAction("Illegal Argument")
                    .remoteAddress(request.getRemoteAddr()).remoteHost(request.getRemoteHost())
                    .remoteUser(request.getRemoteUser()).build());
            throw e;
        }
    }

}