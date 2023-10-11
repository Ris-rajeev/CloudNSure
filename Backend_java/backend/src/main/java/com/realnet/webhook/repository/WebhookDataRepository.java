package com.realnet.webhook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realnet.webhook.entity.WebhookData;

public interface WebhookDataRepository extends JpaRepository<WebhookData, Long> {

}
