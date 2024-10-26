#!/bin/bash

# Stop any existing containers and remove volumes
echo "Stopping existing containers..."
mvn -pl ipaas.infrastructure docker-compose:down

# Build and start everything with infrastructure
echo "Building and starting all services..."
mvn clean install -Plocal