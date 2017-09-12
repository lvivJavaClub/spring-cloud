FROM spring-cloud-base-image

ADD hackster-service.jar hackster-service.jar
ADD wrapper.sh wrapper.sh

RUN bash -c 'chmod +x ./wrapper.sh'
RUN bash -c 'touch ./hackster-service.jar'

ENTRYPOINT ["/bin/bash", "./wrapper.sh"]
