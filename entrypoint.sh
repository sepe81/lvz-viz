#!/bin/sh
set -e
if [ -z $HOST_IP ]; then
  export HOST_IP="0.0.0.0"
fi
if [ -z $JMX_PORT ]; then
  export JMX_PORT=1099
fi

# see https://github.com/cstroe/java-jmx-in-docker-sample-app
JMX_PARAMS="-Djava.rmi.server.hostname=$HOST_IP \
  -Djava.security.egd=file:/dev/./urandom \
  -Dcom.sun.management.jmxremote \
  -Dcom.sun.management.jmxremote.host=$HOST_IP \
  -Dcom.sun.management.jmxremote.port=$JMX_PORT \
  -Dcom.sun.management.jmxremote.rmi.port=$JMX_PORT \
  -Dcom.sun.management.jmxremote.local.only=false \
  -Dcom.sun.management.jmxremote.authenticate=false \
  -Dcom.sun.management.jmxremote.ssl=false"

if [ "$1" = "java" ]; then
  java $JMX_PARAMS $JAVA_OPTS -XX:MaxMetaspaceSize=96m -jar ./app.jar
fi
exec "$@"
