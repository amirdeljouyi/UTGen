---
# Page settings
layout: default
keywords:
comments: false

# Hero section
title: Getting Started
description: How to get started with using UTGen.

# Micro navigation
micro_nav: true

# Page navigation
page_nav:
    next:
        content: Next page
        url: '/configuration'
---

# What is UTGen?
 We're glad you asked! UTGen is a tool for automatically generating unit tests for Java code.
It is based on [EvoSuite](http://www.evosuite.org/), a popular tool for generating unit tests for Java code.
UTGen is a fork of EvoSuite that has been modified to generate tests that are more human-readable and easier to understand.
It achieves this by generation tests that are more similar to the way that a human would write them, with more descriptive names and comments.
UTGen uses a Large Language Model (LLM) to generate more understandable
 (1) test names, (3) variable names, and (3) comments, (4) and test data.

# What does it include?
UTGen includes a server and a client.
The client is responsible for sending requests to the server and receiving responses.
The client runs a modified version of EvoSuite that generates tests using the LLM.
The server is responsible for handling the requests from the client to the model; 
It handles the responses from the LLM and runs validations on the generated outputs to ensure that they are valid Java code.
The server additionally check that the model has not modified the code in such a way that it has semantically changed the code.

# How to install
To get started with UTGen, you will need to install the server and the client.
UTGen is also available as a Docker image, which makes it easy to get up and running quickly.
You can find more information on how to install UTGen in the [Docker Guide](/docker).

## Server
To install the server, you will need to clone the [UTGen-LLM-Server](https://github.com/amirdeljouyi/UTGen-LLM-server) repository from GitHub.

```bash
git clone git@github.com:amirdeljouyi/UTGen-LLM-server.git
```

You will also need to install the dependencies for the server.
To do this, first navigate to the `UTGen-LLM-server` directory and then run the following command:

```bash
pip install -r requirements.txt
```

## Client
To install the client, you will need to clone the [UTGen-Client](https://github.com/amirdeljouyi/UTGen-Client) repository from GitHub.

```bash
git clone git@github.com:amirdeljouyi/UTGen-Client.git
```