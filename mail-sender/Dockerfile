FROM sapmachine/jdk11
RUN mkdir /workspace
WORKDIR /workspace
COPY target/mail-sender-0.0.1-SNAPSHOT.jar .
CMD java -jar mail-sender-0.0.1-SNAPSHOT.jar
