#!/bin/bash

if [ "$WATE" == "true" ]; then

    echo "Trying to connect to discovery-server"
    until $(curl --output /dev/null --silent --head --fail http://discovery-server:8761/info); do
        echo '.'
        sleep 1
    done

    echo "Trying to connect to storage-service"
    until $(curl --output /dev/null --silent --head --fail http://storage-service:8091/info); do
        echo '.'
        sleep 1
    done

    echo "Trying to connect to realtor-service"
    until $(curl --output /dev/null --silent --head --fail http://realtor-service:8080/info); do
        echo '.'
        sleep 1
    done
    echo "Starting"

fi

java -jar /api-gateway-service.jar
