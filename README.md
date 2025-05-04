# Shrink It

![Shrinkt_Logo](src/main/resources/image/Photo.png )

# 🔗 URL Shortener Service with Safety & Classification Insights

A high-scale URL shortening service built with Java + Spring Boot, designed to not only shrink URLs but also provide real-time **safety** and **categorization** insights using AI.

---

## 🚀 Features

- 🔐 **Safe & Secure**: Integrates OpenAI's ChatGPT to classify and detect malicious URLs.
- 🎯 **Fast Redirection**: Optimized with Redis for ultra-fast lookups.
- 🧠 **AI-Powered Categorization**: Detects phishing/malicious patterns and categorizes websites intelligently.
- 🧾 **Informative Previews**: Users can retrieve original URLs with classification details.
- 🤖 **Telegram Bot Integration**: Supports commands via Telegram to shorten and unshrink URLs.
- ❄️ **Globally Unique Short Codes**: Uses Snowflake ID + Base62 encoding to generate short, unique identifiers.
- 🔁 **Multi-instance Safe**: Uses Consul KV store to avoid collisions in distributed environments.
- 📦 **Dockerized**: Easy to run with `docker-compose`.

---

## 🛠️ Tech Stack

- **Java 17 + Spring Boot**
- **Redis** (for caching)
- **Consul KV Store** (for distributed coordination)
- **PostgreSQL** (for persistence)
- **OpenAI GPT API** (for content classification & safety check)
- **Telegram Bot API**
- **Docker + Docker Compose**

---

## 📱 Telegram Bot Commands

- `/start` — Get bot info and usage guide
- `/shrink <URL>` — Shrinks the URL and returns classification & safety info
- `/unshrink <short-url>` — Returns the original URL with classification & safety info

---

## 📦 API Endpoints

| Method | Endpoint              | Description                                      |
|--------|-----------------------|--------------------------------------------------|
| POST   | `/shrink`             | Generates a shortened URL                        |
| GET    | `/{shrinkCode}`       | Redirects to the original URL                    |
| POST   | `/get/info`           | Returns original URL and classification/safety info |

---

## ⚡ Caching Strategy (Redis)

| API        | Cache Key                     | Cache Name             |
|------------|-------------------------------|------------------------|
| `/shrink`  | `originalUrl`                 | `url_generation_cache` |
| `/{code}`  | `shrinkCode`                  | `url_redirect_cache`   |
| `/get/info`| `shrinkUrl`                   | `url_info_cache`       |

---

## 🧪 Safety & Classification

- Model: Uses OpenAI ChatGPT and optional Hugging Face models (e.g., `URLShield-DistilBERT`) to determine:
    - 🚨 Is the URL phishing/malicious?
    - 🏷️ What category does the site belong to?

---

## 🧰 Setup & Run (Dockerized)

### 1. Clone the repository

```bash
git clone https://github.com/your-repo/url-shortener.git
cd url-shortener
```

### 2. Update the values in .env file
```
# Application
SPRING_APP_NAME=shrink_it
SERVER_PORT=8080
LOG_FILE_PATH=src/main/resources/log/shrink_it.log

# Database (PostgreSQL)
DB_HOST=postgres
DB_PORT=5432
DB_NAME=shrink_it
DB_USER=postgres
DB_PWD=root
DB_MAX_POOL_SIZE=10
DB_MIN_IDLE=2
DB_IDLE_TIMEOUT=30000
DB_MAX_LIFETIME=1800000
DB_CONNECTION_TIMEOUT=30000
DB_POOL_NAME=HikariCP

# Redis
REDIS_HOST=redis
REDIS_PORT=6379
REDIS_PASSWORD=test_db

# Consul
CONSUL_DISCOVERY_HOST=consul
CONSUL_DISCOVERY_PORT=8500
CONSUL_DISCOVERY_SCHEME=http

# Shrink Service
BASE_URL=http://localhost:8080/

# OpenAI
OPENAI_API_KEY=<your-super-secure-key>

# Telegram Bot
TELEGRAM_BOT_USERNAME=shrink_it_bot
TELEGRAM_BOT_TOKEN=<your-super-secure-token>
TELEGRAM_WEBHOOK_PATH=/api/webhook
TELEGRAM_WEBHOOK_BASE_URL=<telegram-webhook-base-url>

```

### 3. Setup Telegram Bot
Use Telegram [BotFather](https://telegram.me/BotFather) to create your bot and setup webhook using the below api call
```text
https://api.telegram.org/bot<you-bot-token>/setWebhook?url=<telegram-webhook-base-url>/api/webhook
```

### 4. Run your service with Docker
```bash
docker compose up --build
```
