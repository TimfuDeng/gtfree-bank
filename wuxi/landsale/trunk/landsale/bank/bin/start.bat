@echo off
TITLE BANK SERVER
java -jar app/bank.jar --spring.config.location=cfg/ --logging.path=logs/ --server.tomcat.basedir=tmp/