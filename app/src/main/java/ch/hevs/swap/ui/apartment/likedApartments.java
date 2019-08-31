package ch.hevs.swap.ui.apartment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ch.hevs.swap.R;
import ch.hevs.swap.data.models.AppartController;
import ch.hevs.swap.data.models.UserController;
import ch.hevs.swap.ui.homepage.BaseActivity;
import ch.hevs.swap.ui.homepage.HomepageBuyer;
import ch.hevs.swap.ui.homepage.HomepageSeller;

public class likedApartments extends BaseActivity {
    private ListView mListView;
    private ArrayList<String> apartLiked = new ArrayList<>();
    private UserController userController = new UserController();
    private String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
            "Sophie", "Tristan", "Ulric", "Vincent", "Willy", "Xavier",
            "Yann", "Zoé"
    };
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
                        System.out.println("################" + appartUID + " " + issue.getValue().toString());
                        if(issue.getValue().toString().equals("true"))
                        {
                            apartLiked.add(issue.getKey());
                            //apartLiked.add(new AppartController().GetNameApp(appartUID));
                        }
                    }
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(likedApartments.this,
                            android.R.layout.simple_list_item_1, apartLiked);
                    mListView.setAdapter(adapter);
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
}
