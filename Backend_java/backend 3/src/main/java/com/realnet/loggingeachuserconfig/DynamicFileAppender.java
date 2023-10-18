package com.realnet.loggingeachuserconfig;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class DynamicFileAppender<E> extends AppenderBase<E> {
    private String logDirectory;
    private String fileNamePattern;
    private String maxSize;
    private Layout<E> layout;



    // Map to store appenders for each user
    private Map<String, RollingFileAppender<E>> userAppenders = new HashMap<>();
    
    public void setLogDirectory(String logDirectory) {
        this.logDirectory = logDirectory;
    }

    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }

    public void setLayout(Layout<E> layout) {
        this.layout = layout;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    protected void append(E eventObject) {
        if (isStarted()) {
            String username = MDC.get("USER");
            if (username != null) {
                // Get or create an appender for the user
                RollingFileAppender<E> userAppender = getUserAppender(username);

                // Append the log event to the user's appender
                userAppender.doAppend(eventObject);
            } else {
                addWarn("No 'USER' MDC value found. Skipping log message.");
            }
        }
    }

    private RollingFileAppender<E> getUserAppender(String username) {
        synchronized (userAppenders) {
            RollingFileAppender<E> appender = userAppenders.get(username);
            if (appender == null) {
                appender = createAppender(username);
                userAppenders.put(username, appender);
            }
            return appender;
        }
    }

    private RollingFileAppender<E> createAppender(String username) {
        RollingFileAppender<E> appender = new RollingFileAppender<>();
        appender.setContext(context);
        appender.setEncoder(new LayoutWrappingEncoder<>());
        appender.setFile(getLogFileName(username));

        TimeBasedRollingPolicy<E> rollingPolicy = new TimeBasedRollingPolicy<>();
        rollingPolicy.setContext(context);
        rollingPolicy.setFileNamePattern(fileNamePattern);
        rollingPolicy.setMaxHistory(10);
        rollingPolicy.setParent(appender);
        rollingPolicy.start();
        appender.setRollingPolicy(rollingPolicy);

        SizeBasedTriggeringPolicy<E> triggeringPolicy = new SizeBasedTriggeringPolicy<>();
        triggeringPolicy.setMaxFileSize(FileSize.valueOf(maxSize));
        triggeringPolicy.start();
        appender.setTriggeringPolicy(triggeringPolicy);

        appender.setLayout(layout);
        appender.start();

        return appender;
    }

//    private String getLogFileName(String username) {
//        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        return logDirectory + "/" + username + "-" + currentDate + ".log";
//    }
    private String getLogFileName(String username) {
    	//String fileName = loggingservice.getUserLogByusername(username).getLogFileName();
        return logDirectory + "/" + username+".log";
    }



}


