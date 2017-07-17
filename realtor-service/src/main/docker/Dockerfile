FROM java:8
ADD realtor-service.jar realtor-service.jar
ADD wrapper.sh wrapper.sh
RUN bash -c 'chmod +x /wrapper.sh'
RUN bash -c 'touch /realtor-service.jar'
ENTRYPOINT ["/bin/bash", "/wrapper.sh"]
