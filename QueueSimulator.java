import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import Teller;
import Customer;
import BankQueue;
import GroceryQueues;
import FileWriter;
import IOException;
import ArrayList;
import List;
import ThreadLocalRandom;

public class QueueSimulator{

    private final BankQueue bankQueue;
    private final GroceryQueues groceryQueues;
    private final int simulationTime;
    private final List<Customer>BankCustomers, GroceryCustomers;
    private int totalCustomesArrived;
    private int[] totalCustomerDeparted=new int[2];
    private int[] totalCustomerServed=new int[2];
    private int[] totalServiceTime=new int[2];
    private int numofTellers;
    Thread[] tellers;
    FileWriter result;


    public QueueSimulator(int numofTellers,int maxBankQueueLength,int numofCashiers,int maxGroceryQueueLength, int simulationTime){
        try{
            result=new FileWriter("result.txt");
        }
        catch(IOException e){
            e.printStackTrace();
        }
        

        this.numofTellers=numofTellers;
        this.bankQueue =new BankQueue(maxBankQueueLength);
        this.groceryQueues=new GroceryQueues(numofCashiers, maxGroceryQueueLength,this);
        this.simulationTime=simulationTime*60;
        this.BankCustomers=new ArrayList<>();

        this.GroceryCustomers=new ArrayList<>();
        this.totalCustomesArrived=0;
        for(int i=0;i<2;i++){
            this.totalCustomerDeparted[i]=0;
            this.totalCustomerServed[i]=0;
            this.totalServiceTime[i]=0;
        }


        //crating && starting tellers threads
        tellers=new Thread[numofTellers];
        for(int i=0;i<numofTellers;i++){
            tellers[i]=new Thread(new Teller(bankQueue,i));
            tellers[i].start();
        }
        
    }
        public void simulate() throws InteruptedException{
            Thread bankQueueThrad=new Thread(bankQueue);
            bankQueueThrad.start();
            groceryQueues.start();


            int currentTime=0;
            //arriving customers
            while(currentTime<simulationTIme){

                if(currentTime%ThreadLocalRandom.current().nextInt(20,60)==0){
                    Customer customer =new Customer(QueueSystem.currentTimeMills());
                    BankCustomers.add(customer);
                    Customer grocerycustomer =new Customer(customer);
                    BankCustomers.add(grocerycustomer);

                    totalCustomesArrived++;
                    if(!bankQueue.addCustomer(customer)){
                        customer.setDeparted(true);
                    }
                    groceryQueues.addCustomer(grocerycustomer);
                }
                currentTime++;


                try{
                    Thread.sleep(1000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }

            }
            System.out.println("Simulation ended\n");
   //stopping all threads
            bankQueue.stop();
            grocerryQueues.stopAllCashiers();
            for(int i=0;i<numofTellers;i++){
                tellers[i].interrupt();
                tellers[i].join();
            }

        }
        
  
       // for bank customers
        public synchronized void BankCustomersCalculate(){
            for(Customer customer:BankCustomers){
                if(customer.isDeparted()){
                    totalCustomerDeparted[0]++;
                }
                else{
                    totalCustomerServed[0]++;
                    totalServiceTime[0]+=customer.getServiceTime()-customer;
                    getArrivalTime();
                }
            }
        }
       // for grocery customers
        public synchronized void BankCustomersCalculate(){
            for(Customer customer:GroceryCustomers){
                if(customer.isDeparted()){
                    totalCustomerDeparted[1]++;
                }
                else{
                    totalCustomerServed[1]++;
                    totalServiceTime[1]+=customer.getServiceTime()-customer;
                    getArrivalTime();
                }
            }
        }

        //printing results
        public void printResults(){
            double[] averageServiceTime=new double[2];
            GroceryCustomersCalculate();
            BankCustomersCalculate();

            try{
                results.write("Simulation time : "+simulationTime/60+" hours\n");
            }
            catch(IOException e){
                e.printStackTrace();
            }

            for(int i=0;i<2;i++){
                averageServiceTime[i]=totalCustomerServed[i]>0?0 ? (double) totalServiceTime[i]/totalCustomerServed[i]:0;

                averageServiceTime[i]/=1000.0; //seconds
                try{
                    if(i==0) result.write("Bank customers\n");
                    else result.write("Grocery customers\n");
                    result.write("Total customers arrived: "+totalCustomesArrived+"\n");
                    result.write("Total customers served: "+totalCustomerServed[i]+"\n");
                    result.write("Total customers departed: "+totalCustomerDeparted[i]+"\n");
                    result.write("Average service time: "+averageServiceTime[i]+" seconds\n\n");
                }
                catch(IOException e){
                    System.out.println("Error while writing to file");
                    e.printStackTrace();
                }
            }
            try{
                result.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
}
