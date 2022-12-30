mount ./system.img /tmp/bluetoothframework/androidsystem
mkdir -p "/tmp/bluetoothframework/androidsystem$2"
echo "copying $1 to /tmp/bluetoothframework/androidsystem$2"

cp $1 "/tmp/bluetoothframework/androidsystem$2"

umount /tmp/bluetoothframework/androidsystem
