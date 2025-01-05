---
# Page settings
layout: default
keywords:
comments: false

# Hero section
title: Configuration Guide
description: How to configure UTGen to work with your project.

# Micro navigation
micro_nav: true

# Page navigation
page_nav:
    prev:
        content: Previous page
        url: '/getting-started'
    next:
        content: Next page
        url: '/docker'
---

# Client Configuration
You can configure the client to work with best with your project by using different parameter values.
We have provided a list of parameters that you can use to configure the client.
## Parameters

| Parameter    | Description               | Default Value                 |
|:-------------|:--------------------------|:------------------------------|
| `--example`  | some example description  | `hello I am under the water`  |


# Server Configuration
For the server configuration you can choose to either run the server locally or use the HuggingFace server.
## Running with Ollama (local)
When running the server locally you should keep in mind that you need to have the server running the background.
This will be in addition to the client, therefore, you will need rather powerful hardware depending on the size of the project and model you are using.

the default mode of the server is to run locally. So you do not need to change anything.

First you will need to install ollama on your machine. You can do this by running the following command:
```bash
curl -fsSL https://ollama.com/install.sh | sh
```

Once you have installed ollama you can start the server by running the following command:
```bash
strawberry server main
```

This will start the server such that you can now run the client to generate unit tests.
