import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import Customer.java;
import lock;


public BankQueue(int maxBankQueueLength){
    this.maxBankQueueLength = maxBankQueueLength;
    this.queue = new LinkedList<>();
    this.lock = new ReentrantLock();
    this.queueNotEmpty = lock.newCondition();
    this.running = true; 
}

public boolean addCustomer(Customer customer){
    lock.lock();
    try{
        if(queue.size() < maxBankQueueLength){
            queue.add(customer);
            queueNotEmpty.signalAll();
            return true;
        }
        return false;
    }finally{
        lock.unlock();
    }
}

public Customer getCustomerForService(){
    lock.lock();
    try{
        while(queue.isEmpty()&& running){
            queueNotEmpty.await();  
        }
        if(running==false){
            return null;
        }
        return queue.poll();
    }
    catch(InterruptedException e){
        e.printStackTrace();
        return null;
    } finally{
        lock.unlock();
    }


    public void stop(){
        lock.lock();
        try{
            running = false;
            queueNotEmpty.signalAll();
        }finally{
            lock.unlock();
        }
    }

    @override
    public void run(){
        while(running){
            try{
                Thread.sleep(millis:1000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        System.out.println("BankQueue thread stopped");
    }
}