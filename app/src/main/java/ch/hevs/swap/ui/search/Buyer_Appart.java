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

import java.util.ArrayList;
import java.util.List;

import ch.hevs.swap.R;
import ch.hevs.swap.data.models.Appart;

public class Buyer_Appart extends AppCompatActivity {

    private EditText mSearchField;

    private Button mBtnLaunchSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_appart);

        mSearchField = findViewById(R.id.searchField);
        mBtnLaunchSearch = findViewById(R.id.btnLaunchSearch);

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

        String[] results = {
                "home1",
                "home2",
                "home3"
        };

        for(int i = 0; i<results.length; i++)
        {
            TextView view = new TextView(this);
            view.setId(View.generateViewId());
            view.setText(results[i]);
            layout.addView(view, i);
            set.clone(layout);
            set.connect(view.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP, 60+(i*70));
            set.applyTo(layout);
        }

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
