public class Parcel {
    String id;
    int weight;
    int daysInDepot;
    boolean isCollected;

    public Parcel(String id, int weight, int daysInDepot) {
        this.id = id;
        this.weight = weight;
        this.daysInDepot = daysInDepot;
        this.isCollected = false;
    }
    public void setCollected(boolean collected) {
        this.isCollected = collected;
    }

    // Add a getter for isCollected if needed
    public boolean isCollected() {
        return isCollected;
    }
}
