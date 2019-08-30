package ch.hevs.swap.ui.search;




import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.hevs.swap.R;
import ch.hevs.swap.data.models.Appart;
import ch.hevs.swap.ui.homepage.BaseActivity;
import ch.hevs.swap.ui.homepage.HomepageSeller;

public class Buyer_Appart extends BaseActivity {

    private Button mBtnLaunchSearch;

    private Switch switchBuyerSeller;

    List<String> countries;
    List<Appart> apparts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_appart);


        switchBuyerSeller = (Switch) findViewById(R.id.Switch);
        switchBuyerSeller.setChecked(false);
        switchBuyerSeller.setTextOn("Seller");
        switchBuyerSeller.setTextOff("Buyer");




//        mSearchField = findViewById(R.id.searchField);
        mBtnLaunchSearch = findViewById(R.id.btnLaunchSearch);
        countries = new ArrayList<>();

        ListCountries();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, countries);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.autoComplete_Locality);
        textView.setDropDownVerticalOffset(2);
        textView.setAdapter(adapter);


        apparts = new ArrayList<>();

        mBtnLaunchSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    };

    public void search() {

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.root);
        ConstraintSet set = new ConstraintSet();

        String[] results = new String[10];
        ListAppart();
        //   mBtnLaunchSearch.setText(apparts.get(1).addressStreet);

        for (int i = 0; i < results.length; i++) {
            TextView view = new TextView(this);
            view.setId(View.generateViewId());
            view.setText(results[i]);
            layout.addView(view, i);
            set.clone(layout);
            set.connect(view.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP, 60 + (i * 70));
            set.applyTo(layout);
        }

    };


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
                        apparts.add(new Appart((String) issue.child("type").getValue(), (long) issue.child("nbRooms").getValue(), (long) issue.child("price").getValue(), (String) issue.child("addressStreet").getValue(), (String) issue.child("userId").getValue()));

                        //String type, int nbRooms, int price, String adressStreet, String userId
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void ListCountries() {

    FirebaseDatabase mDatabase;
    DatabaseReference mDataBaseRef = FirebaseDatabase.getInstance().getReference();
    mDatabase = FirebaseDatabase.getInstance();

    Query query = mDataBaseRef.child("Localities");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String rst;
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        rst = (String) issue.child("nameLocality").getValue() + " (" + (String) issue.child("npa").getValue() + ")";
                        System.out.println(rst);
                        countries.add(rst);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        switchBuyerSeller.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String statusSwitch1;
                if (switchBuyerSeller.isChecked()) {
                    // The toggle is enabled


                    statusSwitch1 = switchBuyerSeller.getTextOn().toString();
                    Intent homepageSeller = new Intent (Buyer_Appart.this, HomepageSeller.class);
                    startActivity(homepageSeller);
                    finish();


                } else {
                    // The toggle is disabled

                    statusSwitch1 = switchBuyerSeller.getTextOff().toString();
                    Intent homepageBuyer = new Intent (Buyer_Appart.this, Buyer_Appart.class);
                    startActivity(homepageBuyer);
                    finish();
                }
            }
        });

    }

}