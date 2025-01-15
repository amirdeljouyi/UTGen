# Replication Package: Leveraging Large Language Models for Enhancing the Understandability of Generated Unit Tests

This is the replication package for the research project "Leveraging Large Language Models for Enhancing the Understandability of Generated Unit Tests". This guide will walk you through setting up and using the tools involved in our study, including a LLM server and the UTGen client.

## The Replication Package Directory Structure

#### Experiment
The `Experiment` directory holds all materials related to the controlled experiment conducted in this research. It includes:
- Participants’ Results: Individual participants' test cases were conducted.
- Instructions and Guidelines: The materials were provided to participants before the study.
- Workspaces: Configurations and setups used by participants during the experiments.

#### Methodology
The `Methodology` directory provides a detailed guide on how to select the two projects used for the controlled experiment.

#### Results
The `Results` directory contains the test cases generated during the study using the DynaMosa dataset. The dataset used can be accessed in the [UTGen replication package dataset repository](https://github.com/amirdeljouyi/UTGen-replication-package-dataset).

#### Implementation
The following directories contain the implementation code and configurations:
- The `LLM-Server` directory includes the complete codebase for the LLM-Server component.
- The `UTGen-Client` directory contains the codebase for the UTGen-Client component.
- The `Docker` contains the configurations required to run UTGen for RQ1 using Docker. It includes Docker Compose files and setup scripts to streamline deployment and execution.

To start using UTGen, follow the steps outlined in the "Getting Started" section below to set up and run each component.

---

## Getting Started

This section provides instructions on how to prepare your environment for running the tools used in our research.

### Prerequisites

- Docker
- Python 3.10 or newer
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

The dataset has been used in this research can be found [in this repository](https://github.com/amirdeljouyi/UTGen-replication-package-dataset).

### Setting Up the LLM Server

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

### Setting Up UTGen
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
### How to run the experiment with Docker Compose

Ensure Docker is configured with at least 10GB of RAM to handle the tasks in the containers and run the LLM.

1.	Start the LLM Server
Run the following command to start ollama and llm-server service:

```bash
docker-compose -f docker-compose.yml start ollama llm-server
```

2.	Start the UTGen Client
Once the llm-server service is running, and the model is running, you can start the utgen-client service:

```bash
docker-compose -f docker-compose.yml start utgen-client
```
