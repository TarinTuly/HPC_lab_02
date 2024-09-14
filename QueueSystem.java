

import QueueSimulator.java;
public class QueueSystem{
    public static void main(String[] args) throws InteruptedException{
        GroceryQueues groceryQueues = new GroceryQueues(3, 5);
        int numofTellers = 3;
        int maxBankQueueLength = 5;
        int numofCashiers = 3;
        int maxGroceryQueueLength = 2;
        int simulationTime=2;   //2 hours

        QueueSimulator simulator = new QueueSimulator(numofTellers, maxBankQueueLength, numofCashiers, maxGroceryQueueLength, simulationTime);
        simulator.simulate();
        simulator.printResults();
    
    }

}  