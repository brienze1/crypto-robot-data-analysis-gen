![Coverage](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/brienze1/crypto-robot-data-analysis-gen/main/.github/badges/jacoco.json)

# Crypto Data Analysis Generator

<div id="top"></div>


1. [About the Project](#about-the-project)
    1. [Input](#input)
    2. [Output](#output)
    3. [Rules](#rules)
    4. [Built With](#built-with)
        1. [Dependencies](#dependencies)
        2. [Test Dependencies](#test-dependencies)
        3. [Plugins](#plugins)
    5. [Roadmap](#roadmap)
2. [Getting Started](#getting-started)
    1. [Prerequisites](#prerequisites)
    2. [Installation](#installation)
    3. [Requirements](#requirements)
        1. [Deploying Local Infrastructure](#deploying-local-infrastructure)
    4. [Usage](#usage)
        1. [Manual Input](#manual-input)
        2. [Docker Input](#docker-input)
    5. [Testing](#testing)
3. [About Me](#about-me)

## About the Project

The objective of this challenge is to implement a crypto analysis data generator that will trigger the crypto robot
system. It is created using Kotlin using Spring Boot framework. Data is generated over defined time steps (1m, 5m, 15m,
30m, 1h, 6h and 1d).

### Input

The process is triggered via scheduled tasks so no input is required.

### Output

The output generated after the analysis should be an event message sent via AWS SNS `cryptoAnalysisTopic` topic.
This message will trigger the lambda handler to perform the service. Data should come in intervals of analysis, each
interval should have two types of analysis (initially, this could improve in the future) simple moving average
analysis (SMA) and exponential moving average analysis (EMA), each of them will have 6 periods of data analysed. The
analysis will have an indicator for the period evaluated this can range from a STRONG_SELL up to a STRONG_BUY
indication.

The intervals will be generated as:

- ONE_MINUTE
- FIVE_MINUTES
- FIFTEEN_MINUTES
- THIRTY_MINUTES
- ONE_HOUR
- SIX_HOURS
- ONE_DAY

Each interval will analyse the data according to these periods:

- 5
- 10
- 20
- 50
- 100
- 200

Analysis indicators:

- STRONG_BUY
- BUY
- NEUTRAL
- SELL
- STRONG_SELL

Example of how the message should look like:

```json
{
  "interval": "SIX_HOURS",
  "analysisData": {
    "simpleMovingAverages": [
      {
        "period": 5,
        "value": 21720.034,
        "indicator": "NEUTRAL"
      },
      {
        "period": 10,
        "value": 21477.829,
        "indicator": "NEUTRAL"
      },
      {
        "period": 20,
        "value": 21011.074,
        "indicator": "STRONG_BUY"
      },
      {
        "period": 50,
        "value": 20840.5982,
        "indicator": "BUY"
      },
      {
        "period": 100,
        "value": 20521.7347,
        "indicator": "BUY"
      },
      {
        "period": 200,
        "value": 23285.4764,
        "indicator": "NEUTRAL"
      }
    ],
    "exponentialMovingAverages": [
      {
        "period": 5,
        "value": 21480.44981,
        "indicator": "NEUTRAL"
      },
      {
        "period": 10,
        "value": 21219.48731,
        "indicator": "BUY"
      },
      {
        "period": 20,
        "value": 20778.21281,
        "indicator": "BUY"
      },
      {
        "period": 50,
        "value": 20619.44775,
        "indicator": "NEUTRAL"
      },
      {
        "period": 100,
        "value": 20698.25329,
        "indicator": "STRONG_BUY"
      },
      {
        "period": 200,
        "value": 25140.26348,
        "indicator": "NEUTRAL"
      }
    ]
  }
}
```

### Rules

Here are some rules that need to be implemented in this application.

Implemented:

- Data should be gathered from Binance API
- Data retrieved should be always the newest data possible
- Generated analysis should be sent via SNS topic event

### Built With

This application is build with Kotlin with Spring Boot framework. Code is tested, build and a Dockerfile image is sent
to Docker HUB every push made into the main branch using GitHub actions.

The project was created using Clean Architecture and is divided into 4 modules:

* Application -> Project bean configuration and application properties
* Delivery -> Composes the scheduler that triggers the process and also error handlers
* Domain -> Responsible for the domain rules and models
* Integration -> Responsible for the integration with the external infrastructure (SNS, Binance API)

Local environment is created using localstack for testing purposes
using [crypto-robot-localstack](https://github.com/brienze1/crypto-robot-localstack).

Note: Profiles can be used to configure the application for every environment.

#### Dependencies

* [Kotlin](https://kotlinlang.org/docs/home.html) Used to create code in Kotlin
* [Spring Boot](https://spring.io/projects/spring-boot) Used as framework for application
* [Spring Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html) Used to create health
  check endpoints
* [Spring AOP](https://www.baeldung.com/spring-aop) Used to integrate aspect oriented programing with error handlers
* [Spring WebFlux](https://www.baeldung.com/spring-aop) Used to integrate web related dependencies for API calls
* [SNS](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/examples-simple-notification-service.html) Used
  to integrate with AWS SNS
* [Gson](https://github.com/google/gson) Used as Json parser

#### Test Dependencies

* [Junit5](https://github.com/junit-team/junit5): Used to run unit tests (Comes integrated with spring starter test)
* [Junit4](https://github.com/junit-team/junit4): Used to run integration tests
* [Mockito](https://github.com/mockito/mockito): Used to generate mocks used in unit tests (Comes integrated with spring
  starter test)
* [MockK](https://mockk.io/): Used to generate mocks used in unit tests
* [AssertJ](https://assertj.github.io/doc/): Used to make assertions during tests
* [Cucumber](https://cucumber.io/docs/installation/java/): Used to integrate Gherkin with Java and run integration tests
* [Spring Test](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test): Spring test
  dependencies
* [Spring Web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web): Used to create
  Binance API mock during integration tests

#### Plugins

* [Kotlin Maven Plugin](https://kotlinlang.org/docs/all-open-plugin.html): Used to compile Kotlin code
* [Spring Boot Maven Plugin](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/): Used
  to package spring boot application into runnable jar
* [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/): Used to configure test logs
* [Maven Failsafe Plugin](https://maven.apache.org/surefire/maven-failsafe-plugin/): Used to configure tests

### Roadmap

* [X] Implement Behaviour tests (BDD) with cucumber
* [X] Implement Unit tests
* [X] Implement application logic
* [X] Add maven wrapper to run mvn commands locally
* [X] Create Dockerfile
* [X] Create Docker compose for local infrastructure
* [X] Document everything in Readme
* [X] Add timestamp to the event
* [X] Change periods from string to number in events
* [ ] Add logger lib and logs

<p align="right">(<a href="#top">back to top</a>)</p>

## Getting Started

### Prerequisites

- Install version 18 of the JDK
  - [Manual](https://adoptium.net/?variant=openjdk18)
  - [IntelliJ](https://www.jetbrains.com/help/idea/sdk.html#jdk)
  - [Homebrew](https://docs.brew.sh/Installation)
    ```bash
    brew install openjdk@18
    ```

- Set the `JAVA_HOME` environment variable to the path where your JDK is located
  - [Windows](https://confluence.atlassian.com/doc/setting-the-java_home-variable-in-windows-8895.html)
  - macOS/Linux/WSL
    ```bash
    echo 'export JAVA_HOME=/path/to/jdk' >> ~/.bashrc 
    ```

- Install Docker (Optional)
  - [Windows/macOS/Linux/WSL](https://www.docker.com/get-started/)

### Installation

- Run the following to install dependencies and compile the project:
  - Windows
    ```bash
    mvnw.bat clean install
    ```
  - macOS/Linux/WSL
    ```bash
    ./mvnw clean install
    ```

### Requirements

To run the application locally, first a local infrastructure needs to be deployed

#### Deploying Local Infrastructure

- Start the required infrastructure via localstack using docker compose command:
  - Windows/macOS/Linux/WSL
    ```bash
    docker-compose up
    ```

- To stop localstack:
  - Windows/macOS/Linux/WSL
    ```bash
    docker-compose down
    ```

### Usage

#### Manual Input

- Start the compiled application:
  - Windows/macOS/Linux/WSL
    ```bash
    java -jar ./application/target/crypto-robot-analysis-generator.jar --spring.profiles.active=local
    ```

- To stop the application just press Ctrl+C

#### Docker Input

- In case you want to use a Docker container to run the application first you need to build the Docker image from
  Dockerfile:
- Note: This may take a while to build (close to 5 min)
  - Windows/macOS/Linux/WSL
    ```bash
    docker build -t crypto-robot-data-analysis-gen .
    ```

- And then run the new created image:
  - Windows/macOS/Linux/WSL
    ```bash
    docker run --network="host" -d -it -e SPRING_PROFILES_ACTIVE=docker crypto-robot-data-analysis-gen:latest bash -c \
    "java -jar application/target/crypto-robot-analysis-generator.jar --spring.profiles.active=docker"
    ```

### Testing

- To run the tests just type the command bellow in terminal:
  - Windows
    ```bash
    mvnw.bat test
    ```
  - macOS/Linux/WSL
    ```bash
    ./mvnw test
    ```

- To run lint just type the command bellow in terminal:
  - Windows
    ```bash
    mvnw.bat antrun:run@ktlint-format
    ```
  - macOS/Linux/WSL
    ```bash
    ./mvnw antrun:run@ktlint-format
    ```

### Known issues

- docker error command not found "\r", fix:
  - Open your shell file on NotePad++
  - Click on Edit on Top bar menu, then choose EOL Conversion --> Unix(LF)
  - Save the file.

<p align="right">(<a href="#top">back to top</a>)</p>

## About me

Hello! :)

My name is Luis Brienze, and I'm a Software Engineer.

I focus primarily on software development, but I'm also good at system architecture, mentoring other developers,
etc... I've been in the IT industry for 4+ years, during this time I worked for companies like Itau, Dock, Imagine
Learning and
EPAM.

I graduated from UNESP studying Automation and Control Engineering in 2022, and I also took multiple courses on Udemy
and Alura.

My main stack is Java, but I'm also pretty good working with Kotlin and TypeScript (both server side).
I have quite a good knowledge of AWS Cloud, and I'm also very conformable working with Docker.

During my career, while working with QA's, I've also gained alot of valuable experience with testing applications in
general from unit/integrated
testing using TDD and BDD, to performance testing apps with JMeter for example.

If you want to talk to me, please fell free to reach me anytime at [LinkedIn](https://www.linkedin.com/in/luisbrienze/)
or [email](lfbrienze@gmail.com).

<p align="right">(<a href="#top">back to top</a>)</p>