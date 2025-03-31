#!/bin/bash
#bash script for our UNIX-user :)
clear
echo "-------DB Build script-------"
date

#colors for colorized output.
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m'

#region set environment sources -> .env
if [ -f .env ]; then
  echo -e "$GREEN ✔ $NC .env file found"
  #set .env file as a source for environment Variables
  source .env
else
  echo -e "$RED ✕ ERROR: $NC .env file not found!">&2
  echo -e "$BLUE ✕ FIX: $NC Please Change the default credentials in .env.example and rename it to .env">&2
  exit 1
fi
#endregion


#region check environment variables
if [[ -n "$POSTGRES_JDBC_URL" ]]; then
  echo -e "$GREEN ✔ $NC JDBC URL found"
  else
    echo -e "$RED ✕ ERROR: $NC JDBC URL not found">&2
    exit 1
fi
if [[ -n "$POSTGRES_JDBC_USER" ]]; then
  echo -e "$GREEN ✔ $NC JDBC USER found"
  else
       echo -e "$RED ✕ ERROR: $NC JDBC USER not found">&2
       exit 1
fi
if [[ -n "$POSTGRES_PASSWORD" ]]; then
  echo -e "$GREEN ✔ $NC JDBC PASSWORD found"
   else
         echo -e "$RED ✕ ERROR: $NC JDBC PASSWORD not found">&2
         exit 1
fi
if [[ -n "$POSTGRES_PORT" ]]; then
  echo -e "$GREEN ✔ $NC POSTGRES PORT found"
   else
         echo -e "$RED ✕ ERROR: $NC POSTGRES PORT not found">&2
         exit 1
fi
if [[ -n "$POSTGRES_PORT_VIRTUELL" ]]; then
  echo -e "$GREEN ✔ $NC POSTGRES PORT VIRTUELL found"
   else
         echo -e "$RED ✕ ERROR: $NC POSTGRES PORT VIRTUELL not found">&2
         exit 1
fi
#endregion


#region create and run database


if [ -f DockerFile ]; then
  echo -e "$GREEN ✔ $NC DockerFile found"
else
  echo -e "$RED ✕ ERROR: $NC DockerFile not found!">&2
  exit 1
fi


if [ -f docker-compose.yml ]; then
  echo -e "$GREEN ✔ $NC docker-compose file found"
else
  echo -e "$RED ✕ ERROR: $NC docker-compose file not found!">&2
  exit 1
fi

if [ -f init.sql ]; then
  echo -e "$GREEN ✔ $NC init.sql file found"
else
  echo -e "$RED ✕ ERROR: $NC init.sql file not found!">&2
  exit 1
fi

#start docker
open -a Docker
#pull the latest release of postgresql
docker pull bitnami/postgresql:latest
# await docker start
while ! docker info > /dev/null 2>&1; do
  echo "wait for Docker..."
  sleep 2
done

#use docker-compose file to build image
docker-compose build
#start the container (-d ≙ runs in the background)
docker-compose up -d
#endregion

#region check docker container available
Containerid=$(docker inspect -f '{{.Id}}' BinaryBunker-postgres)
if [ -z "$Containerid" ]; then
    echo -e "$RED ✕ ERROR: $NC Container not found">&2
    exit 1
else
    echo -e "$GREEN ✔ $NC Container-ID: $Containerid"
fi
#take the first 12 letters of the Containerid
ContainerIdshort=${Containerid:0:12}

#if we can see the status of the container
if docker ps --format "{{.ID}}" | grep -v "CONTAINER ID" | grep $ContainerIdshort; then
  echo -e "$GREEN ✔ $NC DB Container found"
else
  #error output(stderr) with: ">&2"
  echo -e "$RED ✕ ERROR: $NC Container not found $NC">&2
  exit 1
fi
#endregion
#potential extension: ssl, read only, single database mode, single session mode, disconnection after no keep alive,sleep mode on long inactivity.