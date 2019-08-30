package ch.hevs.swap.data.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserController {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public void addApartViewed(String uidApp, boolean like)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mDatabase.child("users/"+ mAuth.getCurrentUser().getUid()+"/apartViewed/" + uidApp).setValue(like);
        // usercon.AddApartViewed(appartQueue.getFirst().getInfo().getValeur(),true);
    }

    public ArrayList<String> apartLiked () {

        ArrayList<String> lstapartLiked = new ArrayList<>();
        DatabaseReference mDataBaseRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        Query query = mDataBaseRef.child("users/" + mAuth.getCurrentUser().getUid() + "/apartViewed/");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String appartUID;
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        appartUID = (String) issue.getKey();
                        System.out.println("################"  + appartUID + " " + issue.getValue().toString());
                        lstapartLiked.add(appartUID);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return lstapartLiked;
    }
}