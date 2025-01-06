import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
public class ParcelManager {



        private Map<String, Parcel> parcelManager= new HashMap();

        public ParcelManager() {
        }

        public void addParcel(Parcel parcel) {
            this.parcelManager.put(parcel.id, parcel);
        }

        public Parcel findParcelById(String id) {
            return (Parcel)this.parcelManager.get(id);
        }

        public void removeParcel(String id) {
            this.parcelManager.remove(id);
        }

        public Collection<Parcel> getAllParcels() {
            return this.parcelManager.values();
        }
    }


