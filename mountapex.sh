filename=$(basename -- "$1")
extension="${filename##*.}"
filename="${filename%.*}"

mkdir -p /tmp/bluetoothframework/capex$1-apex
mkdir -p /tmp/bluetoothframework/apex$1-apex

if [ $extension = capex ]
then
    unzip -q -o /home/zain/testmount/$1 -d /tmp/bluetoothframework/capex$1-apex
else
    cp /home/zain/testmount/$1 /tmp/bluetoothframework/capex$1-apex/original_apex
fi

unzip -q -o /tmp/bluetoothframework/capex$1-apex/original_apex -d /tmp/bluetoothframework/apex$1-open/

losetup /dev/loop13 /tmp/bluetoothframework/apex$1-open/apex_payload.img
mount -o ro /dev/loop13 /tmp/bluetoothframework/wifimountloc

cmd.exe /c "pause"

umount /tmp/bluetoothframework/wifimountloc
losetup -d /dev/loop13