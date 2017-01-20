package com.jayson.DateTimeTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.OptionalLong;
import java.util.Random;
import java.util.stream.LongStream;

/**
 * Created by jayson on 1/16/17.
 */
public class Sandbox {
    public static void main(String[] args) {
        long offset = 1;
        long currentDateTime = System.currentTimeMillis();
        Random randGen = new Random();
        LongStream offsetGen = randGen.longs(1,86400000);
        OptionalLong optionalOffset = offsetGen.findAny();
        if (optionalOffset.isPresent()) {
            offset = optionalOffset.getAsLong();
        }

        System.out.println(String.valueOf(currentDateTime));
        System.out.println(String.valueOf(offset));
        currentDateTime = currentDateTime - offset;
        String format = "YYYY-MM-DD hh:mm:ss.sss";
        SimpleDateFormat converter = new SimpleDateFormat(format);


        System.out.println(new Date(currentDateTime));

        System.out.println(converter.format(currentDateTime));
    }
}
