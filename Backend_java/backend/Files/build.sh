#!/bin/bash

PRJ_NAME=suresetu-b
#DOCKER_USER=farhan23432
#DOCKER_PASS=Farhan@23432
#DOCKER_URL=https://index.docker.io/v1/
DOCKER_USER=risadmin
DOCKER_PASS=welcometoris1

#DOCKER_USER=$(vault kv get -field=user secret/registry)
#DOCKER_PASS=$(vault kv get -field=pass secret/registry)
DOCKER_URL=43.205.154.152:5000
CONT_PORT=30181
TARGET_PORT=19004
DOCKER_TAG=$(date +%s)
IMAGE_NAME=$DOCKER_USER/$PRJ_NAME:$DOCKER_TAG
# Stop any existing containers with the same name and ports
docker stop $PRJ_NAME >/dev/null 2>&1 || true
docker rm $PRJ_NAME >/dev/null 2>&1 || true

# Build and push the Docker image
DOCKER_BUILDKIT=1 docker build -t $DOCKER_USER/$PRJ_NAME:$DOCKER_TAG --build-arg BUILD_ID=$DOCKER_TAG .
docker login --username=$DOCKER_USER --password=$DOCKER_PASS $DOCKER_URL

docker tag $IMAGE_NAME $DOCKER_URL/$DOCKER_USER:$PRJ_NAME
docker push $DOCKER_URL/$DOCKER_USER:$PRJ_NAME
#docker push $DOCKER_USER/$PRJ_NAME:$DOCKER_TAG
docker logout $DOCKER_URL
IMAGE_ID=$(docker images --format "{{.ID}}" $IMAGE_NAME)
docker system prune -f
# Deploy the image in a Docker container
docker run -d --name $PRJ_NAME -p $CONT_PORT:$TARGET_PORT --restart always $DOCKER_USER/$PRJ_NAME:$DOCKER_TAG
echo "Image $IMAGE_ID deployed in container $PRJ_NAME"