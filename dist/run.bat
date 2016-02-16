SET mypath=%~dp0
cd %mypath:~0,-1%

java -cp lib/* net.doepner.app.tippotle.Main