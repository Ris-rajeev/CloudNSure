#!/bin/bash

sudo yum -y install node 
sudo yum -y install git
sudo yum -y install docker
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker $USER
newgrp docker

git clone https://github.com/rajeev-007-glitch/Task-Manager-API.git
cd Task-Manager-API
echo "MONGO_URI=mongodb+srv://RajeevSinghJadon:MongoDb@nodeandexpressprojects.ocu9ube.mongodb.net/TASK-MANAGER?retryWrites=true&w=majority" >> .env
echo "FROM node:18
# Create app directory
WORKDIR /usr/src/app
# Install app dependencies
# A wildcard is used to ensure both package.json AND package-lock.json are copied
# where available (npm@5+)
COPY package*.json ./
RUN npm install
# If you are building your code for production
# RUN npm ci --omit=dev
# Bundle app source
COPY . .
EXPOSE 3000
CMD [ "node", "app.js" ]" >> Dockerfile
echo "node_modules
npm-debug.log" >> .dockerignore

docker build . -t rajeev/node-web-app
docker run -p 49160:8080 -d rajeev/node-web-app