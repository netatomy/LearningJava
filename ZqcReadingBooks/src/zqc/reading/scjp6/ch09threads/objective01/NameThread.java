package zqc.reading.scjp6.ch09threads.objective01;


public class NameThread {

    /**
     * @param args
     */
    public static void main(String[] args) {

        NameRunnable r= new NameRunnable();
        Thread t = new Thread(r);
        t.setName("Fred");
        t.start();
        
        System.out.println("thread is " + Thread.currentThread().getName());
    }

}
class NameRunnable implements Runnable{
    @Override
    public void run() {
    
        System.out.println("NameRunnable running");
        System.out.println("Run by " + Thread.currentThread().getName());
    }
}