#!/usr/bin/env bash
nohup java -Dfile.encoding=utf-8 -Xmx512m -Xms128m -Xmn128m -Xss1024k -jar nbi-1.0.jar  --spring.config.location=application.properties &
