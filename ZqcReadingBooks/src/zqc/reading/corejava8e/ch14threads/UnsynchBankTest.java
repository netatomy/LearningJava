package zqc.reading.corejava8e.ch14threads;


public class UnsynchBankTest {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        for (int i = 0; i < NACCOUNTS; i++){
            TransferRunnable r = new TransferRunnable(b, i, INITIAL_BALANCE);
            Thread t = new Thread(r);
            t.start();
        }
    }
    
    public static final int NACCOUNTS = 100;
    public static final double INITIAL_BALANCE = 1000;

}
