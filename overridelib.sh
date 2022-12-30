tempPath=$(pwd)
(cd /tmp/bluetoothframework/bin/$1/ && zip -q -r $tempPath/lib/$1/classes.jar ./)