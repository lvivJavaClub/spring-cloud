FROM java:8

RUN mkdir -p /opt/spring-cloud
WORKDIR /opt/spring-cloud

ADD rating-service.jar rating-service.jar
ADD wrapper.sh wrapper.sh

RUN bash -c 'chmod +x ./wrapper.sh'
RUN bash -c 'touch ./rating-service.jar'

ENTRYPOINT ["/bin/bash", "./wrapper.sh"]
