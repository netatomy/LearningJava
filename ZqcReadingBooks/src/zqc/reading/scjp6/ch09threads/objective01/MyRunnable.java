package zqc.reading.scjp6.ch09threads.objective01;


public class MyRunnable implements Runnable {

    @Override
    public void run() {

        System.out.println("Important job running in MyRunnable");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        MyRunnable r = new MyRunnable(); // Runnable is the job to be done
        Thread t = new Thread(r); // Thread is the worker, r is the target.
    }

}
