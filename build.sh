mkdir ./build/

mkdir -p /tmp/bluetoothframework/dexed
mkdir -p /tmp/bluetoothframework/standard

mkdir -p /tmp/bluetoothframework/out/$1
mkdir -p /tmp/bluetoothframework/out/$1-dexed

mkdir -p /tmp/bluetoothframework/intermediate/$1

mkdir -p /tmp/bluetoothframework/build/recompiled/$1
mkdir -p /tmp/bluetoothframework/build/alldexes/$1

mkdir -p /tmp/bluetoothframework/bin/
mkdir -p /tmp/bluetoothframework/zips/

if ! ant $1; then
    exit 1
fi

(cd /tmp/bluetoothframework/bin/$1/ && zip -q -r /tmp/bluetoothframework/bin/$1.zip ./)

./d8 --min-api 32 --output /tmp/bluetoothframework/perclass/$1 --file-per-class /tmp/bluetoothframework/bin/$1.zip

(cd /tmp/bluetoothframework/perclass/$1 && zip -q -r /tmp/bluetoothframework/zips/$1.zip ./)

mkdir -p /tmp/bluetoothframework/nothidden/$1
./d8 --output /tmp/bluetoothframework/nothidden/$1 /tmp/bluetoothframework/zips/$1.zip

inputdexes=""
outputdexes=""

for file in /tmp/bluetoothframework/nothidden/$1/*.dex
do
    inputdexes="$inputdexes --input-dex=$file"
    outputdexes="$outputdexes --output-dex=/tmp/bluetoothframework/build/alldexes/$1/$(basename $file)"
done

if [ $2 = "hiddenapi" ]; then
    ./hiddenapi encode --no-force-assign-all $inputdexes $outputdexes --api-flags=./hiddenapi-flags-aosp-12.1.csv
else
    cp /tmp/bluetoothframework/nothidden/$1/*.dex /tmp/bluetoothframework/build/alldexes/$1/
fi

rm /tmp/bluetoothframework/dexed/$1/*.dex
cp -r /tmp/bluetoothframework/dexed/$1/* /tmp/bluetoothframework/build/alldexes/$1/

mkdir /tmp/bluetoothframework/out
mkdir /tmp/bluetoothframework/out/
(cd /tmp/bluetoothframework/build/alldexes/$1/ && zip -q -0 -r /tmp/bluetoothframework/out/$1.$3 ./)

mkdir ./build
mkdir ./build/out
windowspath=$(wslpath -w /tmp/bluetoothframework/out/$1.$3)
./zipalign.exe -p -f 4 $windowspath .\\build\\out\\$1.$3

wait