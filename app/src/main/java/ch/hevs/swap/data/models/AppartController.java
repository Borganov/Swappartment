package ch.hevs.swap.data.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AppartController {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String name = "Pas de nom";

    public String insertNewAppart(String designation, int nbRooms, int price, String address, Long idLocality) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        Appart appart = new Appart(designation, nbRooms, price, address, idLocality);
        String key = mDatabase.child("appart").push().getKey();
        mDatabase.child("/appart/" + key).setValue(appart);

        String key2 = mDatabase.child("/users/"+uid+"/apartmentOwned").push().getKey();
        mDatabase.child("/users/"+uid+"/apartmentOwned/"+key2+"/AppId").setValue(key);

        return key;
    }

    public void addImageLinkToAppart(String apartmentKey, String uuidImage) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        String key = mDatabase.child("/appart/" + apartmentKey + "/imgs").push().getKey();
        mDatabase.child("/appart/" + apartmentKey + "/imgs/" + key).setValue(uuidImage);

    }
    public String GetNameApp(String UID)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("/appart/" + UID + "/type");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        name = issue.getValue(String.class);
                        System.out.println(name);
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    return name;
    }
}
