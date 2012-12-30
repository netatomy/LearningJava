package zqc.reading.poeaa.ch09domainlogic.domainmodel;

import zqc.reading.poeaa.ch09domainlogic.MfDate;
import zqc.reading.poeaa.ch09domainlogic.Money;

public class Tester {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Product word = Product.newWordProcessor("Thinking Word");
        Product calc = Product.newSpreadsheet("Thinking Calc");
        Product db = Product.newDatabase("Thinking DB");

        Contract wordContract = new Contract(word, Money.dollars(2000), new MfDate());
    }

}
