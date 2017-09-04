#!/bin/bash

WAITING_FOR_DEPENDENCE=${WAITING_FOR_DEPENDENCE:='false'}

if [ "$WAITING_FOR_DEPENDENCE" != "true" ]; then
    echo "Starting dsl-executor server immediately"
    java -jar ./dsl-executor-service.jar
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

STORAGE_SERVICE=${STORAGE_SERVICE:='storage-service'}
echo "Trying to get '${STORAGE_SERVICE}' from ${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}"
until $(curl --output /dev/null --silent --head --fail "http://${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}/eureka/apps/${STORAGE_SERVICE}"); do
    echo -e ".\c"
    sleep 1
done
echo

RATING_SERVICE=${RATING_SERVICE:='rating-service'}
echo "Trying to get '${RATING_SERVICE}' from ${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}"
until $(curl --output /dev/null --silent --head --fail "http://${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}/eureka/apps/${RATING_SERVICE}"); do
    echo -e ".\c"
    sleep 1
done
echo

HACKSTER_SERVICE=${HACKSTER_SERVICE:='hackster-service'}
echo "Trying to get '${HACKSTER_SERVICE}' from ${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}"
until $(curl --output /dev/null --silent --head --fail "http://${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}/eureka/apps/${HACKSTER_SERVICE}"); do
    echo -e ".\c"
    sleep 1
done
echo

echo "Starting dsl-executor server"
echo "Setting eureka.client.serviceUrl.defaultZone to http://${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}/eureka"
echo
env "eureka.client.serviceUrl.defaultZone=http://${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}/eureka" \
    java -jar ./dsl-executor-service.jar
