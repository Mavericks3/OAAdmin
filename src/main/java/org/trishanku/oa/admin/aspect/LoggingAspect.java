package org.trishanku.oa.admin.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class LoggingAspect {

    Logger controllerLog = LoggerFactory.getLogger("ControllerLog");
    Logger serviceLog = LoggerFactory.getLogger("ServiceLog");
    Logger repositoryLog = LoggerFactory.getLogger("RepositoryLog");

    @Autowired
    AuditRepository auditRepository;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    ObjectMapper objectMapper;


    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
//    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
//            " || within(@org.springframework.stereotype.Service *)" +
//            " || within(@org.springframework.web.bind.annotation.RestController *)")
//    public void springBeanPointcut() {
//        // Method is empty as this is just a Pointcut, the implementations are in the advices.
//    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(org.trishanku.oa.admin.repository..*)")
    public void applicationRepositoryPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Pointcut("within(org.trishanku.oa.admin.service..*)")
    public void applicationServicePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Pointcut("within(org.trishanku.oa.admin.controller..*)")
    public void applicationControllerPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "applicationRepositoryPointcut()", throwing = "e")
    public void logAfterThrowingForRepositories(JoinPoint joinPoint, Throwable e) {
        repositoryLog.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
    }

    @AfterThrowing(pointcut = "applicationServicePointcut()", throwing = "e")
    public void logAfterThrowingForServices(JoinPoint joinPoint, Throwable e) {
        serviceLog.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
    }

    @AfterThrowing(pointcut = "applicationControllerPointcut()", throwing = "e")
    public void logAfterThrowingForControllers(JoinPoint joinPoint, Throwable e) {
        controllerLog.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
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
    @Around("applicationRepositoryPointcut()")
    public Object logAroundForRepositories(ProceedingJoinPoint joinPoint) throws Throwable {

        repositoryLog.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

        try {
            Object result = joinPoint.proceed();

            repositoryLog.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);

            return result;
        } catch (IllegalArgumentException e) {
            repositoryLog.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }

    @Around("applicationServicePointcut()")
    public Object logAroundForservices(ProceedingJoinPoint joinPoint) throws Throwable {

        serviceLog.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

        try {
            Object result = joinPoint.proceed();

            serviceLog.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);

            return result;
        } catch (IllegalArgumentException e) {
            serviceLog.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }

    @Around("controllerPointcut()")
    public Object logControllerAccess(ProceedingJoinPoint joinPoint) throws Throwable {

        //System.out.println("testing the logging aspect");

        controllerLog.info("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String username= "";
        if(!(joinPoint.getSignature().getName().equalsIgnoreCase("authenticate")))
            username = jwtUtil.extractUsernameFromRequest();



        auditRepository.save(Audit.builder().accessedBy(username).accessedResource(joinPoint.getSignature().getDeclaringTypeName() + "-->" + joinPoint.getSignature().getName()).eventAt(new Date())
                .inputParameters(Arrays.toString(joinPoint.getArgs())).uuid(UUID.randomUUID()).eventAction("Entry").remoteAddress(request.getRemoteAddr()).remoteHost(request.getRemoteHost())
                .remoteUser(request.getRemoteUser()).build());

        try {
            Object result = joinPoint.proceed();


            controllerLog.info("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);

            auditRepository.save(Audit.builder().accessedBy(username).accessedResource(joinPoint.getSignature().getDeclaringTypeName() + "-->" + joinPoint.getSignature().getName()).eventAt(new Date())
                    .returnedResult(objectMapper.writeValueAsString(result)).uuid(UUID.randomUUID()).eventAction("Exit")
                    .remoteAddress(request.getRemoteAddr()).remoteHost(request.getRemoteHost())
                    .remoteUser(request.getRemoteUser()).build());

            return result;
        } catch (IllegalArgumentException e) {
            controllerLog.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

            auditRepository.save(Audit.builder().accessedBy(username).accessedResource(joinPoint.getSignature().getDeclaringTypeName() + "-->" + joinPoint.getSignature().getName()).eventAt(new Date())
                    .inputParameters(Arrays.toString(joinPoint.getArgs())).uuid(UUID.randomUUID()).eventAction("Illegal Argument")
                    .remoteAddress(request.getRemoteAddr()).remoteHost(request.getRemoteHost())
                    .remoteUser(request.getRemoteUser()).build());
            throw e;
        }
    }

}