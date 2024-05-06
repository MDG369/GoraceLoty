# GoraceLoty
![image](https://github.com/MDG369/GoraceLoty/assets/73025866/f5d571ed-fa5f-4092-93aa-97da0457f03e)
Project for RSWW

## Requirements
- Node
- Docker
- Angular cli

## How to use
### Running in docker
- build svc using ```maven package -Dmaven.test.skip```
- frontend using ```npm install``` ```npm run build```.
- Change line endings in create_databases.sh to LF
- run ```docker compose up -d``` in the main directory
### Running locally
- run db_postgres in docker: ```docker compose up -d db_postgres```
- run services and frontend locally from your IDE
### Adding new service
- Specify the service address in frontends proxy-conf, this is temporary, as frontend will only communicate with a gateway/orchestrator/something later on
- Add the location in nginx.conf (also temporary, example given below) 
- In docker compose for db_postgres add the name of the new db for the service and rebuild the database
### Additional info
- Push your changes to a new branch and create a pull request. There won't be a code review so lets hope the code works. 

### Adding new location in nginx.conf
```
location /api/hotel/ {
    rewrite ^/api/hotel(/.*)$ $1 break;
    proxy_pass http://localhost:8080;
    proxy_http_version 1.1;
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection $connection_upgrade;
    proxy_read_timeout 300;
    proxy_connect_timeout 300;
    proxy_send_timeout 300;
  }
```
Change localhost to docker container name for docker deployment
