package zqc.reading.scjp6.ch09threads.objective01;


public class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("Important job running in MyThread");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        MyThread t = new MyThread();
        t.start();
    }

}
