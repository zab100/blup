package com.zbt.shared.utils;

public class AndroidConversions {
    /**
     * @param uuid - uuid provided by bluecove, does not contain spaces or dashes
     * @return - uuid supported by android, view ParcelUuid
     */
    public static String convertUUIDString(String uuid){
        return uuid.substring(0, 8) 
                + "-" 
                + uuid.substring(8, 12) 
                + "-" 
                + uuid.substring(12,16)
                + "-"
                + uuid.substring(16,20)
                + "-"
                + uuid.substring(20);
    }
}
