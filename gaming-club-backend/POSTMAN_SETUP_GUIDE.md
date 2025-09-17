# Gaming Club API - Postman Setup Guide

## 🚀 Quick Start

### 1. Start Your Backend Server
```bash
cd gaming-club-backend
mvn spring-boot:run
```

### 2. Import Postman Collection
1. Open Postman
2. Click **Import** button
3. Select `postman/Gaming_Club_API_Collection.json`
4. Click **Import**

### 3. Import Environment
1. In Postman, click **Environments** tab
2. Click **Import**
3. Select `postman/Gaming_Club_Environment.json`
4. Click **Import**
5. Select "Gaming Club Environment" from dropdown

## 📋 API Endpoints Overview

### Members API (`/api/members`)
- `GET /api/members` - Get all members
- `GET /api/members/{id}` - Get member by ID
- `POST /api/members` - Create new member
- `PUT /api/members/{id}` - Update member
- `DELETE /api/members/{id}` - Delete member
- `GET /api/members/search` - Search members
- `GET /api/members/phone/{phone}` - Get member by phone

### Games API (`/api/games`)
- `GET /api/games` - Get all games
- `GET /api/games/{id}` - Get game by ID
- `POST /api/games` - Create new game
- `PUT /api/games/{id}` - Update game
- `DELETE /api/games/{id}` - Delete game
- `GET /api/games/search` - Search games
- `GET /api/games/price-range` - Get games by price range

### Transactions API (`/api/transactions`)
- `GET /api/transactions` - Get all transactions
- `GET /api/transactions/{id}` - Get transaction by ID
- `POST /api/transactions` - Create new transaction
- `GET /api/transactions/member/{memberId}` - Get transactions by member
- `GET /api/transactions/game/{gameId}` - Get transactions by game
- `GET /api/transactions/date-range` - Get transactions by date range

### Recharges API (`/api/recharges`)
- `GET /api/recharges` - Get all recharges
- `GET /api/recharges/{id}` - Get recharge by ID
- `POST /api/recharges` - Create new recharge
- `GET /api/recharges/member/{memberId}` - Get recharges by member
- `GET /api/recharges/date-range` - Get recharges by date range

### Collections API (`/api/collections`)
- `GET /api/collections` - Get all collections
- `GET /api/collections/{id}` - Get collection by ID
- `POST /api/collections` - Create new collection
- `GET /api/collections/date-range` - Get collections by date range
- `GET /api/collections/type/{type}` - Get collections by type

### Admin Users API (`/api/admin`)
- `GET /api/admin` - Get all admin users
- `GET /api/admin/{id}` - Get admin user by ID
- `POST /api/admin` - Create new admin user
- `POST /api/admin/login` - Admin login
- `GET /api/admin/username/{username}` - Get admin by username

### Health Check
- `GET /actuator/health` - Application health status
- `GET /actuator/info` - Application information

## 🔧 Testing Workflow

### Step 1: Health Check
1. Run **Application Health** request
2. Should return `{"status":"UP"}`

### Step 2: Create Test Data
1. **Create Member** - Use sample data provided
2. **Create Game** - Use sample data provided
3. **Create Admin User** - Use sample data provided

### Step 3: Test CRUD Operations
1. Test all GET, POST, PUT, DELETE operations
2. Use the returned IDs in subsequent requests
3. Update environment variables with returned IDs

### Step 4: Test Business Logic
1. **Create Transaction** - Links member and game
2. **Create Recharge** - Updates member balance automatically
3. **Create Collection** - Daily collection tracking

## 📝 Sample Data

### Member Sample
```json
{
  "name": "John Doe",
  "phone": "+1-555-0123",
  "balance": 100.0,
  "email": "john.doe@example.com",
  "membershipType": "PREMIUM",
  "joinDate": "2025-01-17"
}
```

### Game Sample
```json
{
  "name": "Space Invaders",
  "price": 2.5,
  "description": "Classic arcade shooter game",
  "category": "Arcade",
  "difficulty": "Easy",
  "maxPlayers": 1,
  "imageUrl": "https://example.com/space-invaders.jpg"
}
```

### Transaction Sample
```json
{
  "memberId": "{{memberId}}",
  "gameId": "{{gameId}}",
  "amount": 2.5,
  "transactionType": "GAME_PLAY",
  "notes": "Game play transaction"
}
```

### Recharge Sample
```json
{
  "memberId": "{{memberId}}",
  "amount": 50.0,
  "paymentMethod": "CASH",
  "notes": "Member recharge transaction"
}
```

## 🐛 Troubleshooting

### Common Issues

1. **Connection Refused**
   - Ensure backend is running on port 8080
   - Check if MongoDB Atlas connection is working

2. **404 Not Found**
   - Verify the endpoint URL is correct
   - Check if the resource ID exists

3. **Validation Errors**
   - Check required fields are provided
   - Verify data types match schema

4. **MongoDB Connection Issues**
   - Verify credentials in application.properties
   - Check network connectivity to Atlas

### Debug Steps
1. Check application logs in console
2. Verify MongoDB Atlas cluster is accessible
3. Test with simple GET requests first
4. Check Postman console for detailed error messages

## 📊 Environment Variables

The following variables are automatically managed:
- `baseUrl`: http://localhost:8080
- `memberId`: Auto-populated from create responses
- `gameId`: Auto-populated from create responses
- `transactionId`: Auto-populated from create responses
- `rechargeId`: Auto-populated from create responses
- `collectionId`: Auto-populated from create responses
- `adminId`: Auto-populated from create responses

## 🎯 Next Steps

1. Import the collection and environment
2. Start your backend server
3. Run the health check
4. Create test data using the sample requests
5. Test all CRUD operations
6. Explore the search and filter endpoints

Happy testing! 🎮
