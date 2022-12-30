mkdir -p /tmp/bluetoothframework/wifimountloc
mkdir -p /tmp/bluetoothframework/androidsystem


mkdir -p /tmp/bluetoothframework/capex/$2-apex
mkdir -p /tmp/bluetoothframework/apex/$2-open

echo "mounting $3"
trap 'echo system.img mounted, cannot exit safely, try again after unmount' INT

mount $3 /tmp/bluetoothframework/androidsystem

filename=$(basename -- "$2")
extension="${filename##*.}"
filename="${filename%.*}"

if [ $extension = capex ]
then
    unzip -q -o /tmp/bluetoothframework/androidsystem$2 -d /tmp/bluetoothframework/capex$2-apex
else
    cp /tmp/bluetoothframework/androidsystem$2 /tmp/bluetoothframework/capex$2-apex
fi
umount $3

unzip -q -o /tmp/bluetoothframework/capex$2-apex/original_apex -d /tmp/bluetoothframework/apex$2-open


filename=$(basename "$1")
extension="${filename##*.}"
filename="${filename%.*}"

rm -r ./dexed/$filename
mkdir -p ./dexed/$filename

mkdir -p /tmp/bluetoothframework/dexed

mkdir /tmp/bluetoothframework/androidsystem
losetup /dev/loop13 /tmp/bluetoothframework/apex/$2-open/apex_payload.img
mount -o ro /dev/loop13 /tmp/bluetoothframework/wifimountloc
cp "/tmp/bluetoothframework/wifimountloc$1" /tmp/bluetoothframework/dexed/
umount /tmp/bluetoothframework/wifimountloc
losetup -d /dev/loop13

echo "unmounted $3"
trap INT

mkdir /tmp/bluetoothframework/dexed/$filename
unzip -q -o /tmp/bluetoothframework/dexed/$filename.$extension -d /tmp/bluetoothframework/dexed/$filename

mkdir -p ./src/$filename

mkdir -p /tmp/bluetoothframework/perclass/$filename
./d8 --output /tmp/bluetoothframework/perclass/$filename --file-per-class /tmp/bluetoothframework/dexed/$filename/*.dex