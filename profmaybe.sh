mkdir -p /tmp/bluetoothframework/artapeximg
mkdir -p /tmp/bluetoothframework/androidsystem
mkdir -p /tmp/bluetoothframework/icu4japeximg

mount ./system.img /tmp/bluetoothframework/androidsystem

mkdir -p /tmp/bluetoothframework/capex/art
mkdir -p /tmp/bluetoothframework/apex/art
unzip -q -o /tmp/bluetoothframework/androidsystem/system/apex/com.android.art.capex -d /tmp/bluetoothframework/capex/art
unzip -q -o /tmp/bluetoothframework/capex/art/original_apex -d /tmp/bluetoothframework/apex/art
losetup /dev/loop10 /tmp/bluetoothframework/apex/art/apex_payload.img
mount -o ro /dev/loop10 /tmp/bluetoothframework/artapeximg

mkdir -p /tmp/bluetoothframework/apex/icu4j
unzip -q -o /tmp/bluetoothframework/androidsystem/system/apex/com.android.i18n.apex -d /tmp/bluetoothframework/apex/icu4j
losetup /dev/loop11 /tmp/bluetoothframework/apex/icu4j/apex_payload.img
mount -o ro /dev/loop11 /tmp/bluetoothframework/icu4japeximg

./profman \
--output-profile-type=boot \
--create-profile-from=./boot-image-profile.txt \
\
--apk=/tmp/bluetoothframework/artapeximg/javalib/core-oj.jar \
--apk=/tmp/bluetoothframework/artapeximg/javalib/core-libart.jar \
--apk=/tmp/bluetoothframework/artapeximg/javalib/okhttp.jar \
--apk=/tmp/bluetoothframework/artapeximg/javalib/bouncycastle.jar \
--apk=/tmp/bluetoothframework/artapeximg/javalib/apache-xml.jar \
--apk=/tmp/bluetoothframework/androidsystem/system/framework/framework.jar \
--apk=/tmp/bluetoothframework/androidsystem/system/framework/framework-graphics.jar \
--apk=/tmp/bluetoothframework/androidsystem/system/framework/ext.jar \
--apk=/tmp/bluetoothframework/androidsystem/system/framework/telephony-common.jar \
--apk=/tmp/bluetoothframework/androidsystem/system/framework/voip-common.jar \
--apk=/tmp/bluetoothframework/androidsystem/system/framework/ims-common.jar \
--apk=/tmp/bluetoothframework/icu4japeximg/javalib/core-icu4j.jar \
\
--dex-location=/apex/com.android.art/javalib/core-oj.jar \
--dex-location=/apex/com.android.art/javalib/core-libart.jar \
--dex-location=/apex/com.android.art/javalib/okhttp.jar \
--dex-location=/apex/com.android.art/javalib/bouncycastle.jar \
--dex-location=/apex/com.android.art/javalib/apache-xml.jar \
--dex-location=/system/framework/framework.jar \
--dex-location=/system/framework/framework-graphics.jar \
--dex-location=/system/framework/ext.jar \
--dex-location=/system/framework/telephony-common.jar \
--dex-location=/system/framework/voip-common.jar \
--dex-location=/system/framework/ims-common.jar \
--dex-location=/apex/com.android.i18n/javalib/core-icu4j.jar \
\
--reference-profile-file=/tmp/bluetoothframework/boot-image.prof 2> ./boot-image-profile-error.txt


./profman \
--output-profile-type=bprof \
--create-profile-from=./boot-profile.txt \
\
--apk=/tmp/bluetoothframework/artapeximg/javalib/core-oj.jar \
--apk=/tmp/bluetoothframework/artapeximg/javalib/core-libart.jar \
--apk=/tmp/bluetoothframework/artapeximg/javalib/okhttp.jar \
--apk=/tmp/bluetoothframework/artapeximg/javalib/bouncycastle.jar \
--apk=/tmp/bluetoothframework/artapeximg/javalib/apache-xml.jar \
--apk=/tmp/bluetoothframework/androidsystem/system/framework/framework.jar \
--apk=/tmp/bluetoothframework/androidsystem/system/framework/framework-graphics.jar \
--apk=/tmp/bluetoothframework/androidsystem/system/framework/ext.jar \
--apk=/tmp/bluetoothframework/androidsystem/system/framework/telephony-common.jar \
--apk=/tmp/bluetoothframework/androidsystem/system/framework/voip-common.jar \
--apk=/tmp/bluetoothframework/androidsystem/system/framework/ims-common.jar \
--apk=/tmp/bluetoothframework/icu4japeximg/javalib/core-icu4j.jar \
\
--dex-location=/apex/com.android.art/javalib/core-oj.jar \
--dex-location=/apex/com.android.art/javalib/core-libart.jar \
--dex-location=/apex/com.android.art/javalib/okhttp.jar \
--dex-location=/apex/com.android.art/javalib/bouncycastle.jar \
--dex-location=/apex/com.android.art/javalib/apache-xml.jar \
--dex-location=/system/framework/framework.jar \
--dex-location=/system/framework/framework-graphics.jar \
--dex-location=/system/framework/ext.jar \
--dex-location=/system/framework/telephony-common.jar \
--dex-location=/system/framework/voip-common.jar \
--dex-location=/system/framework/ims-common.jar \
--dex-location=/apex/com.android.i18n/javalib/core-icu4j.jar \
\
--reference-profile-file=/tmp/bluetoothframework/boot-image.bprof 2> ./boot-profile-error.txt

cp /tmp/bluetoothframework/boot-image.prof /tmp/bluetoothframework/androidsystem/system/etc
cp /tmp/bluetoothframework/boot-image.bprof /tmp/bluetoothframework/androidsystem/system/etc



umount /tmp/bluetoothframework/androidsystem
umount /tmp/bluetoothframework/artapeximg
umount /tmp/bluetoothframework/icu4japeximg
losetup -d /dev/loop10
losetup -d /dev/loop11
