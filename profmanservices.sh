mkdir -p /tmp/bluetoothframework/androidsystem

mount ./system.img /tmp/bluetoothframework/androidsystem

./profman \
--output-profile-type=boot \
--create-profile-from=./art-profile \
--apk=./build/out/services.jar \
--dex-location=/system/framework/services.jar \
--reference-profile-file=/tmp/bluetoothframework/services.jar.prof 2> ./services-boot-image-profile-error.txt


./profman \
--output-profile-type=bprof \
--create-profile-from=./art-profile-boot \
--apk=./build/out/services.jar \
--dex-location=/system/framework/services.jar \
--reference-profile-file=/tmp/bluetoothframework/services.jar.bprof 2> ./services-boot-profile-error.txt

cp /tmp/bluetoothframework/services.jar.prof /tmp/bluetoothframework/androidsystem/system/framework
cp /tmp/bluetoothframework/services.jar.bprof /tmp/bluetoothframework/androidsystem/system/framework

umount /tmp/bluetoothframework/androidsystem
