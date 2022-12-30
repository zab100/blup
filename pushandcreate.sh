mount ./system.img /tmp/bluetoothframework/androidsystem
mkdir -p "/tmp/bluetoothframework/androidsystem$2"
echo "copying $1 to /tmp/bluetoothframework/androidsystem$2"

cp $1 "/tmp/bluetoothframework/androidsystem$2"

find /tmp/bluetoothframework/androidsystem$2 -exec chown root:root {} \;
find /tmp/bluetoothframework/androidsystem$2 -type d -exec chmod 755 {} \;
find /tmp/bluetoothframework/androidsystem$2 -type f -exec chmod 644 {} \;
find /tmp/bluetoothframework/androidsystem$2 -type d -exec chcon --reference=/tmp/bluetoothframework/androidsystem$3 {} \;
find /tmp/bluetoothframework/androidsystem$2 -type f -exec chcon --reference=/tmp/bluetoothframework/androidsystem$4 {} \;

umount /tmp/bluetoothframework/androidsystem
