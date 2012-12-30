package zqc.reading.poeaa.ch09domainlogic;

import java.math.BigDecimal;

public class Money {

    private String currency = "USD";
    private long   num      = 0;

    public Money(String c, long num) {

        currency = c;
        this.num = num;
    }

    public Money[] allocate(int n) {

        // TODO Auto-generated method stub
        return null;
    }

    public Money add(Money money) {

        return null;
    }

    public static Money dollars(long num) {

        return new Money("USD", num);
    }

    public static Money dollars(BigDecimal dec) {

        return null;
    }

    public BigDecimal amount() {

        // TODO Auto-generated method stub
        return null;
    }
}