FROM python:3.8-slim AS llm-server
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

# Install NVIDIA Container Toolkit
RUN curl -s -L https://nvidia.github.io/nvidia-container-runtime/gpgkey | apt-key add - && \
    distribution=$(. /etc/os-release; echo $ID$VERSION_ID) && \
    curl -s -L https://nvidia.github.io/nvidia-container-runtime/$distribution/nvidia-container-runtime.list | tee /etc/apt/sources.list.d/nvidia-container-runtime.list && \
    apt-get update && apt-get install -y nvidia-container-toolkit && \
    apt-get clean

# Configure NVIDIA Container Runtime
RUN nvidia-ctk runtime configure && systemctl restart docker

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

# Expose ports
EXPOSE 8000

### Default command to start the LLM server
CMD ["bash", "-c", "source .env/bin/activate && ./run-docker-server.sh"]