@echo off
TITLE REDIS SERVER
java -jar redis/core.jar --spring.config.location=cfg/ --logging.path=logs/ --server.tomcat.basedir=tmp/