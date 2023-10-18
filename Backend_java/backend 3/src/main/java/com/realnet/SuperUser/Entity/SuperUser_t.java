package com.realnet.SuperUser.Entity;
 import lombok.*;
 import javax.persistence.*;
 import java.time.LocalDateTime;
 import java.util.*;

 @Entity 
 @Data
 public class    SuperUser_t{ 

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String username;
 private String userPassw;
 private String title;
 private String shortName;
 private String fullName;
 private String expiryDate;
 private String daysMth;
 private String noDaysMth;
 private String status;
 private String changePassw;
 private String createby;
 private String createdate;
 private String updateby;
 private String updatedate;
 private String usrGrp;
 private String langCode;
 private String firstLogin;
 private String positionCode;
 private String departmentCode;
 private String Account;
 private String email;
 private String notification;
 private String customerId;
 private String password1;
 private String password2;
 private String password3;
 private String password4;
 private String pwdChangedCnt;
 private String lastPwdChangedDate;
 private String photo;
 private String photoName;
 private String provider;
 private String country;
 private String depString;
 private String isBlocked;
 private String about;
 private String checknumber;
 private String working;
 private String accesstype;
 private String access_duration;
 private String random_no;
 private String mob_no;
 private boolean IsComplete;
 private boolean active;
 private String customerNumer;
 private String positionCodeString;
 private String departmentCodeString;
 private String usrGrpId;
 private String confirmPassword;
 private String usrGrpName;
 private String totalLogInfo;
 private String pass;
 private String roles;
 
 
 private String extn1;
 private String extn2;
 private String extn3;
 private String extn4;
 private String extn5;
 private String extn6;
 private String extn7;

 }