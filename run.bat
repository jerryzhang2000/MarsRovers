@echo off
set "debug=n"
set /p "debug=Do you want to turn the Log on?(y/n)"
java -jar target\rover-1.1-jar-with-dependencies.jar %debug%
pause