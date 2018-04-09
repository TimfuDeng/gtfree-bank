@echo off
TITLE CORE SERVER
java -jar app/core.jar --spring.config.location=cfg/ --logging.path=logs/ --server.tomcat.basedir=tmp/