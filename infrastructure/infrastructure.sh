#!/bin/bash

case "$1" in
    "up")
        mvn -pl ipaas.infrastructure clean install -Pinfrastructure
        mvn -pl ipaas.infrastructure docker-compose:up -Pinfrastructure
        ;;
    "down")
        mvn -pl ipaas.infrastructure docker-compose:down -Pinfrastructure
        ;;
    *)
        echo "Usage: $0 {up|down}"
        exit 1
        ;;
esac