import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import java.util.concurrent.locks.ReentrantLock;

public class GroceryQueues {

    public class GroceryQueues{
        private final List<Cashier> cashiers;
        private final int numofCashiers;
        private final ReentrantLock lock=new ReentrantLock();
        Thread[] CashierThreads;

        public GroceryQueues(int numofCashiers, int maxGroceryQueueLength, QueueSimulator simulator){
           System.out.println("Grocery Queue started \n");
           this.cashiers=new ArrayList<>(); 
           this.numofCashiers=numofCashiers;
           this.CashierThreads=new Thread[numofCashiers];

           for(int i=0; i<numofCashiers; i++){
               Cashier cashier=new Cashier(i, maxGroceryQueueLength, simulator);
               cashiers.add(cashier);
               CashierThreads[i]=new Thread(cashier);
        }
    }

    public void start(){
        for(int i=0; i<numofCashiers; i++){
            CashierThreads[i].start();
        }
    }

    public void addCustomer(Customer customer){
        long startTime=System.currentTimeMillis();
        boolean added=false;
        while(System.currentTimeMillis-startTime<1000 && !added){
            //short queue finding
            lock.lock();
            try{
                Cashier shortQueue=null;
                for(Cashier cashier:cashiers){
                    if(shortQueue==null || cashier.getQueueLength()<shortQueue.getQueueLength()){
                        shortQueue=cashier;
                    }
                }
                if(shortQueue!=null && shortQueue.getQueueSize()<shortQueue.getMaxQueueLength()){
                    shortQueue.addCustomer(customer);
                    added=true;
                }
            }finally{lock.unlock();}
            try{
                Thread.sleep(100);
            } catch(InterruptedException e){
                e.printStackTrace();}
        }
        if(!added){
            System.out.println("Customer departed from grocery queue which arrived at %d\n",customer.getArrivalTime());
            customer.setDeparted(true);
        }
    }

    public void stopAllCashiers(){
        for(Cashier cashier:cashiers){
            cashier.stop();
        }

        for(int i=0; i<numofCashiers; i++){
            try{
                CashierThreads[i].interrupt();
                CashierThreads[i].join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
}