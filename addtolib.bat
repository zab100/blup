echo "pushd %1"
pushd "%1"
echo ".\dex-tools-2.1\d2j-dex2jar.bat %2 -o .\lib\%3\%4.jar"
.\dex-tools-2.1\d2j-dex2jar.bat %2 -o .\lib\%3\%4.jar
popd