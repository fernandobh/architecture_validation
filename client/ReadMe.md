### Get All
```
curl http://localhost:8080/client
```
### Get One
```
curl http://localhost:8080/client/{id}
```
### Post One
```
curl -X POST http://localhost:8080/client -H 'Content-Type: application/json' -d '{"firstName":"test","lastName":"test"}'
```
### IMAGE
```
docker build -t app-mongo:1.0.1 .
```