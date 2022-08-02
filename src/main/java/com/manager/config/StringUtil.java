package com.manager.config;

public class StringUtil {
    public static String checkValidString(String value){
        if(value != null && !value.isEmpty()){
            return value;
        }
        return null;
    }

    public static Integer checkValidInteger(Integer value){
        if(value != null){
            return value;
        }
        return 0;
    }
}
