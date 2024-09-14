Task Overview:
BankQueue Class:

Modeled after bank queues where there is one single queue for multiple tellers.
Customers are served by the first available teller.
The constructor takes the number of tellers and the maximum queue length as parameters.
If the queue is full when a customer arrives, they immediately leave.
GroceryQueues Class:

Modeled after grocery store queues where each cashier has their own queue.
Customers join the queue with the fewest people waiting.
If two or more queues have the same number of people, a queue is chosen at random.
The constructor takes the number of cashiers and the maximum queue length as parameters.
If no queue has space, the customer waits for up to 10 seconds before leaving.
Customer Class:

Tracks the arrival time of each customer.
Records the time taken to serve the customer.
Tracks if the customer left without being served.
QueueSimulator Class:

Manages events, simulates the passage of time (ticks every second), and processes customer events.
It takes simulation time (in minutes) as input and outputs:
Total number of customers arrived.
Total number of customers who left without being served.
Total number of customers served.
The average time taken to serve each customer.
Requirements:
Implement the simulation for 2 hours of simulation with:
3 tellers in the BankQueue with a maximum queue length of 5.
3 cashiers in the GroceryQueues with a maximum queue length of 2.
Use synchronization mechanisms like locks, semaphores, and monitors to ensure thread safety in concurrent operations.
Output:
For both queue types, output the following:
Total customers arrived.
Total customers who left without being served.
Total customers served.
Average service time per customer.
