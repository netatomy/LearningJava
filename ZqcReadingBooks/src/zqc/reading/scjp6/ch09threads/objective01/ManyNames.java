package zqc.reading.scjp6.ch09threads.objective01;

public class ManyNames {

    /**
     * @param args
     */
    public static void main(String[] args) {

        NameRunnable nr = new NameRunnable();
        Thread one = new Thread(nr);
        Thread two = new Thread(nr);
        Thread three = new Thread(nr);

        one.setName("Fred");
        two.setName("Lucy");
        three.setName("Ricky");
        
        one.start();
        two.start();
        three.start();
    }

    private static class NameRunnable implements Runnable {

        @Override
        public void run() {

            for (int x = 0; x < 3; x++) {
                System.out.println("Run by " + Thread.currentThread().getName()
                                + ", x is " + x);
            }
        }
    }

}
