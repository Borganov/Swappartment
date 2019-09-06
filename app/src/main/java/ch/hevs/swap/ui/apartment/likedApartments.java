package ch.hevs.swap.ui.apartment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;

import ch.hevs.swap.R;
import ch.hevs.swap.data.models.Appart;
import ch.hevs.swap.data.models.AppartController;
import ch.hevs.swap.data.models.UserController;
import ch.hevs.swap.ui.homepage.BaseActivity;
import ch.hevs.swap.ui.homepage.HomepageBuyer;
import ch.hevs.swap.ui.homepage.HomepageSeller;
import ch.hevs.swap.ui.search.SearchApart;

public class likedApartments extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private ArrayList<String> apartLiked = new ArrayList<>();
    ArrayList<String> apartLikedId = new ArrayList<>();
    int index = 0;

    private UserController userController = new UserController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_apartments);
        mListView = (ListView) findViewById(R.id.listView);

        //android.R.layout.simple_list_item_1 est une vue disponible de base dans le SDK android,
        //Contenant une TextView avec comme identifiant "@android:id/text1"


        DatabaseReference mDataBaseRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();


        Query query = mDataBaseRef.child("users/" + mAuth.getCurrentUser().getUid() + "/apartViewed");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String appartUID;
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        appartUID = (String) issue.getKey();
                        //System.out.println("################" + appartUID + " " + issue.getValue().toString());
                        if(issue.child("like").getValue().toString().equals("true"))
                        {
                            apartLikedId.add(issue.child("AppId").getValue().toString());
                            //apartLiked.add(new AppartController().GetNameApp(appartUID));
                        }
                    }

                    Query queryNameApart = FirebaseDatabase.getInstance().getReference("appart");
                    queryNameApart.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(String apartId:apartLikedId)
                            {
                                String apartementName = dataSnapshot.child(apartId).child("designation").getValue(String.class);

                                apartLiked.add(apartementName);

                            }

                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(likedApartments.this,
                                    android.R.layout.simple_list_item_1, apartLiked);
                            mListView.setAdapter(adapter);

                            mListView.setOnItemClickListener(likedApartments.this::onItemClick);
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

    }

    public void onBackPressed() {
        this.startActivity(new Intent(this, HomepageBuyer.class));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(likedApartments.this,"Vous avez fait une demande pour l'appartement :  " + apartLiked.get(position), Toast.LENGTH_LONG).show();
       // userController.addMessage(apartLiked.get(position));
        DatabaseReference mDatabase;
        FirebaseAuth mAuth;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        ArrayList<String> users = new ArrayList<>();
        Query query = mDatabase.child("users");
        String theapartLiked = apartLikedId.get(position);
        index = 0;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                users.clear();
                if (dataSnapshot.exists()) {
                    String rst;
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        rst = (String) issue.getKey();
                        users.add(rst);
                    }

                    for (String user : users) {
                        Query query1 = mDatabase.child("users/" + user + "/apartmentOwned");
                        query1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String rst = "noId";

                                    // dataSnapshot is the "issue" node with all children with id 0
                                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                        rst = (String) issue.child("AppId").getValue();
                                        if(rst != null) {
                                            if (((String) issue.child("AppId").getValue()).contains(theapartLiked) && index < 1) {
                                                userController.addMessage(theapartLiked,user);
                                                index = 1;
                                            }
                                        }
                                        if(index==1)
                                            break;
                                    }
                                    if(index==1)
                                        return;
                                }
                                if(index==1)
                                    return;
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    if(index==1)
                        return;

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
