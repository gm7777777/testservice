package com.test.service1.util;

import java.util.UUID;

public class UUIDGenerator {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
