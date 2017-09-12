#!/bin/bash

WAITING_FOR_DEPENDENCE=${WAITING_FOR_DEPENDENCE:='true'}
if [ "$WAITING_FOR_DEPENDENCE" != "true" ]; then
    echo "Starting config server immediately"
    java -jar ./config-server.jar
    exit 0
fi

source ./functions.sh

DISCOVERY_SERVER_HOST=${DISCOVERY_SERVER_HOST:='discovery-server'}
DISCOVERY_SERVER_PORT=${DISCOVERY_SERVER_PORT:=8761}
waitingForService ${DISCOVERY_SERVER_HOST} ${DISCOVERY_SERVER_PORT}

WAITING_FOR_MONITORING=${WAITING_FOR_MONITORING:='false'}
if [ "$WAITING_FOR_MONITORING" == "true" ]; then
    waitingForServiceInDiscovery ${DISCOVERY_SERVER_HOST} ${DISCOVERY_SERVER_PORT} ${ZIPKIN_SERVER:='zipkin-server'}
fi

echo "Starting config server"
echo "Setting eureka.client.serviceUrl.defaultZone to http://${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}/eureka"
echo
env "eureka.client.serviceUrl.defaultZone=http://${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}/eureka" \
    java -jar ./config-server.jar
