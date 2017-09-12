#!/bin/bash

WAITING_FOR_DEPENDENCE=${WAITING_FOR_DEPENDENCE:='true'}
if [ "$WAITING_FOR_DEPENDENCE" != "true" ]; then
    echo "Starting api-gateway server immediately"
    java -jar ./api-gateway-service.jar
    exit 0
fi

source ./functions.sh

DISCOVERY_SERVER_HOST=${DISCOVERY_SERVER_HOST:='discovery-server'}
DISCOVERY_SERVER_PORT=${DISCOVERY_SERVER_PORT:=8761}
waitingForService ${DISCOVERY_SERVER_HOST} ${DISCOVERY_SERVER_PORT}

waitingForServiceInDiscovery ${DISCOVERY_SERVER_HOST} ${DISCOVERY_SERVER_PORT} ${REALTOR_SERVICE:='realtor-service'}
waitingForServiceInDiscovery ${DISCOVERY_SERVER_HOST} ${DISCOVERY_SERVER_PORT} ${CLIENT_SERVICE:='client-service'}

WAITING_FOR_MONITORING=${WAITING_FOR_MONITORING:='false'}
if [ "$WAITING_FOR_MONITORING" == "true" ]; then
    waitingForServiceInDiscovery ${DISCOVERY_SERVER_HOST} ${DISCOVERY_SERVER_PORT} ${ZIPKIN_SERVER:='zipkin-server'}
fi

echo "Starting api-gateway server"
echo "Setting eureka.client.serviceUrl.defaultZone to http://${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}/eureka"
echo
env "eureka.client.serviceUrl.defaultZone=http://${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}/eureka" \
    java -jar ./api-gateway-service.jar
