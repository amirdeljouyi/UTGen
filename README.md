# Replication Package: Leveraging Large Language Models for Enhancing the Understandability of Generated Unit Tests

This is the replication package for the research project "Leveraging Large Language Models for Enhancing the Understandability of Generated Unit Tests". This guide will walk you through setting up and using the tools involved in our study, including a LLM server and the UTGen client.

## Getting Started

This section provides instructions on how to prepare your environment for running the tools used in our research.

### Prerequisites

- Docker
- Python 3.6 or newer
- Java Development Kit (JDK) versions 8 and 11

### Clone the Repository

To begin, clone this repository to your local machine and unzip all relevant files within.

```bash
git clone <repository-url>
cd <repository-folder>
cd LLM-Server
unzip LLM-server.zip
```

or clone the [UTGen-LLM](https://github.com/amirdeljouyi/UTGen-LLM-server) and [UTGen-Client](https://github.com/amirdeljouyi/UTGen-Client) repositories.

## Setting Up the LLM Server

**1. Install Docker:** Ensure Docker is installed on your machine. Docker installation guides are available [here](https://docs.docker.com/engine/install/ubuntu/)

**2. Run ollama as a Docker Container:** Follow the [official ollama Docker image guide](https://ollama.com/blog/ollama-is-now-available-as-an-official-docker-image) to install and run ollama.

**3. Create a Python Virtual Environment:**
```bash
python -m venv .env
source .env/bin/activate
```

**4. Install Requirements:**
```bash
pip install -r requirements.txt
```

**5. Start the Server:**
```bash
./run-server
```

## Setting Up UTGen
**1. Install Java Versions:** Use [SDKMAN](https://sdkman.io/) to install the required Java versions.

**2. Prepare `classes.csv`:** Ensure the target directory for UTGen contains a `classes.csv` file with `project` and `class` headers.

**3. Project Requirements:** Each project should contain at least the following structure

* A `.jar` file of the project.
* An `evosuite-files` directory with:
    * `evosuite.properties` file.
    *  `inheritance.xml.gz` file.
* A `lib` directory with all dependent libraries

**4. Run UTGen:**
```bash
./run-utestgen.sh <DIR-PATH> <JAVA-VERSION: 8 OR 11> <USE-INHERITANCE: true OR false>
```
