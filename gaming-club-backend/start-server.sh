#!/bin/bash
echo "Starting Gaming Club Backend Server..."
echo ""
echo "Checking Java version..."
java -version
echo ""
echo "Compiling project..."
./mvnw clean compile
if [ $? -ne 0 ]; then
    echo "Compilation failed! Please check the errors above."
    exit 1
fi
echo ""
echo "Starting Spring Boot application..."
echo "Server will be available at: http://localhost:8080"
echo "Health check: http://localhost:8080/actuator/health"
echo ""
./mvnw spring-boot:run
