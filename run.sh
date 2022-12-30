export arch=x64
./clean.sh

export systemimgloc=./wsa/$arch/system.img

rm $1/system.img &
rm $1/system_ext.img &
rm $1/product.img &
rm $1/vendor.img &
cp ./wsa/$arch/system.img ./ &

./pull.sh /system/framework/framework.jar $systemimgloc
./pull.sh /system/framework/services.jar $systemimgloc
./pull.sh /system/app/Bluetooth/Bluetooth.apk $systemimgloc

./pullfromapex.sh /javalib/framework-wifi.jar /system/apex/com.android.wifi.capex $systemimgloc
./pullfromapex.sh /javalib/framework-connectivity.jar /system/apex/com.android.tethering.capex $systemimgloc

./copytolib.sh framework &
./copytolib.sh services &
./copytolib.sh Bluetooth &
./copytolib.sh framework-wifi &
./copytolib.sh framework-connectivity &
wait

# ./build.sh framework hiddenapi jar
# ./build.sh services no jar
# ./overridelib.sh framework
# ./overridelib.sh services
if ! ./build.sh Bluetooth no apk; then
    exit 1
fi

wait

# ./push.sh ./build/out/framework.jar /system/framework
# ./push.sh ./build/out/services.jar /system/framework
# ./pushandcreate.sh ./BluetoothForwarder/app/build/outputs/apk/debug/app-debug.apk /system/app/BluetoothForwarder /system/app /system/app/CertInstaller/CertInstaller.apk
# ./push.sh ./platform.xml /system/etc/permissions
./pushandcreate.sh ./build/out/Bluetooth.apk /system/app/Bluetooth /system/app /system/app/CertInstaller/CertInstaller.apk
./push.sh ./libbluetooth_jni.so /system/lib64/

# ./profmaybe.sh
# ./profmanservices.sh

mount ./system.img /tmp/bluetoothframework/androidsystem
find /tmp/bluetoothframework/androidsystem/ -name "*.oat" -delete &
find /tmp/bluetoothframework/androidsystem/ -name "*.art" -delete &
find /tmp/bluetoothframework/androidsystem/ -name "*.vdex" -delete &
wait
umount /tmp/bluetoothframework/androidsystem

# cp ./system.img $1
rm -r "$1/AppxMetadata"
rm "$1/AppxBlockMap.xml"
rm "$1/AppxSignature.p7x"
rm "$1/[Content_Types.xml]"

./installgapps.sh $1

./reinstall.sh $1