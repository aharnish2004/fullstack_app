@echo off
echo Testing Fixed Endpoints...
echo.

echo 1. Testing /api/members (should work)
curl -s http://localhost:8080/api/members
echo.
echo.

echo 2. Testing /api/games (should work now)
curl -s http://localhost:8080/api/games
echo.
echo.

echo 3. Testing /api/transactions (should work now)
curl -s http://localhost:8080/api/transactions
echo.
echo.

echo 4. Testing /api/recharges (should work now)
curl -s http://localhost:8080/api/recharges
echo.
echo.

echo 5. Testing /api/collections (should work)
curl -s http://localhost:8080/api/collections
echo.
echo.

echo 6. Testing /api/admin (should work)
curl -s http://localhost:8080/api/admin
echo.
echo.

echo All endpoints should return empty arrays [] now!
pause
