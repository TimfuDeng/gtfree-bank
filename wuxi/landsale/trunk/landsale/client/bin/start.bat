@echo off
TITLE CLIENT SERVER
java -jar app/client.jar --spring.config.location=cfg/ --logging.path=logs/ --server.tomcat.basedir=tmp/