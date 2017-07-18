FROM java:8

RUN mkdir -p /opt/spring-cloud
WORKDIR /opt/spring-cloud

ADD api-gateway-service.jar api-gateway-service.jar
ADD wrapper.sh wrapper.sh

RUN bash -c 'chmod +x ./wrapper.sh'
RUN bash -c 'touch ./api-gateway-service.jar'

ENTRYPOINT ["/bin/bash", "./wrapper.sh"]
