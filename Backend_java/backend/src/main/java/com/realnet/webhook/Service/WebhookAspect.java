package com.realnet.webhook.Service;

import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.realnet.webhook.entity.Webhook_workflow;

@Aspect
@Component
public class WebhookAspect {

	@Autowired
	private Workflow_service workflowService;

	@Autowired
	private OutgoingService outgoingService;

	// @Pointcut("execution(*
	// com.realnet.SureConnect.Controller.SureController.*(..))|| execution(*
	// com.realnet.Accesstype_back.Controllers.AccesstypeController.*(..))")
    @Pointcut("execution(* com.realnet.*.*(..))")
//	@Pointcut("within(com.realnet..*) || within(com.realnet..*controller) || within(com.realnet..*controllers)")
	public void webhookMethods() {
	}

	@Around("webhookMethods()")
	public Object aroundWebhookMethods(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		String onEvent = joinPoint.getSignature().getName();
		
		String onEntity = "";
		if (args.length != 0) {
			onEntity = args[0].getClass().getSimpleName();
		}

		if (onEvent.equals("Savedata") || onEvent.equals("add") || onEvent.equals("insert")) {
			onEvent = "add";
		}
		System.out.println("aroundWebhookMethods called with onEntity: " + onEntity);

		// Check if the entity is allowed
		if (onEntity != null && outgoingService.isEntityAllowed(onEntity)) {

			// Check if the event type is allowed for the entity
			if (outgoingService.isOnEventAllowed(onEntity, onEvent)) {

				// Invoke the REST controller method
				Object result = joinPoint.proceed();

				// Save the data to the entity table
				if (onEntity != null) {
					Webhook_workflow webhook = workflowService.save_webhook(result, onEvent, onEntity);
					return result;
				}

				return result;

			} else {
				// Event type not allowed for the entity, so ignore it
				return joinPoint.proceed();
			}

		} else {
			// Entity not found in entity events table,so ignore it
			return joinPoint.proceed();
		}
	}
}