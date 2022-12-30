mkdir ./lib
rm -r ./lib/$1/*

for file in /tmp/bluetoothframework/dexed/$1/*.dex
do
    fn=$(basename "$file")
    fn="${fn%.*}"
    ./dex-tools-2.1/d2j-dex2jar.sh $file -o ./lib/$1/$fn.jar &
done

wait