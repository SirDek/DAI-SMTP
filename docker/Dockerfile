FROM openjdk:11-jre
LABEL authors="Cedric Centeno, Laetitia Guidetti"

RUN apt-get update && \
  apt-get clean

# Copy the executable jar in the image file system.
COPY MockMock-1.4.1-SNAPSHOT.one-jar.jar /opt/app/MockMock.jar

# Port for SMTP server
EXPOSE 25
# Port for http (web interface)
EXPOSE 8282

# Executed comment when the Docker container starts
CMD java -jar /opt/app/MockMock.jar

	