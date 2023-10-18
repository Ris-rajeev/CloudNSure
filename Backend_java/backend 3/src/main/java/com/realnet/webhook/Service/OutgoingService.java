package com.realnet.webhook.Service;

import java.util.List;

import org.hibernate.query.criteria.internal.predicate.IsEmptyPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.webhook.entity.OutgoingEntity;
import com.realnet.webhook.repository.OutgoingRepository;

@Service
public class OutgoingService {

	@Autowired
	private OutgoingRepository outgoingRepository;

	public boolean isEntityAllowed(String onEntity) {
		if (onEntity == null || onEntity.isEmpty()) {
			return true; // Allow unspecified entities
		}

		List<OutgoingEntity> entityEventsList = outgoingRepository.findByOnEntity(onEntity);

		if (entityEventsList == null || entityEventsList.isEmpty()) {
			return false; // Disallow entities not found in entity events
		}

		return true;
	}

	public boolean isOnEventAllowed(String onEntity, String onEvent) {
		OutgoingEntity entityEvents = outgoingRepository.findByOnEntityAndOnEvent(onEntity, onEvent);
		if (entityEvents == null || !entityEvents.is_active()) {
			return false; // Disallow if entity not found or inactive
		}
		return true;
	}

	public boolean isOnincomingwebhookallowed(Long incoming_id) {
		List<OutgoingEntity> out = outgoingRepository.findbyincomingwebhook(incoming_id);
		if (!out.isEmpty()) {
			return true;

		} else {
			return false;
		}

	}
}
