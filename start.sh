#!/bin/bash

# Function to check if Docker is running
check_docker() {
    if ! docker info > /dev/null 2>&1; then
        echo "Error: Docker is not running. Please start Docker and try again."
        exit 1
    fi
}

echo "Checking Docker status..."
check_docker

# Determine which docker compose command to use
if command -v docker-compose &> /dev/null; then
    DOCKER_COMPOSE="docker-compose"
elif docker info | grep -q "Client:"; then 
    # Try 'docker compose' anyway, sometimes version check fails but command works? 
    # No, if 'docker compose' failed, it failed.
    # Let's assume if docker is installed, maybe the user needs to install the plugin.
    if docker compose version &> /dev/null; then
        DOCKER_COMPOSE="docker compose"
    else
        echo "Warning: 'docker-compose' or 'docker compose' not found."
        echo "Attempting to use 'docker-compose' anyway..."
        DOCKER_COMPOSE="docker-compose"
    fi
else
     DOCKER_COMPOSE="docker-compose"
fi

echo "Using command: $DOCKER_COMPOSE"

echo "Starting Backend and Database..."
cd kindergarten
$DOCKER_COMPOSE up -d --build

if [ $? -ne 0 ]; then
    echo "Error: Failed to start backend containers. Make sure Docker Compose is installed."
    exit 1
fi

echo "Backend is starting up... (Waiting for 10 seconds)"
sleep 10

cd ..

echo "Installing Frontend dependencies..."
npm install

echo "Starting Frontend..."
echo "The application will be available at http://localhost:3000"
npm run dev
