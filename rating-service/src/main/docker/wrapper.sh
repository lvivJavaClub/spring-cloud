#!/bin/bash

if [ "$WAITING_FOR_DEPENDENCE" == "true" ]; then

    echo "Trying to connect to discovery-server"
    until $(curl --output /dev/null --silent --head --fail http://discovery-server:8761/info); do
        echo '.'
        sleep 1
    done

    echo "Trying to connect to config-server"
    until $(curl --output /dev/null --silent --head --fail http://config-server:8888/info); do
        echo '.'
        sleep 1
    done
    echo "Starting"

fi

java -jar /rating-service.jar
