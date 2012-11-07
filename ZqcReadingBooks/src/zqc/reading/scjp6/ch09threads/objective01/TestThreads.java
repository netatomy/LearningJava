package zqc.reading.scjp6.ch09threads.objective01;


public class TestThreads {

    /**
     * @param args
     */
    public static void main(String[] args) {

        FooRunnable r = new FooRunnable();
        Thread t = new Thread(r);
        t.start();
    }

}

class FooRunnable implements Runnable{
    @Override
    public void run() {
    
        for (int i = 0; i < 6; i++) {
            System.out.println("Runnable running");
        }
    }
}
