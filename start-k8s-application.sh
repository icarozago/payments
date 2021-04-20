#!/bin/sh
minikube start
minikube docker-env
eval $(minikube -p minikube docker-env)
cd payments/
mvn clean package -DskipTests
docker build -t payments .
cd ../mail-sender
mvn clean package -DskipTests
docker build -t mail-sender
cd ../k8s
kubectl apply -f zookeeper-service.yml
kubectl apply -f kafka-service.yml
kubectl apply -f postgres-pv.yml
kubectl apply -f postgres-service.yml
kubectl apply -f payments-service.yml
kubectl apply -f mail-sender-service.yml
kubectl apply -f logstash-service.yml
kubectl apply -f payments-hpa.yml
