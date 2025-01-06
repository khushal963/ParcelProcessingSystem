import javax.swing.table.AbstractTableModel;
import java.util.List;

class ParcelTableModel extends AbstractTableModel {
    private final List<Parcel> parcels;
    private final String[] columnNames = {"ID", "Weight", "Days in Depot", "Collected"};

    public ParcelTableModel(List<Parcel> parcels) {
        this.parcels = parcels;
    }

    @Override
    public int getRowCount() {
        return parcels.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Parcel parcel = parcels.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return parcel.id;
            case 1:
                return parcel.weight;
            case 2:
                return parcel.daysInDepot;
            case 3:
                return parcel.isCollected;
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}