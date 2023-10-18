#pulling from github

FROM alpine/git:v2.30.1 AS code
ARG BUILD_ID
RUN echo $BUILD_ID
LABEL stage=builder
LABEL build_id=$BUILD_ID
RUN mkdir repo
WORKDIR /repo
RUN git clone -b deploy0807 https://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@github.com/ganeshris/suresetu.git

#************* Stage 2 - Maven Build **************
FROM maven AS mvn
LABEL stage=builder
LABEL build=$BUILD_ID
WORKDIR /workspace/app
COPY --from=code /repo/suresetu/backend /workspace/app
RUN mvn install -Dmaven.test.skip

#******** Stage 3 - Docker Build *********

#FROM java:8-jdk-alpine
FROM openjdk:8-jdk-alpine
FROM farhan23432/cns-jdk:jdk-01
LABEL stage=app
LABEL build=$BUILD_ID
RUN mkdir /usr/app
COPY --from=mvn /workspace/app/target/*jar /usr/app/gtest.jar
WORKDIR /usr/app
EXPOSE 19004
ENTRYPOINT ["java", "-jar", "gtest.jar"]