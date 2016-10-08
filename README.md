[![Build Status](https://travis-ci.org/SadatAnwar/lunch-mator.svg?branch=master)](https://travis-ci.org/SadatAnwar/lunch-mator)
### Lunch-Mator
The lunch meetup app. 

![lunch-mator](/public/images/lunchmator.jpg)

# 
The wire-frames for the UI can be accessed/and or worked upon here [wireframe](https://wireframe.cc/dQkepa). 

### Dev setup
## DB
0. Install postgress (for mac try [this](http://postgresapp.com/))
0. create a database called lunch_mator 
  0. `$ createdb lunch_mator`
0. create user app
  0. `$ createuser -P -s -e app`


```bash
Role "app" with password "root" on database lunch_mator
```
## typings
```bash
npm install -g typings
typings install
```
