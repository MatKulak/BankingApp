Project use mysql database. In order to use app u need to:
- install mysql
- create tables using mysqldump:
  mysqldump -u [user] -p [password] [database] > mysqldump.sql
- update config.properties file:
db.url=database
db.user=user
db.password=password
  
