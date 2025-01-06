# Base image
FROM eclipse-temurin:8u362-b09-jdk-jammy AS utgen-client
LABEL authors="amirdeljouyi"
ENV DEBIAN_FRONTEND=noninteractive

WORKDIR /app

RUN apt-get update && apt-get install -y git && apt-get clean

RUN git clone https://github.com/amirdeljouyi/UTGen-replication-package-dataset.git dataset

WORKDIR /app/dataset

# Ensure run-utestgen.sh is executable
RUN chmod +x ./run-utestgen.docker.sh
RUN chmod +x ./run-experiment.sh

CMD ["bash", "-c", "./run-experiment.sh"]