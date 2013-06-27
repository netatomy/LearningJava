package zqc.reading.poeaa.ch09domainlogic;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MfDate {

    private Calendar calendar;
    
    public MfDate(Date date) {
        calendar = new GregorianCalendar(date.getYear(), date.getMonth(), date.getDate());
    }

    public MfDate() {
        calendar = Calendar.getInstance();
    }

    public MfDate addDays(int i) {

        // TODO Auto-generated method stub
        return new MfDate();
    }

    public java.sql.Date toSqlDate() {

        return new Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public boolean after(MfDate date) {

        return calendar.after(date.calendar);
    }
}