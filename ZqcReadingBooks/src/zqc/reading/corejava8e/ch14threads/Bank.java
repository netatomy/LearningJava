package zqc.reading.corejava8e.ch14threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    public Bank(int n, double initialBalance) {

        accounts = new double[n];
        for (int i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
        bankLock = new ReentrantLock();
        sufficientFunds = bankLock.newCondition();
    }

    public void transfer(int from, int to, double amount)
                    throws InterruptedException {

        bankLock.lock();
        try {
            // if (accounts[from] < amount)
            // return;
            while (accounts[from] < amount)
                sufficientFunds.await();
            System.out.println(Thread.currentThread());
            accounts[from] -= amount;
            System.out.format(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.format(" Total Balance: %10.2f%n", getTotalBalance());
            sufficientFunds.signalAll();
        }
        finally {
            bankLock.unlock();
        }
    }
    
    public synchronized void transfer2(int from, int to, double amount) throws InterruptedException{
        while (accounts[from] < amount)
            
            wait();
        System.out.println(Thread.currentThread());
        accounts[from] -= amount;
        System.out.format(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.format(" Total Balance: %10.2f%n", getTotalBalance());
        notifyAll();
    }

    // synchronized
    public double getTotalBalance() {

        bankLock.lock();
        try {
            double sum = 0;

            for (double a : accounts)
                sum += a;
            return sum;
        }
        finally {
            bankLock.unlock();
        }
    }

    public int size() {

        return accounts.length;
    }

    private final double[] accounts;
    private Lock           bankLock;
    private Condition      sufficientFunds;
}
