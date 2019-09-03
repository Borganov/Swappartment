package ch.hevs.swap.data.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class Appart {

    public String designation;
    public long nbRooms;
    public long price;
    public String addressStreet;
    public Long idLocality;
    public String comment;
    public ArrayList<String> pics;

    public Appart() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Appart(String designation, Integer nbRooms, Integer price, String addressStreet, Long idLocality, String comment) {
        this.designation = designation;
        this.nbRooms = nbRooms;
        this.price = price;
        this.addressStreet = addressStreet;
        this.idLocality = idLocality;
        this.comment = comment;
        pics = new ArrayList<String>();
    }

    public Appart(String designation, Integer nbRooms, Integer price, String addressStreet, Long idLocality, String comment,ArrayList<String> pics) {
        this.designation = designation;
        this.nbRooms = nbRooms;
        this.price = price;
        this.addressStreet = addressStreet;
        this.idLocality = idLocality;
        this.comment = comment;
        this.pics = pics;
    }

    public Appart(String designation, long nbRooms, long price, String adressStreet, String mouais) {
        this.designation = designation;
        this.nbRooms = nbRooms;
        this.price = price;
        this.addressStreet = adressStreet;
    }

    public Appart(String designation, long nbRooms, long price, String adressStreet, Long idLocality) {
        this.designation = designation;
        this.nbRooms = nbRooms;
        this.price = price;
        this.addressStreet = adressStreet;
        this.idLocality = idLocality;
    }

}
