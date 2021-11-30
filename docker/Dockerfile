FROM jboss/wildfly

ARG login
ARG password
ARG db_url
ARG db_user
ARG db_password

#ADD web-lab-3-1.0.war /opt/jboss/wildfly/standalone/deployments/

RUN /opt/jboss/wildfly/bin/add-user.sh "$login" "$password" --silent
ADD standalone.xml /opt/jboss/wildfly/standalone/configuration

#deploy jdbc driver for oracle
ADD ojdbc8.jar /opt/jboss/wildfly/modules/system/layers/base/com/oracle/main/ojdbc8.jar
#deploy charsets for db
ADD orai18n.jar /opt/jboss/wildfly/modules/system/layers/base/com/oracle/main/orai18n.jar
#add module to wildfly
ADD  module.xml /opt/jboss/wildfly/modules/system/layers/base/com/oracle/main/module.xml
#ADD  /opt/jboss/wildfly/standalone/deployments/postgresql-42.3.1.jar

#RUN mkdir -p /opt/wildfly/logs
#VOLUME /opt/wildfly/logs

RUN echo "javax.persistence.jdbc.url = $db_url" > /opt/jboss/wildfly/dbproperties && \
    echo "javax.persistence.jdbc.user = $db_user" >> /opt/jboss/wildfly/dbproperties && \
    echo "javax.persistence.jdbc.password = $db_password" >> /opt/jboss/wildfly/dbproperties

ENV DB_properties=/opt/jboss/wildfly/dbproperties
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]