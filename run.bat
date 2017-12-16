@echo off
if not exist "%JAVA_HOME%\bin\ztev_nbi.exe" (
    copy "%JAVA_HOME%\bin\javaw.exe"  "%JAVA_HOME%\bin\ztev_nbi.exe"
)else (
 echo "ztev_nbi.exe already exists"
)
 
start ztev_nbi.exe -Dfile.encoding=utf-8 -Xmx512m -Xms128m -Xmn128m -Xss1024k -jar nbi-1.0.jar   --spring.config.location=application.properties

exit
