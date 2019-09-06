package ch.hevs.swap.ui.message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ch.hevs.swap.R;
import ch.hevs.swap.ui.homepage.BaseActivity;
import ch.hevs.swap.ui.homepage.HomepageSeller;
import ch.hevs.swap.ui.search.AppartDetails;
import ch.hevs.swap.ui.search.SearchApart;

public class MessageList extends BaseActivity {

    ArrayList<Message> messages;
    ArrayList<String> itemList;
    String sellerUid;

    private ArrayAdapter <String> adapter;
    ListView messageListView;

    private FirebaseDatabase mDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference dbRefBuyer;
    private DatabaseReference dbRefAppartment;
    private FirebaseUser user;

    private String appartmentName;
    private String buyerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        messageListView =(ListView)findViewById(R.id.messageListView);

        //Get user id
        user = FirebaseAuth.getInstance().getCurrentUser();
        sellerUid = user.getUid();



        //Get info from Seller
        mDatabase = FirebaseDatabase.getInstance();
        databaseReference = mDatabase.getReference("users/"+sellerUid);
        dbRefAppartment = mDatabase.getReference("appart/");
        dbRefBuyer = mDatabase.getReference("users/");

        messages = new ArrayList<Message>();
        itemList = new ArrayList<String>();

        //GET NOTIFICATIONS LIST, WE LOOP ON IT
        Query query = databaseReference.child("notifications");
        query.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear list if needed
                messages.clear();
                itemList.clear();
                for (DataSnapshot notif:dataSnapshot.getChildren())
                {
                    String idApp =  (String) notif.child("AppId").getValue();
                    String idBuyer =  (String) notif.child("BuyerId").getValue();

                    // GET APPARTMENT NAME
                    Query query = dbRefAppartment.child(idApp);
                    query.addValueEventListener(new ValueEventListener(){
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            appartmentName = (String) dataSnapshot.child("designation").getValue();

                            // GET BUYER NAME
                            Query query = dbRefBuyer.child(idBuyer);
                            query.addValueEventListener(new ValueEventListener(){
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    buyerName = (dataSnapshot.child("name").getValue() + " " + dataSnapshot.child("firstname").getValue());

                                    //Création du message
                                    String text = buyerName + " a aimé la maison : " + appartmentName;
                                    messages.add(new Message(text, idBuyer, idApp));
                                    itemList.add(text);

                                    adapter = new ArrayAdapter(MessageList.this, android.R.layout.simple_list_item_1, itemList);
                                    messageListView.setAdapter(adapter);
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
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        messageListView.setClickable(true);
        messageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                openDialog(position);

            }
        });

    }



    public ArrayList<String> getMessageBySellerId(String idSeller){
        ArrayList<String> response = new ArrayList<String>();



        return response;
    }

    public void getAppartmentNameByIdApp(String idAppartment){
        dbRefBuyer = mDatabase.getReference("appart/");

        Query query = databaseReference.child(idAppartment);
        query.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                appartmentName = (String) dataSnapshot.child("designation").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public String getBuyerNameByIdBuyer(String idBuyer){
        final String[] name = new String[1];
        dbRefBuyer = mDatabase.getReference("users/");

        Query query = databaseReference.child(idBuyer);
        query.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name[0] = (dataSnapshot.child("name").getValue() + " " + dataSnapshot.child("name").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return name[0];
    }

    public void openDialog(int index) {

        FirebaseDatabase mDatabase;
        DatabaseReference mDataBaseRef = FirebaseDatabase.getInstance().getReference("users/");
        mDatabase = FirebaseDatabase.getInstance();

        String idBuyer = messages.get(index).getIdBuyer();

        Query queryDesignation = mDataBaseRef.child(idBuyer);

        queryDesignation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String information = "";

                    for (DataSnapshot apart : dataSnapshot.getChildren()) {
                        information = "Nom : " + dataSnapshot.child("firstname").getValue().toString()+" " + dataSnapshot.child("name").getValue().toString() + "\n" +
                                "E-mail : " + dataSnapshot.child("email").getValue().toString() + "\n" +
                                "Téléphone : " + dataSnapshot.child("tel").getValue().toString();
                    }

                    //open modal appartDetails with the information of the appartment
                    MessageDetails messageDetails = new MessageDetails(information); //parameter of AppartDetails = description of appartment
                    messageDetails.show(getSupportFragmentManager(), "Informations acheteur");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void onBackPressed() {
        this.startActivity(new Intent(this, HomepageSeller.class));
    }
}


class Message {
    String message;
    String idBuyer;
    String idApprtment;

    public Message(String message, String idBuyer, String idApprtment){
        this.message = message;
        this.idApprtment = idApprtment;
        this.idBuyer = idBuyer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIdBuyer() {
        return idBuyer;
    }

    public void setIdBuyer(String idBuyer) {
        this.idBuyer = idBuyer;
    }

    public String getIdApprtment() {
        return idApprtment;
    }

    public void setIdApprtment(String idApprtment) {
        this.idApprtment = idApprtment;
    }

}

