package com.realnet.DataTypesWireframe.Entity;
 import lombok.*;
 import javax.persistence.*;
 import java.time.LocalDateTime;
 import java.util.*;
 @Entity
 @Data
 public class    DataTypesWireframe_t{
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 private String text;
 private String password;
 private Long number;
 private String date;
 private String dateTime;
 private String paragraph;
 private String textArea;
 private String dropDown;
 private String autoComplete;
 private boolean radioField;
 private boolean checkbox;
 private String email;
 private String phoneNumber;
 private String calculatedField;
 private String section;
 private String division;
 private String fileUpload;
 private String selectField;
 private String button;
 private String multiselect;
 private String tagsField;
 private String image;
 private String audioField;
 private String url;
 private String decima;
 private String recaptchaField;
 private String htmlElement;
 private String pannel;
 private String tabField;
 private String addressGroup;
 private String signatureField;
 private String percent;
 private String currency;
 private String fieldSet;
 private String tableField;
 private String hidden;
 private String editGrid;
 private String tree;
 private String content;
 private String element;
 private String colomn;
 private String dataMap;
 private String dataGrid;
 
 /////checking
 private String gender;
 }