package com.platform.tool.lock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author SZ
 * @Description:
 * @date 2021/1/21 9:40
 */
public class OrderNumberGenerator {

    private int count = 0;

    public String getNumber() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+ ++count;
    }


}
