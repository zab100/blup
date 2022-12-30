package com.zbt.shared.utils;

import java.util.Arrays;

public class Utils {
    /**
     * Generates a long from a String where stringToLong(x) != stringToLong(y),
     * but stringToLong(x) == stringToLong(x)
     * @param s - A String
     * @return a long that represents s
     *
     */
    public static long stringToLong(String s){
        byte[] bytes = s.getBytes();
        long result = 0;

        for(byte b : bytes){
            result = result + b;
            result = result << 8;
        }

        return result;
    }

    /**
     * @param bytes - an array of bytes
     * @return a boolean whether all bytes in the array are zero
     */
    public static boolean allBytes0(byte[] bytes){
        for(byte b : bytes){
            if(b != 0){
                return false;
            }
        }
        return true;
    }

    public static String cleanAddress(String address){
        return address.trim().replace(" ", "").replace(":", "").toUpperCase();
    }

    /**
     * @param uuid - A UUID as a String
     * @return The same UUID trimmed and without dashes, spaces, and in uppercase
     */
    public static String cleanUUID(String uuid){
        return uuid.trim().replace("-", "").replace(" ", "").toUpperCase();
    }

    /**
     * Reverse an array without modifying the original array passed
     * @param <T> - The type of the array passed
     * @param array - An array
     * @return The same array, reversed
     */
    public static <T> T[] reverseArray(T[] array){
        T[] copy = Arrays.copyOf(array, array.length);
        for(int i = 0; i < copy.length / 2; i++){
            T temp = copy[i];
            copy[i] = copy[copy.length - 1 - i];
            copy[copy.length - 1 - i] = temp;
        }
        return copy;
    }

    /**
     * Attempts to match a uuid to KnownUUIDs to aid with debugging
     * @param uuid - A 128 bit UUID as a String
     * @return Debugging information about the UUID
     */
    public static String attemptMatchUUID(String uuid){
        // String shortForm = uuid.substring(0, 8);
        // long l = Long.parseLong(shortForm, 16);

        // String ret = "0x" + shortForm;

        // KnownUUIDs[] knownUUIDs = KnownUUIDs.values();

        // knownUUIDs = reverseArray(knownUUIDs);

        // for(KnownUUIDs shortUUID : knownUUIDs){
        //     if((l & shortUUID.getShortForm()) == shortUUID.getShortForm()){
        //         ret += " " + shortUUID.name();
        //         break;
        //     }
        // }

        // return ret;
        return uuid;
    }
}
