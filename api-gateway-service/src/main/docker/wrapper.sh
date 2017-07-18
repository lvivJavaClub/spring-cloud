#!/bin/bash

WAITING_FOR_DEPENDENCE=${WAITING_FOR_DEPENDENCE:='false'}

if [ "$WAITING_FOR_DEPENDENCE" != "true" ]; then
    echo "Starting api-gateway server immediately"
    java -jar ./api-gateway-service.jar
    exit 0
fi

DISCOVERY_SERVER_HOST=${DISCOVERY_SERVER_HOST:='discovery-server'}
DISCOVERY_SERVER_PORT=${DISCOVERY_SERVER_PORT:=8761}

echo "Trying to connect to discovery server on ${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}"
until $(curl --output /dev/null --silent --head --fail "http://${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}/info"); do
    echo -e ".\c"
    sleep 1
done
echo

REALTOR_SERVICE_HOST=${REALTOR_SERVICE_HOST:='realtor-service'}
REALTOR_SERVICE_PORT=${REALTOR_SERVICE_PORT:=8080}

echo "Trying to connect to on ${REALTOR_SERVICE_HOST}:${REALTOR_SERVICE_PORT}"
until $(curl --output /dev/null --silent --head --fail "http://${REALTOR_SERVICE_HOST}:${REALTOR_SERVICE_PORT}/info"); do
    echo -e ".\c"
    sleep 1
done
echo

# rem TODO replace with client-service
STORAGE_SERVICE_HOST=${STORAGE_SERVICE_HOST:='storage-service'}
STORAGE_SERVICE_PORT=${STORAGE_SERVICE_PORT:=8091}

echo "Trying to connect to on ${STORAGE_SERVICE_HOST}:${STORAGE_SERVICE_PORT}"
until $(curl --output /dev/null --silent --head --fail "http://${STORAGE_SERVICE_HOST}:${STORAGE_SERVICE_PORT}/info"); do
    echo -e ".\c"
    sleep 1
done
echo

echo "Starting api-gateway server"
echo "Setting eureka.client.serviceUrl.defaultZone to http://${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}/eureka"

env "eureka.client.serviceUrl.defaultZone=http://${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}/eureka" \
    java -jar ./api-gateway-service.jar
