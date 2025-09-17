@echo off
echo ========================================
echo Gaming Club API - Complete Endpoint Test
echo ========================================
echo.

echo Testing all endpoints...
echo.

echo 1. Root endpoint (/)
curl -s http://localhost:8080/
echo.
echo.

echo 2. Test endpoint (/test)
curl -s http://localhost:8080/test
echo.
echo.

echo 3. Database test endpoint (/db-test)
curl -s http://localhost:8080/db-test
echo.
echo.

echo 4. Status endpoint (/status)
curl -s http://localhost:8080/status
echo.
echo.

echo 5. API info endpoint (/api)
curl -s http://localhost:8080/api
echo.
echo.

echo 6. Health check (/actuator/health)
curl -s http://localhost:8080/actuator/health
echo.
echo.

echo 7. Members API (/api/members)
curl -s http://localhost:8080/api/members
echo.
echo.

echo 8. Games API (/api/games)
curl -s http://localhost:8080/api/games
echo.
echo.

echo 9. Transactions API (/api/transactions)
curl -s http://localhost:8080/api/transactions
echo.
echo.

echo 10. Recharges API (/api/recharges)
curl -s http://localhost:8080/api/recharges
echo.
echo.

echo ========================================
echo Test Complete
echo ========================================
echo.
echo Each endpoint should return different responses.
echo If you see the same response for all endpoints, there's a routing issue.
echo.
pause
