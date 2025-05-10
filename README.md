Running Selenium Tests on Dockerized Grid via GitHub Actions: Step-by-Step Guide

This guide outlines the entire process to run Selenium test automation using Docker, Selenium Grid, and GitHub Actions.

✅ Objective

To run Selenium test suites inside a Java-only container that connects to a Dockerized Selenium Grid (Hub + Browser Nodes), and upload test results as GitHub Actions artifacts.

📘 Explanation of the Workflow Steps

1. Packaging the Project

We use Maven to build the automation code and package it into a JAR file. Along with it, all dependencies (including Selenium, TestNG, etc.) are copied into a libs/ directory using Maven's dependency plugin. These files are later used inside the Docker test container to execute tests.

2. Creating Docker Network

Before starting containers, a custom Docker network called grid is created (if not already existing). This allows all the containers (hub, nodes, test runner) to communicate with each other by name.

3. Starting Selenium Grid

We use a Docker Compose file to spin up the Selenium Grid — which includes a Hub and browser nodes (Chrome, Firefox). These containers are attached to the grid network. The Hub receives test commands and routes them to the appropriate node.

4. Building the Java Test Runner Image

A lightweight custom Docker image is built using a Dockerfile. It includes only:

Java runtime

Test JARs and dependencies in /project-package/libs

TestNG suite files in /project-package/test-suites

A shell script (runner.sh) to run the tests

This image does NOT include browsers — it will connect remotely to the Selenium Grid.

5. Running Tests in the Container

The test runner container is started with environment variables like:

HUB_HOST — the hostname of the Selenium Hub

BROWSER — browser to use (chrome/firefox)

THREAD_COUNT — how many tests to run in parallel

TEST_SUITE — which TestNG suite XML to execute

This container:

Connects to the Grid hub using the hostname (e.g., hub)

Runs the tests via TestNG

Saves logs or results into a shared volume mounted to a folder called testCaseResults

6. Uploading Artifacts

After test execution, the testCaseResults/ folder (on the GitHub Actions host) is uploaded as an artifact. This folder may contain logs, screenshots, or test reports.

7. Tearing Down Selenium Grid

After everything finishes, the Selenium Grid containers are shut down to free resources.

✅ Result

Your tests run inside a clean Java container

Browsers execute inside Dockerized Chrome/Firefox containers

Tests connect to Selenium Grid hub (http://hub:4444/wd/hub)

TestNG XML specifies what to run

Results are saved to testCaseResults/ and uploaded to GitHub as artifacts

✅ You now have a fully automated and isolated Selenium execution pipeline with GitHub Actions and Docker!

