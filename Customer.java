public class Customer{
    private long arrivalTime;
    private long servedTime;
    private long serviceTime;
    private boolean served;
    private boolean departed;

    public Customer(long arrivalTime){
        this.arrivalTime = arrivalTime;
        this.serviceTime= ThreadLocalRandom.current().nextInt(60, 300);
        this.servedTime=arrivalTime;
        this.served = false;
        this.departed = false;
    }

    public Customer(Customer copy){
        this.arrivalTime=copy.getArrivalTime();
        this.serviceTime=copy.getServiceTime();
        this.servedTime=copy.getServedTime();
        this.served=false
        this.departed=false;
    }

    public long getArrivalTime(){
        return arrivalTime;
    }

    public long getServiceTime(){
        return serviceTime;
    }
    public long getServedTime(){
        return servedTime;
    }
    public boolean isServed(){
        return served;
    }
    public boolean isDeparted(){
        return departed;
    }
    public void setServed(boolean served){
        this.served=served;
    }
    public void setDeparted(boolean departed){
        this.departed=departed;
    }
    public void setServedTime(long servedTime){
        this.servedTime=servedTime;
    }
    
}