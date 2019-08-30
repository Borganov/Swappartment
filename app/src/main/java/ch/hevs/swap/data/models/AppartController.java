package ch.hevs.swap.data.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AppartController {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public String insertNewAppart(String designation, int nbRooms, int price, String address, Long idLocality) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        Appart appart = new Appart(designation, nbRooms, price, address, idLocality);
        String key = mDatabase.child("appart").push().getKey();
        mDatabase.child("/appart/" + key).setValue(appart);

        String key2 = mDatabase.child("/users/"+uid+"/apartmentOwned").push().getKey();
        mDatabase.child("/users/"+uid+"/apartmentOwned/"+key2).setValue(key);

        return key;
    }

    public void addImageLinkToAppart(String apartmentKey, String uuidImage) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        String key = mDatabase.child("/appart/" + apartmentKey + "/imgs").push().getKey();
        mDatabase.child("/appart/" + apartmentKey + "/imgs/" + key).setValue(uuidImage);

    }
}
