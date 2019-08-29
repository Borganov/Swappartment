package ch.hevs.swap.data.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Appart {

    public String type;
    public long nbRooms;
    public long price;
    public String addressStreet;
    public long addressNo;
    public String localityId;
    public String comment;
    public String userId;

    public Appart(String type, long nbRooms, long price, String addressStreet, String userId) {
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

    public Appart(String type, long nbRooms, long price, String adressStreet, String userId) {
        this.type = type;
        this.nbRooms = nbRooms;
        this.price = price;
        this.addressStreet = adressStreet;
        this.userId = userId;
    }

}
