package ch.hevs.swap.ui.search;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.List;

import ch.hevs.swap.R;
import ch.hevs.swap.data.models.Appart;

public class Buyer_Appart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_appart);
    }

    private void ListAppart() {
        FirebaseDatabase mDatabase;
        DatabaseReference mDataBaseRef;
        mDatabase = FirebaseDatabase.getInstance();
        mDataBaseRef = mDatabase.getReference().child("appart");
         List<Appart> apparts = new ArrayList<>();

        mDataBaseRef.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Put a new user in the list
                // Cast the dataSnapshot data to the User class
                Appart appart = dataSnapshot.getValue(Appart.class);
                apparts.add(appart);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        apparts.get(3);
    }
}
