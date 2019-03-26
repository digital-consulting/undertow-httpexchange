# Undertow httpexchange application

### How to run with local docker:

The default run config is presented in plugin documentation.
For undertow example I create a custom run config:
* mvn package -> will run also docker:build and docker:push
* mvn install -> will run package task (with included docker commands) and docker:start
* to stop current running container use docker:stop


Make sure you have a Docker running on your local machine
Configure image name <name><DOCKER_HUB>/<IMAGE_NAME>:<VERSION_TAG></name> with your Docker hub URL
Configure <authConfig> with your Docker hub credentials