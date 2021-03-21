#!/bin/sh
cd payments/
mvn clean package -DskipTests
cd ../mail-sender
mvn clean package -DskipTests
cd ..
docker-compose down
docker-compose up -d --build
