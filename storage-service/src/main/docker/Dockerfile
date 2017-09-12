FROM spring-cloud-base-image

ADD storage-service.jar storage-service.jar
ADD wrapper.sh wrapper.sh

RUN bash -c 'chmod +x ./wrapper.sh'
RUN bash -c 'touch ./storage-service.jar'

ENTRYPOINT ["/bin/bash", "./wrapper.sh"]
