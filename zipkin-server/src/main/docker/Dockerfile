FROM java:8

RUN mkdir -p /opt/spring-cloud
WORKDIR /opt/spring-cloud

ADD zipkin-server.jar zipkin-server.jar
ADD wrapper.sh wrapper.sh

RUN bash -c 'chmod +x ./wrapper.sh'
RUN bash -c 'touch ./zipkin-server.jar'

ENTRYPOINT ["/bin/bash", "./wrapper.sh"]
