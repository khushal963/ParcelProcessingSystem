import java.util.LinkedList;
import java.util.Queue;

public class QueueManager {
    private Queue<Customer> customerQueue = new LinkedList<>();

    public QueueManager() {
    }

    public void addCustomer(Customer customer) {
        this.customerQueue.add(customer);
    }

    public Customer getNextCustomer() {
        return this.customerQueue.poll();
    }

    public boolean isEmpty() {
        return this.customerQueue.isEmpty();
    }

    public int size() {
        return this.customerQueue.size();
    }

    public Queue<Customer> getCustomerQueue() {
        return this.customerQueue;
    }
}