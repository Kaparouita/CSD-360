## Pull sql
docker pull mysql:latest

## Run sql
docker run --name mysql -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 -d mysql
