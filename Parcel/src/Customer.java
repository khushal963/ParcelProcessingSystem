public class Customer {
    int sequenceNumber;
    String name;
    String parcelId;

    public Customer(int sequenceNumber, String name, String parcelId) {
        this.sequenceNumber = sequenceNumber;
        this.name = name;
        this.parcelId = parcelId;
    }

    public String getName() {
        return this.name;
    }
}
