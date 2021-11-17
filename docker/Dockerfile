FROM jboss/wildfly

ARG login
ARG password
ARG dp_properties

#ADD web-lab-3-1.0.war /opt/jboss/wildfly/standalone/deployments/

RUN /opt/jboss/wildfly/bin/add-user.sh "$login" "$password" --silent
ADD standalone.xml /opt/jboss/wildfly/standalone/configuration

#deploy jdbc driver for oracle
ADD ojdbc8.jar /opt/jboss/wildfly/standalone/deployments
#deploy jdbc driver for postgres
ADD postgresql-42.3.1.jar /opt/jboss/wildfly/standalone/deployments

ADD dbproperties "$dp_properties"
ENV DB_properties=/dbproperties
#RUN mkdir -p /opt/wildfly/logs
#VOLUME /opt/wildfly/logs

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

#docker run -p 8080:8080 -p 9990:9990 -it -v C:/ITMO/Programming/web-lab-3/build/libs/wars:/opt/jboss/wildfly/standalone/deployments/ --rm bd-lab3

#build this image with --build-arg login --build-arg password