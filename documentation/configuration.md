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

Customize how LLM is utilized during the test generation process with the following adjustable properties:

| Property                                | Description                                                                                       | Default Value                |
|:----------------------------------------|:--------------------------------------------------------------------------------------------------|:-----------------------------|
| `LLM_TEST_DATA`                         | Enables LLMs to enhance test data generation.                                                     | `true`                       |
| `LLM_POST_PROCESSING_REPROMPT_BUDGET`   | Sets the number of additional prompts used for improving test cases during post-processing.        | `2`                          |
| `LLM_POST_PROCESSING`                   | Uses LLMs to refine the readability and clarity of test cases by improving identifiers and comments.| `true`                       |
| `LLM_GRAPHQL_ENTRYPOINT`                | Specifies the entrypoint URL for connecting to the utgen-llm-server.                               | `0.0.0.0:8000/graphql`       |
| `TEST_NAMING_STRATEGY`                  | For LLM-based improvements to test names, set this to LLM_BASED.                                   | `numbered`                   |
| `Test_Format`                           | To leverage LLMs at any stage of the process, set this to JUNIT5LLM.                               | `JUNIT5LLM`                  |
| `Reformat`                              | Automatically runs a prettifier to format the generated test cases.                                | `true`                       |


# Server Configuration
For the server configuration you can currently only configure the server to run locally or on Ollama.
We aim to provide more configuration options in the future, such as support for huggingface 🤗.
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
