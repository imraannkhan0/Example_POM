package org.example.test;

/**
 * Created by Sabyasachi on 19/6/2015.
 */

import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestRunData {

    public Date starttime = new Date();
    public Date endtime = null;
    public String ExecutionTime = null;

    static Logger log = Logger.getLogger(TestRunData.class);

    public String GetExecutionTime() {
        String stime = new Timestamp(starttime.getTime()).toString();
        String etime = new Timestamp(endtime.getTime()).toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(stime);
            d2 = format.parse(etime);
            long diff = d2.getTime() - d1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            log.info(diffDays + " days, ");
            log.info(diffHours + " hours, ");
            log.info(diffMinutes + " minutes, ");
            log.info(diffSeconds + " seconds.");
            ExecutionTime = Long.toString(diffSeconds);
            log.info("ExecutionTime = " + ExecutionTime);
            return ExecutionTime + " secs.";

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public String getStarttime() {
        return new Timestamp(starttime.getTime()).toString();
    }

    public String getEndtime() {
        return new Timestamp(endtime.getTime()).toString();
    }


}
