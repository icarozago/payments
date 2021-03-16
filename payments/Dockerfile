FROM sapmachine/jdk11
RUN mkdir /workspace
WORKDIR /workspace
COPY target/payments-0.0.1-SNAPSHOT.jar .
CMD java -jar payments-0.0.1-SNAPSHOT.jar