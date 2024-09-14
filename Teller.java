import java.util.concurrent.locks.Lock;
import Customer;
import BankQueue;

public class Teller implements Runnable{
    private final BankQueue bankQueue;
    private final int tellerID;

    public Teller(BankQueue bankQueue, int tellerID){
        this.bankQueue = bankQueue;
        this.tellerID = tellerID;
    }

    @Override
    public void run(){

        System.out.println("Teller %d is starting\n", tellerID);
        while(true){
            Customer customer = bankQueue.getCustomerForService();
            if(customer == null){
                break;
            }
            System.out.println("Teller %d is serving customer %d\n", tellerID, customer.getServiceTime());
            if(!customer.isDeparted()){
                customer.setServedTime(System.currentTimeMillis());
                customer.setServed(true);
                try{
                    Thread.sleep(customer.getServiceTime()*1000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Teller %d is stopping\n", tellerID);
    }
}