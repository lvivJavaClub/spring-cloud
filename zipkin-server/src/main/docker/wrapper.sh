#!/bin/bash

WAITING_FOR_DEPENDENCE=${WAITING_FOR_DEPENDENCE:='true'}
if [ "$WAITING_FOR_DEPENDENCE" != "true" ]; then
    echo "Starting zipkin server immediately"
    java -jar ./zipkin-server.jar
    exit 0
fi

source ./functions.sh

DISCOVERY_SERVER_HOST=${DISCOVERY_SERVER_HOST:='discovery-server'}
DISCOVERY_SERVER_PORT=${DISCOVERY_SERVER_PORT:=8761}
waitingForService ${DISCOVERY_SERVER_HOST} ${DISCOVERY_SERVER_PORT}

echo "Starting zipkin server"
echo "Setting eureka.client.serviceUrl.defaultZone to http://${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}/eureka"
echo
env "eureka.client.serviceUrl.defaultZone=http://${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}/eureka" \
    java -jar ./zipkin-server.jar
