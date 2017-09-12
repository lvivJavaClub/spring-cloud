FROM spring-cloud-base-image

ADD dsl-executor-service.jar dsl-executor-service.jar
ADD wrapper.sh wrapper.sh

RUN bash -c 'chmod +x ./wrapper.sh'
RUN bash -c 'touch ./dsl-executor-service.jar'

ENTRYPOINT ["/bin/bash", "./wrapper.sh"]
