#!/bin/bash 

mvn clean package

java -jar target/workshop-0.0.1-SNAPSHOT.jar
