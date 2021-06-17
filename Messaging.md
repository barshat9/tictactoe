## Player Joined
### UI Request
```json
{
  "eventType": "1",
  "gameID": "numeric",
  "initiatingPlayerID": "numeric"
}
```

### Message Published
```json
{
  "eventType": "1",
  "gameID": "string",
  "joinedPlayerID": "numeric",
  "joinedPlayerName": "string"
}
```

## Player Moved

### UI Request
```json
{
  "eventType": "2",
  "gameID": "numeric",
  "playerID": "numeric",
  "position": "numeric"
}
```

### Event Published
```json
{
  "eventType": "user-played",
  "gameID": "numeric",
  "initiatingPlayerID": "numeric",
  "position": "numeric",
  "status": "string",
  "winnerPlayerID": "numeric",
  "loserPlayerID": "numeric"
}
```

