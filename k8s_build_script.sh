#!/bin/bash

PRJ_NAME=cnsportal-b
DOCKER_USER=risadmin
DOCKER_PASS=admin1234

DOCKER_URL=git.cloudnsure.com
CONT_PORT=30181
TARGET_PORT=19004
DOCKER_TAG=latest
IMAGE_NAME=$DOCKER_URL/$DOCKER_USER/$PRJ_NAME:$DOCKER_TAG

docker stop $PRJ_NAME >/dev/null 2>&1 || true
docker rm $PRJ_NAME >/dev/null 2>&1 || true

# Build and push the Docker image
DOCKER_BUILDKIT=1 docker build -t $DOCKER_URL/$DOCKER_USER/$PRJ_NAME:$DOCKER_TAG --build-arg BUILD_ID=$DOCKER_TAG .
docker login --username=$DOCKER_USER --password=$DOCKER_PASS $DOCKER_URL​
docker tag $IMAGE_NAME $DOCKER_URL/$DOCKER_USER/$PRJ_NAME:latest
docker push $DOCKER_URL/$DOCKER_USER/$PRJ_NAME:$DOCKER_TAG
docker push $DOCKER_URL/$DOCKER_USER/$PRJ_NAME:latest
docker logout $DOCKER_URL
IMAGE_ID=$(docker images --format "{{.ID}}" $IMAGE_NAME)
docker system prune -f

echo "
apiVersion: apps/v1
kind: Deployment
metadata:
  name: $PRJ_NAME
spec:
  replicas: 2
  selector:
    matchLabels:
      app: $PRJ_NAME
  template:
    metadata:
      labels:
        app: $PRJ_NAME
    spec:
      containers:
        - name: $PRJ_NAME
          image: $IMAGE_NAME  
          ports:
            - containerPort: $TARGET_PORT

" >>deployment.yml

kubectl apply -f "deployment.yaml