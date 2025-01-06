FROM python:3.10 AS llm-server
LABEL authors="amirdeljouyi"
# Set environment variables
ENV PYTHONDONTWRITEBYTECODE=1
ENV PYTHONUNBUFFERED=1

# Install system dependencies
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    curl \
    gnupg \
    docker.io \
    && apt-get clean

# Set up application environment
WORKDIR /app

# Clone the LLM-Server repository
RUN git clone https://github.com/amirdeljouyi/UTGen-LLM-server.git LLM-Server

WORKDIR /app/LLM-Server

RUN apt-get update && apt-get install -y \
    build-essential \
    libffi-dev \
    libssl-dev \
    python3-dev \
    gcc

## Install Python dependencies in a virtual environment
RUN python -m venv .env && \
    . .env/bin/activate

RUN python -m pip install --no-cache-dir -r requirements.txt
RUN chmod 777 ./run-server.sh
RUN chmod 777 ./run-docker-server.sh

## Load the codellama:7b-instruct model in Ollama
#RUN docker exec ollama ollama pull codellama:7b-instruct

# Expose ports
EXPOSE 8000

### Default command to start the LLM server
CMD ["bash", "-c", "source .env/bin/activate && ./run-docker-server.sh"]