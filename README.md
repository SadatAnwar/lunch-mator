### Lunch-Mator
The lunch meetup app. 

![lunch-mator](/ui/src/assets/images/lunchmator.jpg)


## Dev setup
### DB
0. Install postgress (for mac try [this](http://postgresapp.com/))
0. create a database called lunch_mator 
  * `$ createdb lunch_mator`
0. create user app
  * `$ createuser -P -s -e app`


```bash
Role "app" with password "root" on database lunch_mator
```
### typings
```bash
npm install -g typings
typings install
```
### SASS Compiler

```bash
gem install sass
```

### Run 
#### For dev
```bash 
sbt run -Dconfig.resource=application.test.conf -Dlogger.resource=test-logback.xml
```
#### For prod
```bash
sbt dist
``` 
and then 
```bash
unzip DIST_FILE
sudo DIST_FILE/bin/lunch-mator -Dhttp.port=80
```
