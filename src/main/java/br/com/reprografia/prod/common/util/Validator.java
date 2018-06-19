package br.com.reprografia.prod.common.util;

import java.util.List;


public class Validator {




    public static boolean isNullOrEmpty(String value){
        return value == null || value.isEmpty();
    }

    public static boolean isNullOrEmpty(Object value){
        return value == null;
    }

    public static boolean isNullOrEmpty(List value){
        return value == null || value.isEmpty();
    }
}
