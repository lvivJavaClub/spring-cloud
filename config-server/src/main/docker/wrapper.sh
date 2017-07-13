#!/bin/bash

if [ "$WATE" == "true" ]; then

    echo "Trying to connect to discovery-server"
    until $(curl --output /dev/null --silent --head --fail http://discovery-server:8761/info); do
        echo '.'
        sleep 1
    done
    echo "Starting"

fi

java -jar /config-server.jar
