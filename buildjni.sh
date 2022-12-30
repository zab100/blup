mkdir -p /tmp/bluetoothframework/jnicompiled/$1

for file in ./jni/$1/*
do
    filename=$(basename "$1")
    extension="${filename##*.}"
    filename="${filename%.*}"

    g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux $file -o $filename.o
done

g++ -shared -fPIC -o ./build/out/$2 /tmp/bluetoothframework/jnicompiled/$1/* -lc