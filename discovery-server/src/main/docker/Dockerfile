FROM spring-cloud-base-image

ADD discovery-server.jar discovery-server.jar
ADD wrapper.sh wrapper.sh

RUN bash -c 'chmod +x ./wrapper.sh'
RUN bash -c 'touch ./discovery-server.jar'

ENTRYPOINT ["/bin/bash", "./wrapper.sh"]
