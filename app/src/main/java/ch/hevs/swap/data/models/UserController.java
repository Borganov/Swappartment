package ch.hevs.swap.data.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserController {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public void AddApartViewed(String uidApp, boolean like)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mDatabase.child("users/"+ mAuth.getCurrentUser().getUid()+"/apartViewed/" + uidApp).setValue(like);
        // usercon.AddApartViewed(appartQueue.getFirst().getInfo().getValeur(),true);
    }

}