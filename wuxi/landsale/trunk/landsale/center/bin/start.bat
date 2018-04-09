@echo off
TITLE CENTER SERVER
java -jar app/center.jar --spring.config.location=cfg/ --logging.path=logs/ --server.tomcat.basedir=tmp/