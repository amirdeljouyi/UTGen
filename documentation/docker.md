---
# Page settings
layout: default
keywords:
comments: false

# Hero section
title: Running UTGen (Docker)
description: A guide to running UTGen using Docker. (replication of the original paper)

# Micro navigation
micro_nav: true

# Page navigation
page_nav:
    prev:
        content: Previous page
        url: '/configuration'
    next:
        content: Next page
        url: '/resources'
---

# How to use the docker image
The UTGen Docker files can be found under the `Docker` directiory of the UTGen repository. The Docker image is built using the `Dockerfile` and the `docker-compose.yml` file is used to run the experiment.

We have multiple variants of the Docker image:
1. `client.11.Dockerfile` - This Dockerfile is used to build the UTGen client image with the version 11 of the OpenJDK.
2. `client.8.Dockerfile` - This Dockerfile is used to build the UTGen client image with the version 8 of the OpenJDK.
3. `llm-server.cpu.Dockerfile` - This Dockerfile is used to build the LLM Server image with the CPU compute engine.
4. `llm-server.nvidia.Dockerfile` - This Dockerfile is used to build the LLM Server image with the Nvidia GPU compute engine.
5. `docker-compose.yml` - This file is used to run the experiment with the UTGen client and the LLM Server.
6. `docker-compose.nvidia.yml` - This file is used to run the experiment with the UTGen client and the LLM Server with the Nvidia GPU compute engine.

## How to build the Docker image

### Start the LLM Server

Run the following command to start the ollama llm-server service:

```bash
docker-compose -f docker-compose.yml start ollama llm-server
```

### Start the UTGen Client
   
Once the llm-server service is running, and the model is running, you can start the utgen-client service:

```bash
docker-compose -f docker-compose.yml start utgen-client
```