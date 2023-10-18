package com.realnet.loggingeachuserconfig;

import java.time.LocalDateTime;
import java.util.List;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.After;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.realnet.logging1.entity.AppUserLog;
import com.realnet.logging1.repository.AppUserLogginRepository;
import javax.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class UserAccessLoggingAspect {

	@Autowired
	AppUserLogginRepository appuserLoggingRepo;

	private static final Logger logger = LoggerFactory.getLogger(UserAccessLoggingAspect.class);

	@After("execution(* com.realnet..*Controller.*(..))")
	public void logUserApiAccess(JoinPoint joinPoint) {
		String username = MDC.get("USER");

		// Capture the start time for measuring the response time
		long startTime = System.currentTimeMillis();

		// Access the HttpServletRequest and HttpServletResponse objects
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();

		// Get the request URL and method
		String requestUrl = request.getRequestURI();
		String httpMethod = request.getMethod();
		int statusCode = response.getStatus();

		// Capture the end time after method execution
		long endTime = System.currentTimeMillis();

		// Calculate the response time
		long responseTime = endTime - startTime;

		// Create a custom log message that includes all information
		String logMessage = String.format(
				"User %s accessed %s API at %s. HTTP Method: %s, Status code: %d, Response time: %d ms", username,
				requestUrl, LocalDateTime.now(), httpMethod, statusCode, responseTime);
		List<AppUserLog> userList = appuserLoggingRepo.findByUserName(username);
		boolean logFlag = false;
		for (AppUserLog user : userList) {
			if (user.getUserName().toLowerCase().equals(username.toLowerCase()) && user.getGenerateLog().equals("Y")) {
				logFlag = true;
				break;
			}
		}
		if (logFlag) {
			logger.info(logMessage);
		}
		MDC.clear();
	}
}
