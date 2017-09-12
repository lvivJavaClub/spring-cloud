#!/bin/bash

waitingForService() {
    SERVER_HOST=$1
    SERVER_PORT=$2
    echo "Trying to connect to server on ${SERVER_HOST}:${SERVER_PORT}"
    until $(curl --output /dev/null --silent --head --fail "http://${SERVER_HOST}:${SERVER_PORT}/info"); do
        echo -e ".\c"
        sleep 1
    done
    echo
}

STORAGE_SERVICE=${STORAGE_SERVICE:='storage-service'}
waitingForServiceInDiscovery() {
    DISCOVERY_SERVER_HOST=$1
    DISCOVERY_SERVER_PORT=$2
    SERVICE=$3
    echo "Trying to get '${SERVICE}' from ${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}"
    until $(curl --output /dev/null --silent --head --fail "http://${DISCOVERY_SERVER_HOST}:${DISCOVERY_SERVER_PORT}/eureka/apps/${SERVICE}"); do
        echo -e ".\c"
        sleep 1
    done
    echo
}
