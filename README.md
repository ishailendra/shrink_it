# Shrinkt

![Shrinkt_Logo](src/main/resources/image/Photo.png )

URL shortening web service, which provides short aliases for redirection of long URLs.
The user's browser will immediately redirect to the original (long) url when they access the short URL.


## Implementation
### Tech Used
+ Spring Boot
+ Redis Cache
+ MySQL DB
+ Docker
+ Telegram Bot

When user sends a new url to shrink/shorten, URL will be validated and then shrink url will be stored in DB and returned; It will also be stored in Redis cache.
If the user sends the same url again to shrink, it will be fetched from Redis cache.

When user uses the shrink url, they will be redirected to the original long URL. This is also cached to prevent DB calls.

Users can also use the telegram bot to Shrink and Unshrink url.

## How to Use It

- Check and update the properties in **.env** file
```
REDIS_HOST_PASSWORD=test_db
MYSQL_ROOT_PASSWORD=root
DB_USER=root
DB_PWD=root
DB_HOST=mysql_db
DB_PORT=
REDIS_HOST=redis
TELEGRAM_TOKEN=telegram_sample_token
BASE_URL=www.example.com/
```
- Start docker containers
``` docker compose up --build -d ```
-  To use telegram bot create a Bot using [BotFather](https://t.me/BotFather) and provide the token in .env file





