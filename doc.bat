@echo off

set MAVEN_REPOSITORY=%M2_HOME%\repository

set Either=%MAVEN_REPOSITORY%/io/github/jbock-java/either/1.5.2/either-1.5.2.jar

javadoc -d doc --source-path src -p %Either% -subpackages com &rem com.xuxiaocheng.wlist.api

echo.
echo Then run the following command to start a http server.
echo "cd doc & py -m http.server & cd.."
