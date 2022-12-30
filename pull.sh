filename=$(basename "$1")
extension="${filename##*.}"
filename="${filename%.*}"

mkdir -p /tmp/bluetoothframework/dexed

mkdir /tmp/bluetoothframework/androidsystem
echo "mounting $2"
trap 'echo system.img mounted, cannot exit safely, try again after unmount' INT
mount $2 /tmp/bluetoothframework/androidsystem
cp "/tmp/bluetoothframework/androidsystem$1" /tmp/bluetoothframework/dexed/
umount /tmp/bluetoothframework/androidsystem
echo "unmounted $2"
trap INT

mkdir -p /tmp/bluetoothframework/dexed/$filename
unzip -q -o /tmp/bluetoothframework/dexed/$filename.$extension -d /tmp/bluetoothframework/dexed/$filename

mkdir -p ./src/$filename

mkdir -p /tmp/bluetoothframework/perclass/$filename
./d8 --output /tmp/bluetoothframework/perclass/$filename --file-per-class /tmp/bluetoothframework/dexed/$filename/*.dex