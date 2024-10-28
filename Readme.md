# API Documentation

This project provides a RESTful API documentation






## Server URL : ```http://localhost:8080``` 
### (Make sure you store the server URL in .env file)

## Authentication Endpoints

### 1. Register User


**POST** `/api/users/register`

```http
POST /api/users/register HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 87

{
    "email": "demo@gmail.com",
    "username": "blaster",
    "password": "123456"
}
```

### 2. Login User

It will return a token i.e. the JWT  token, you need to send the token everytime 
in "Authorization : Bearer \<token>\" in request headers.


**POST** `/api/users/login`

```http
POST /api/users/login HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 60

{
    "email":"demo@gmail.com",
    "password":"123456"
}
```


## Server Info Endpoints

### 1. Server status

**GET** `/`

```http
GET / HTTP/1.1
Host: localhost:8080
```

### Expected response : Server is running at port 8080 


### 2. Server metrics

**GET** `/metrics`

```http
GET /metrics HTTP/1.1
Host: localhost:8080
```



