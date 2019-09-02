package ch.hevs.swap.ui.homepage;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import ch.hevs.swap.R;
import ch.hevs.swap.ui.search.Buyer_Appart;


public class HomepageSeller extends BaseActivity {


    ListView listView;

    private Button addAppartement;

    private ArrayAdapter <String> adapter;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String apartmentKey;
    private FirebaseDatabase mDatabase;
    //FIREBASE
    private FirebaseStorage storage;
    private StorageReference storageReference;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_homepage);

        listView=(ListView)findViewById(R.id.listview);
        user =FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        ArrayList<String> itemList = new ArrayList<>();

        //Firebase init
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        //get param
       // Bundle b = getIntent().getExtras();
        //apartmentKey = b.getString("key");

        apartmentKey = null;

        databaseReference = FirebaseDatabase.getInstance().getReference("users/"+uid);
        mDatabase = FirebaseDatabase.getInstance();

        Query query = databaseReference.child("apartmentOwned");

        query.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    itemList.clear();
                    String userApartment;
                    // dataSnapshot is the "issue" node with all children with id 0

                    userApartment = dataSnapshot.child(uid).child("ap").getValue(String.class);
                    itemList.add(userApartment);




                    adapter = new ArrayAdapter(HomepageSeller.this, android.R.layout.simple_list_item_1, itemList);
                    listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });












        final Switch sw = (Switch) findViewById(R.id.Switch);
        sw.setChecked(true);
        sw.setTextOn("Seller");
        sw.setTextOff("Buyer");


        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String statusSwitch1;
                if (sw.isChecked()) {
                    // The toggle is enabled
                    statusSwitch1 = sw.getTextOn().toString();
                    Intent homepageSeller = new Intent (HomepageSeller.this, HomepageSeller.class);
                    startActivity(homepageSeller);
                    finish();


                } else {
                    // The toggle is disabled
                    statusSwitch1 = sw.getTextOff().toString();
                    Intent homepageBuyer = new Intent (HomepageSeller.this, Buyer_Appart.class);
                    startActivity(homepageBuyer);
                    finish();
                }
            }
        });



            //Intialization Button

        addAppartement = (Button) findViewById(R.id.addAppartementFromSeller);

        addAppartement.setOnClickListener((View.OnClickListener) HomepageSeller.this);
            //Here MainActivity.this is a Current Class Reference (context)
        }


        public void onClick(View addApartmentDetails) {

            Intent intent = new Intent(HomepageSeller.this, addApartmentDetails.class);
        }


    }


