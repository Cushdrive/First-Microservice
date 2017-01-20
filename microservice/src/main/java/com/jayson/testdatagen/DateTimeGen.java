package com.jayson.testdatagen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.OptionalLong;
import java.util.Random;
import java.util.stream.LongStream;

/**
 * Created by jayson on 1/16/17.
 */
public class DateTimeGen {

    private final long MS_IN_24HOURS = 86400000;
    private final long MIN_MS_OFFSET = 1;
    private final String DATE_TIME_FORMAT = "YYYY-MM-DD hh:mm:ss.sss";
    private final Random randGen = new Random();

    public DateTimeGen() {
        //Default constructor for annotation
    }

    public String getRandomDateTimeStringFromLast24() {
        long offset = 1;
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        long currentDateTime = System.currentTimeMillis();
        LongStream offsetGen = randGen.longs(MIN_MS_OFFSET,MS_IN_24HOURS);
        OptionalLong optionalOffset = offsetGen.findAny();
        offset = optionalOffset.getAsLong();

        currentDateTime = currentDateTime - offset;

        return dateFormatter.format(currentDateTime);
    }

    public Date getRandomDateTimeFromLast24() {
        long offset = 1;
        long currentDateTime = System.currentTimeMillis();
        LongStream offsetGen = randGen.longs(MIN_MS_OFFSET,MS_IN_24HOURS);
        OptionalLong optionalOffset = offsetGen.findAny();
        offset = optionalOffset.getAsLong();

        currentDateTime = currentDateTime - offset;

        return new Date(currentDateTime);
    }
}
