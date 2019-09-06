package ch.hevs.swap.ui.homepage;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
import java.util.HashMap;

import ch.hevs.swap.R;
import ch.hevs.swap.ui.apartment.addApartmentDetails;
import ch.hevs.swap.ui.message.MessageDetails;
import ch.hevs.swap.ui.message.MessageList;


public class HomepageSeller extends BaseActivity {


    ListView listView;

    private Button addAppartement;

    private Button notifications;

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
        ArrayList<String> apartmentIdList = new ArrayList<>();

        //Firebase init
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        //get param
        // Bundle b = getIntent().getExtras();
        //apartmentKey = b.getString("key");

        apartmentKey = null;

        mDatabase = FirebaseDatabase.getInstance();
        databaseReference = mDatabase.getReference("users/"+uid);

        notifications = (Button)findViewById(R.id.notificationButton);

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notif();
            }
        });

        Query query = databaseReference.child("apartmentOwned");

        query.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                itemList.clear();
                apartmentIdList.clear();

                // dataSnapshot is the "issue" node with all children with id 0

                for (DataSnapshot childrenSnapshot:dataSnapshot.getChildren())
                {

                    String classinfo = childrenSnapshot.getValue().getClass().toString();
                    HashMap<String,String> AppInfo = (HashMap<String, String>) childrenSnapshot.getValue();

                  //  String apartmentId = childrenSnapshot.getValue().toString();

                    apartmentIdList.add(AppInfo.get("AppId"));

                }
                Query queryNameApart = mDatabase.getReference("appart");
                queryNameApart.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(String apartId:apartmentIdList)
                        {
                            String apartementName = dataSnapshot.child(apartId).child("designation").getValue(String.class);
                            itemList.add(apartementName);

                        }

                        adapter = new ArrayAdapter(HomepageSeller.this, android.R.layout.simple_list_item_1, itemList);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







        //Intialization Button
        addAppartement = findViewById(R.id.addAppartementFromSeller);

        addAppartement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAppartment();

            }
        });
    }

    public void notif(){
        Intent intent = new Intent(this, MessageList.class);
        startActivity(intent);
    }

    public void addAppartment() {
        Intent intent = new Intent(this, addApartmentDetails.class);
        startActivity(intent);
    }

}