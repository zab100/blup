cp ./system.img ./WSAGAScript/\#IMAGES &
cp ./wsa/x64/system_ext.img ./WSAGAScript/\#IMAGES &
cp ./wsa/x64/product.img ./WSAGAScript/\#IMAGES &
cp ./wsa/x64/vendor.img ./WSAGAScript/\#IMAGES &
wait

cp ./open_gapps* ./WSAGAScript/\#GAPPS

(cd ./WSAGAScript &&
./extract_gapps_pico.sh &&
./extend_and_mount_images.sh &&
./apply.sh &&
./unmount_images.sh)

cp ./WSAGASCRIPT/\#IMAGES/* $1
