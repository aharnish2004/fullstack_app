@echo off
echo Testing Gaming Club API Endpoints...
echo.

echo Waiting for server to start...
timeout /t 15 /nobreak > nul

echo.
echo ========================================
echo Testing Basic Endpoints
echo ========================================

echo.
echo 1. Testing root endpoint...
curl -s http://localhost:8080/ | findstr "message"
if %errorlevel% neq 0 (
    echo ERROR: Root endpoint failed
) else (
    echo SUCCESS: Root endpoint working
)

echo.
echo 2. Testing server test endpoint...
curl -s http://localhost:8080/test | findstr "status"
if %errorlevel% neq 0 (
    echo ERROR: Test endpoint failed
) else (
    echo SUCCESS: Test endpoint working
)

echo.
echo 3. Testing database connection...
curl -s http://localhost:8080/db-test | findstr "status"
if %errorlevel% neq 0 (
    echo ERROR: Database test failed
) else (
    echo SUCCESS: Database test working
)

echo.
echo 4. Testing health check...
curl -s http://localhost:8080/actuator/health | findstr "status"
if %errorlevel% neq 0 (
    echo ERROR: Health check failed
) else (
    echo SUCCESS: Health check working
)

echo.
echo 5. Testing Members API...
curl -s http://localhost:8080/api/members
if %errorlevel% neq 0 (
    echo ERROR: Members API failed
) else (
    echo SUCCESS: Members API working
)

echo.
echo ========================================
echo Test Complete
echo ========================================
echo.
echo If all tests show SUCCESS, your API is working!
echo If any show ERROR, check the server logs.
echo.
pause
