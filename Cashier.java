import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;
import Customer.java;
import QueueSimulator.java;


public class Cashier implements Runnable{
    private final Queue<Customer> queue;
    private final int maxGroceryQueueLength;
    private final ReentrantLock lock;
    private boolean running;
    // private final QueueSimulator simulator;
    private final int id;
    public Cashier(int maxGroceryQueueLength, QueueSimulator simulator, int id) {
        this.queue = new LinkedList<>();
        this.maxGroceryQueueLength = maxGroceryQueueLength;
        this.lock = new ReentrantLock();
        this.running = true;
        // this.simulator = simulator;
        this.id = id;
    }
    public int getQueueSize() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
    public int getMaxQueueLength() {
        return maxGroceryQueueLength;
    }
    public boolean addCustomer(Customer customer) {
        lock.lock();
        try {
            if (queue.size() < maxGroceryQueueLength) {
                System.out.printf("Cashier Queue %d got new customer.\n\n", id);
                queue.offer(customer);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }
    public void stop() {
        running = false;
    }
    @Override
    public void run() {
        System.out.printf("Cashier %d starts working.\n", id);
        while (running) {
            Customer customer = null;
            lock.lock();
            try {
                if (!queue.isEmpty()) {
                    customer = queue.poll();
                }
            } finally {
                lock.unlock();
            }

            if (customer != null) {
                try {
                    int serviceTime = customer.getServiceTime(); // Use customer-specific service time
                    customer.setServedTime(System.currentTimeMillis());
                    System.out.printf("Cashier %d served customer, service time : %d\n\n", id, serviceTime);
                    Thread.sleep(serviceTime * 1000L); // Simulate service time
                    customer.setServed(true);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            try {
                Thread.sleep(100); // Check for new customers every 100 milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.printf("Cashier %d stops.\n", id);
        
    }

}