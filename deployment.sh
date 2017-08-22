#!/usr/bin/env bash
ssh gradefruit@172.17.3.60 rm -rf /Users/gradefruit/dev/deployment/
ssh gradefruit@172.17.3.60 mkdir /Users/gradefruit/dev/deployment
scp /Users/syedsadatanwar/home_dev/lunch-mator/target/universal/lunch-mator-2.0.zip gradefruit@172.17.3.60:/Users/gradefruit/dev/deployment/lunch-mator-2.0.zip
ssh gradefruit@172.17.3.60  unzip /Users/gradefruit/dev/deployment/lunch-mator-2.0.zip -d /Users/gradefruit/dev/deployment
ssh gradefruit@172.17.3.60 screen -d -m /Users/gradefruit/dev/deployment/lunch-mator-2.0/bin/lunch-mator -Dhttp.port=9000

