import java.io.IOException;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        try {
            Depot manager = new Depot();
            manager.readParcelsFromFile("D:\\Khushal\\ParcelProcessingSystem\\src\\Parcels.txt");
            manager.readCustomersFromFile("D:\\Khushal\\ParcelProcessingSystem\\src\\Customer.txt");
            manager.processCustomers();
            manager.generateReport("Report.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
