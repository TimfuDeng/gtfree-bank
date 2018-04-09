@echo off
TITLE MONITORING SERVER
java -jar redis/monitoring.jar --spring.config.location=cfg/ --logging.path=logs/ --server.tomcat.basedir=tmp/