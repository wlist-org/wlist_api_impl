@echo off

javadoc -d doc --source-path src -subpackages com.xuxiaocheng.wlist.api

echo.
echo Then run the following command to start a http server.
echo "cd doc & py -m http.server & cd.."
