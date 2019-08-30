package ch.hevs.swap.data.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class Appart {

    public String type;
    public long nbRooms;
    public long price;
    public String addressStreet;
    public long addressNo;
    public String localityId;
    public String comment;
    public ArrayList<String> pics;

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
        pics = new ArrayList<String>();
    }

    public Appart(String type, Integer nbRooms, Integer price, String addressStreet, Integer addressNo, String localityId, String comment,ArrayList<String> pics) {
        this.type = type;
        this.nbRooms = nbRooms;
        this.price = price;
        this.addressStreet = addressStreet;
        this.addressNo = addressNo;
        this.localityId = localityId;
        this.comment = comment;
        this.pics = pics;
    }

    public Appart(String type, long nbRooms, long price, String adressStreet, String mouais) {
        this.type = type;
        this.nbRooms = nbRooms;
        this.price = price;
        this.addressStreet = adressStreet;
    }

    public Appart(String type, long nbRooms, long price, String adressStreet) {
        this.type = type;
        this.nbRooms = nbRooms;
        this.price = price;
        this.addressStreet = adressStreet;
    }

}
