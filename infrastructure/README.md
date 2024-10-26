# Infrastructure Module

![Status](https://img.shields.io/badge/Status-Development-blue)
![Version](https://img.shields.io/badge/Version-1.0.0-green)

## Table of Contents
- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Directory Structure](#directory-structure)
- [Service Configuration](#service-configuration)
- [Maven Configuration](#maven-configuration)
- [Usage Guide](#usage-guide)
- [Best Practices](#best-practices)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

## Overview
This module provides a containerized local development environment with essential services including PostgreSQL, RabbitMQ, and Keycloak. The infrastructure is managed through Docker Compose and automated using Maven plugins.

## Prerequisites
- Docker 20.10.x or newer
- Docker Compose V2
- Maven 3.8.x or newer
- JDK 17 or newer
- At least 4GB of available RAM

## Directory Structure
```
infrastructure/
├── pom.xml                              # Maven configuration
├── src/
│   └── main/
│       └── resources/
│           └── db/
│               └── 01-init-keycloak-db.sql  # Keycloak database initialization
├── docker/
│   ├── docker-compose.yml               # Service definitions
│   └── config/
│       └── database/                    # Mounted SQL scripts
```

## Service Configuration

### PostgreSQL Database
```yaml
postgres:
  image: postgres:15-alpine
  ports:
    - "5432:5432"
  environment:
    - POSTGRES_USER=ipaas_admin_user
    - POSTGRES_DB=ipaas
```
- **Port:** 5432
- **Default User:** ipaas_admin_user
- **Initial Database:** ipaas
- **Mounted Volumes:** 
  - `./config/database/` → `/docker-entrypoint-initdb.d/`

### RabbitMQ Message Broker
```yaml
rabbitmq:
  image: rabbitmq:3-management-alpine
  ports:
    - "5672:5672"
    - "15672:15672"
  environment:
    - RABBITMQ_DEFAULT_USER=ipaas_broker_user
```
- **Ports:** 
  - 5672 (AMQP)
  - 15672 (Management UI)
- **Default User:** ipaas_broker_user
- **Features:**
  - Management plugin enabled
  - Persistent message storage

### Keycloak Authentication Server
```yaml
keycloak:
  image: quay.io/keycloak/keycloak:21.1
  ports:
    - "9090:9090"
  environment:
    - KEYCLOAK_ADMIN=ipaas_keycloak_admin
    - KC_DB_SCHEMA=ipaas_auth
```
- **Port:** 9090
- **Admin User:** ipaas_keycloak_admin
- **Database Schema:** ipaas_auth

## Maven Configuration

### Key Plugins Configuration

1. **Directory Creation**
```xml
<plugin>
  <artifactId>maven-antrun-plugin</artifactId>
  <executions>
    <execution>
      <phase>validate</phase>
      <goals>
        <goal>run</goal>
      </goals>
      <configuration>
        <target>
          <mkdir dir="${project.build.directory}/docker-compose"/>
        </target>
      </configuration>
    </execution>
  </executions>
</plugin>
```

2. **Resource Copying**
```xml
<plugin>
  <artifactId>maven-resources-plugin</artifactId>
  <executions>
    <execution>
      <phase>validate</phase>
      <goals>
        <goal>copy-resources</goal>
      </goals>
      <configuration>
        <outputDirectory>${project.build.directory}/docker-compose</outputDirectory>
        <resources>
          <resource>
            <directory>docker</directory>
            <filtering>true</filtering>
          </resource>
        </resources>
      </configuration>
    </execution>
  </executions>
</plugin>
```

3. **Docker Compose Management**
```xml
<plugin>
  <groupId>com.dkanejs.maven.plugins</groupId>
  <artifactId>docker-compose-maven-plugin</artifactId>
  <executions>
    <execution>
      <id>up</id>
      <phase>verify</phase>
      <goals>
        <goal>up</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

## Usage Guide

### Basic Commands

1. **Start Infrastructure**
```bash
# From parent project
mvn clean verify

# From infrastructure module
cd infrastructure
mvn clean verify
```

2. **Stop Infrastructure**
```bash
# From infrastructure module
mvn clean
```

3. **Skip Infrastructure**
```bash
mvn clean verify -Pskip-infrastructure
```

### Health Checks
- PostgreSQL: `psql -h localhost -U ipaas_admin_user -d ipaas`
- RabbitMQ: http://localhost:15672 (management UI)
- Keycloak: http://localhost:9090/auth/admin

## Best Practices

### Database Initialization
- Place custom SQL scripts in `docker/config/database/`
- Follow naming convention: `XX-description.sql`
- Scripts execute in alphabetical order

### Service Dependencies
- Keycloak starts after PostgreSQL is healthy
- Applications should implement retry mechanisms

### Development Workflow
- Start infrastructure before running applications
- Use skip profile for CI/CD pipelines
- Keep SQL scripts idempotent

## Troubleshooting

### Port Conflicts
```bash
# Check for running services
netstat -tulpn | grep LISTEN

# Check Docker containers
docker ps

# Remove conflicting containers
docker rm -f $(docker ps -aq)
```

### Database Connection Issues
```bash
# Verify PostgreSQL container
docker ps | grep postgres

# Check logs
docker logs infrastructure-postgres-1

# Test connection
psql -h localhost -U ipaas_admin_user -d ipaas
```

### Keycloak Startup Problems
```bash
# Verify database schema
psql -h localhost -U ipaas_admin_user -d ipaas -c "\dn"

# Check Keycloak logs
docker logs infrastructure-keycloak-1
```

## Contributing

### Pull Request Process
1. Update the README.md with details of changes if applicable
2. Update the version tags if applicable
3. Follow the existing code style
4. Include relevant tests for new features

### Commit Message Convention
```
type(scope): subject

body

footer
```
- Types: feat, fix, docs, style, refactor, test, chore
- Scope: postgres, rabbitmq, keycloak, maven
- Subject: imperative mood, no period
- Body: motivation for change, contrast with previous behavior
- Footer: breaking changes, reference issues

---
For more information, please contact the infrastructure team.