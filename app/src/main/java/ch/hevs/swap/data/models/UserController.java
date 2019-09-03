package ch.hevs.swap.data.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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

    public void getLikeByOwner(){
        ArrayList<String> listApartment;
        DatabaseReference mDataBaseRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        Query query = mDataBaseRef.child("users/" + mAuth.getCurrentUser().getUid() + "/apartmentOwned/");

        query.addListenerForSingleValueEvent (new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
//                        listApartment.add(issue.getKey());
//                        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ " + issue.getKey());
                        Query query1 = mDataBaseRef.child("appart/" + issue.getKey()+"/Likes");

                        query1.addListenerForSingleValueEvent (new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    int nbLikes = 0;
                                    // dataSnapshot is the "issue" node with all children with id 0
                                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
//                        listApartment.add(issue.getKey());
                                        nbLikes++;
                                        System.out.println("********************************************** " + " number " + nbLikes + " " + issue.getValue());
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        for (String idApart: listApartment) {
//            Query query1 = mDataBaseRef.child("appart/" + idApart + "/Likes/").orderByChild(idApart).equalTo(idApart);
//
//            query.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    ArrayList<String> listLikes = new ArrayList<>();
//
//                    if (dataSnapshot.exists()) {
//                        // dataSnapshot is the "issue" node with all children with id 0
//                        for (DataSnapshot issue : dataSnapshot.getChildren()) {
//                            listLikes.add(issue.getKey());
//                            System.out.println("%%%%%%%%%%%%%%" + issue.getKey());
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }

    }
}