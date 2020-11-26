set mypath=%~dp0
cd %mypath:~0,-1%

java -cp lib/* org.oldo.tippotle.Main
