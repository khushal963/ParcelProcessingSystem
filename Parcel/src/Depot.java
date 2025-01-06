
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Depot {
    private QueueManager queueOfCustomers = new QueueManager();
    private ParcelManager parcelMap = new ParcelManager();
    private DepotWorker worker;
    private Set<String> collectedParcels;
    private Set<String> uncollectedParcels;

    public Depot() {
        this.worker = new DepotWorker(this.parcelMap);
        this.collectedParcels = new HashSet();
        this.uncollectedParcels = new HashSet();
    }

    public void readParcelsFromFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));

        String line;
        while((line = br.readLine()) != null) {
            String[] parts = line.split("\\s+");
            Parcel parcel = new Parcel(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            this.parcelMap.addParcel(parcel);
            Report.getInstance().addEvent("Read parcel: " + parcel.id);
            System.out.println("Read parcel: " + parcel.id);
        }

        br.close();
    }

    public void readCustomersFromFile(String fileName) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String name = parts[0] + " " + parts[1];
                    String parcelId = parts[2];
                    Customer customer = new Customer(this.queueOfCustomers.size() + 1, name, parcelId);
                    this.queueOfCustomers.addCustomer(customer);
                    Report.getInstance().addEvent("Read customer: " + name + " with parcel " + parcelId);
                    System.out.println("Read customer: " + name + " with parcel " + parcelId);
                } else {
                    Report.getInstance().addEvent("Invalid line format: " + line);
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void processCustomers() {
        while(!this.queueOfCustomers.isEmpty()) {
            Customer customer = this.queueOfCustomers.getNextCustomer();
            this.worker.processCustomer(customer);
            this.collectedParcels.add(customer.parcelId);
            Report.getInstance().addEvent("Processed customer: " + customer.getName());
        }

    }

    public void generateReport(String filename) throws IOException {
        Report.getInstance().addEvent("Collected Parcels:");

        for(Parcel parcel : this.parcelMap.getAllParcels()) {
            if (parcel.isCollected) {
                double fee = this.worker.calculateFee(parcel);
                Report.getInstance().addEvent(parcel.id + " collected with fee: " + fee);
            } else {
                this.uncollectedParcels.add(parcel.id);
            }
        }

        Report.getInstance().addEvent("\nUncollected Parcels:");

        for(String parcelId : this.uncollectedParcels) {
            Report.getInstance().addEvent(parcelId + " waiting for collection");
        }

        Report.getInstance().writeLogToFile(filename);
    }

    public static void main(String[] args) {
        try {
            Depot manager = new Depot();
            manager.readParcelsFromFile("D:\\Khushal\\ParcelProcessingSystem\\src\\Parcels.txt");
            manager.readCustomersFromFile("D:\\Khushal\\ParcelProcessingSystem\\src\\Customer.txt");
            manager.processCustomers();
            manager.generateReport("log.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
