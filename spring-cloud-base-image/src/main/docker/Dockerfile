FROM anapsix/alpine-java

RUN set -ex && \
    apk upgrade --update && \
    apk add --update curl ca-certificates bash

RUN mkdir -p /opt/spring-cloud
WORKDIR /opt/spring-cloud

ADD functions.sh functions.sh

RUN bash -c 'chmod +r ./functions.sh'
