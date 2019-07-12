package ch.hevs.swap.ui.search;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.hevs.swap.R;
import ch.hevs.swap.data.models.Appart;

public class Buyer_Appart extends AppCompatActivity {

    private EditText mSearchField;

    private Button mBtnLaunchSearch;
    List<Appart> apparts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_appart);

        mSearchField = findViewById(R.id.searchField);
        mBtnLaunchSearch = findViewById(R.id.btnLaunchSearch);
        apparts = new ArrayList<>();

        mBtnLaunchSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });


    }

    public void search() {

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.root);
        ConstraintSet set = new ConstraintSet();

        String[] results = new String[10];
         ListAppart();
     //   mBtnLaunchSearch.setText(apparts.get(1).addressStreet);



    }

    private void ListAppart() {
        FirebaseDatabase mDatabase;
        DatabaseReference mDataBaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance();
       //mDataBaseRef = mDatabase.getReference().child("swap-appartements").child("appart");


        Query query = mDataBaseRef.child("appart");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        apparts.add(new Appart((String)issue.child("type").getValue(), (long)issue.child("nbRooms").getValue(),(long)issue.child("price").getValue(),(String)issue.child("addressStreet").getValue(),(String)issue.child("userId").getValue()));

                        //String type, int nbRooms, int price, String adressStreet, String userId
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
