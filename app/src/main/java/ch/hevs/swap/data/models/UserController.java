package ch.hevs.swap.data.models;

import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ch.hevs.swap.ui.apartment.likedApartments;

public class UserController {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    final ArrayList<String> lstapartLiked = new ArrayList<>();

    public UserController() {
        //apartLiked();
    }
    public ArrayList<String> getLstapartLiked() {
        return lstapartLiked;
    }

    public void addApartViewed(String uidApp, boolean like)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        String key = mDatabase.child("users/" + mAuth.getCurrentUser().getUid()+"/apartViewed").push().getKey();

        mDatabase.child("users/"+ mAuth.getCurrentUser().getUid()+"/apartViewed/" + key + "/AppId").setValue(uidApp);
        mDatabase.child("users/"+ mAuth.getCurrentUser().getUid()+"/apartViewed/" + key + "/like").setValue(like);
        // usercon.AddApartViewed(appartQueue.getFirst().getInfo().getValeur(),true);
    }

    public  void addMessage(String uidApp, String SellerApp) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        String key = mDatabase.child("/users/" + SellerApp + "/notifications").push().getKey();
        mDatabase.child("users/" + SellerApp + "/notifications/" + key + "/AppId").setValue(uidApp);
        mDatabase.child("users/" + SellerApp + "/notifications/" + key + "/BuyerId").setValue(mAuth.getCurrentUser().getUid());

    }

    /*  public void addApartViewed(String uidApp, boolean like, String name)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        if(like)
        {
            mDatabase.child("users/"+ mAuth.getCurrentUser().getUid()+"/apartLiked/" + uidApp).setValue(name);
        } else
        {
            mDatabase.child("users/"+ mAuth.getCurrentUser().getUid()+"/apartDislike/" + uidApp).setValue(like);
        }

        // usercon.AddApartViewed(appartQueue.getFirst().getInfo().getValeur(),true);
    }
    */

    public void apartLiked () {


        DatabaseReference mDataBaseRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        Query query = mDataBaseRef.child("users/" + mAuth.getCurrentUser().getUid() + "/apartViewed");
        lstapartLiked.add("test 1");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String appartUID;
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        appartUID = (String) issue.getKey();
                        System.out.println("################"  + appartUID + " " + issue.getValue().toString());
                        AddinList(appartUID);
                        lstapartLiked.add(appartUID);
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public void AddinList(String appart)
    {
        lstapartLiked.add(appart);
    }
}