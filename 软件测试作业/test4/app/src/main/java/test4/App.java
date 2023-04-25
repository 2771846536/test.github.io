/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package test4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class App {
    public long countTime(String date1, String date2)throws ParseException {
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date fromDate = simpleDateFormat1.parse(date1);
        Date toDate = simpleDateFormat1.parse(date2);
        Calendar fromcal = Calendar.getInstance();
        fromcal.setTime(fromDate);
        Calendar tocal = Calendar.getInstance();
        tocal.setTime(toDate);
        long phoneTime = tocal.getTime().getTime() - fromcal.getTime().getTime();
        long minutes;
        if (phoneTime >= 0 && phoneTime / 1000 < 60 || phoneTime % 60000 > 0)
            minutes = phoneTime / 1000 / 60 + 1;
        else
            minutes = phoneTime / 1000 / 60;
        if (fromcal.get(Calendar.MONTH) == Calendar.NOVEMBER && fromcal.get(Calendar.DATE) <= 7 && fromcal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            if (fromcal.get(Calendar.HOUR_OF_DAY) == 2 && tocal.get(Calendar.HOUR_OF_DAY) == 2 && tocal.get(Calendar.HOUR_OF_DAY) < 3 && tocal.get(Calendar.MINUTE) <= fromcal.get(Calendar.MINUTE)) {

                minutes += 60;
            } else if (fromcal.get(Calendar.HOUR_OF_DAY) < 2 && fromcal.get(Calendar.HOUR_OF_DAY) >= 2) {
                minutes += 60;
            }
        } else {
            if (tocal.get(Calendar.MONTH) == Calendar.NOVEMBER && tocal.get(Calendar.DATE) <= 7 && tocal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                if (tocal.get(Calendar.HOUR_OF_DAY) >= 2)
                    minutes += 60;
            } else {
                if (fromcal.get(Calendar.MONTH) == Calendar.MARCH && fromcal.get(Calendar.DATE) >= 8 && fromcal.get(Calendar.DATE) <= 14 && fromcal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    if (fromcal.get(Calendar.HOUR_OF_DAY) <= 2 && tocal.get(Calendar.HOUR_OF_DAY) >= 3)
                        minutes -= 60;
                } else {
                    if (tocal.get(Calendar.MONTH) == Calendar.MARCH && tocal.get(Calendar.DATE) >= 8 && tocal.get(Calendar.DATE) <= 14 && tocal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        if (tocal.get(Calendar.HOUR_OF_DAY) >= 3)
                            minutes -= 60;
                    }
                }
            }
        }
        return minutes;
    }

    public String countMoney(long time) {
        double money;
        if (time <= 0 || time > 1800) {
            money = -1.00;
        } else if (time <= 20) {
            money = time * 0.05;
        } else {
            money = 1 + (time - 20) * 0.1;
        }
        return String.format("%.2f", money);
    }
    public String cost(String fromtime, String totime) {
        try {
            App p = new App();
            long minutes = p.countTime(fromtime, totime);
            return p.countMoney(minutes);
        } catch (ParseException e) {
            return "-1";
        }
    }
}
