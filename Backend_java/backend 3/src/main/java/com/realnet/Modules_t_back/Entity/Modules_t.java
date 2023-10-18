package com.realnet.Modules_t_back.Entity;
 import lombok.*;
 import javax.persistence.*;
 import java.time.LocalDateTime;
 import java.util.*;

 @Entity 
 @Data
 public class    Modules_t{ 

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String modules;
 private String description;
 private String access_exclusive;

 }