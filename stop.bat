@echo off

set JAVADIR=%~dp0\..\..\JDK
set JAVA_HOME=%JAVADIR%
set CLASSPATH=%JAVA_HOME%\lib\tools.jar;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\jre\lib\rt.jar
set PATH=%JAVA_HOME%\bin;%PATH%

setlocal enabledelayedexpansion
for /f "tokens=1" %%i in ('jps -l ^| findstr "nbi-1.0"') do (
taskkill /F /PID %%i)

