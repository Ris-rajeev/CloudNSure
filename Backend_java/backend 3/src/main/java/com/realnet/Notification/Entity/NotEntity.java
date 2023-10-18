package com.realnet.Notification.Entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.convert.JodaTimeConverters.LocalDateTimeToJodaDateTime;

import lombok.Data;
@Data
@Entity
public class NotEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String notification;
	private String time;

}
