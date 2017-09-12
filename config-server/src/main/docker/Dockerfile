FROM spring-cloud-base-image

ADD config-server.jar config-server.jar
ADD wrapper.sh wrapper.sh

RUN bash -c 'chmod +x ./wrapper.sh'
RUN bash -c 'touch ./config-server.jar'

ENTRYPOINT ["/bin/bash", "./wrapper.sh"]
