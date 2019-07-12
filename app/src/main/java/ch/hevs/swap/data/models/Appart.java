package ch.hevs.swap.data.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Appart {

    public String type;
    public Integer nbRooms;
    public Integer price;
    public String addressStreet;
    public Integer addressNo;
    public String localityId;
    public String comment;

    public Appart() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Appart(String type, Integer nbRooms, Integer price, String addressStreet, Integer addressNo, String localityId, String comment) {
        this.type = type;
        this.nbRooms = nbRooms;
        this.price = price;
        this.addressStreet = addressStreet;
        this.addressNo = addressNo;
        this.localityId = localityId;
        this.comment = comment;
    }

}
