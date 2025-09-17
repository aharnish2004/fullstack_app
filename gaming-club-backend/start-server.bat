@echo off
echo Starting Gaming Club Backend Server...
echo.
echo Checking Java version...
java -version
echo.
echo Compiling project...
call .\mvnw.cmd clean compile
if %errorlevel% neq 0 (
    echo Compilation failed! Please check the errors above.
    pause
    exit /b 1
)
echo.
echo Starting Spring Boot application...
echo Server will be available at: http://localhost:8080
echo Health check: http://localhost:8080/actuator/health
echo.
call .\mvnw.cmd spring-boot:run
pause
