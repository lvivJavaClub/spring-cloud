FROM spring-cloud-base-image

ADD client-service.jar client-service.jar
ADD wrapper.sh wrapper.sh

RUN bash -c 'chmod +x ./wrapper.sh'
RUN bash -c 'touch ./client-service.jar'

ENTRYPOINT ["/bin/bash", "./wrapper.sh"]
