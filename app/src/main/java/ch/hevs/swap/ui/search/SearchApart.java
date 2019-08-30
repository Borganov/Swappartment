package ch.hevs.swap.ui.search;




import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
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

public class SearchApart extends AppCompatActivity {

    private Button mBtnLaunchSearch;

    List<String> localities;
    ArrayList<String> apparts;

    EditText mlocality;
    String locality;
    Long idLocality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_apart);
        mlocality = findViewById(R.id.autoComplete_Locality);
//        mSearchField = findViewById(R.id.searchField);
        mBtnLaunchSearch = findViewById(R.id.btnLaunchSearch);
        localities = new ArrayList<>();

        ListLocalities();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, localities);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.autoComplete_Locality);
        textView.setDropDownVerticalOffset(2);
        textView.setAdapter(adapter);

        apparts = new ArrayList<>();


        mBtnLaunchSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                locality = mlocality.getText().toString();
                if(!locality.isEmpty()){
                    idLocality = new Long(localities.indexOf(locality));
                    search(idLocality);

                }
                else{
                    Toast.makeText(SearchApart.this,"Veuillez introduire une localité", Toast.LENGTH_LONG).show();

                }
            }
        });
    };

    public void search(Long idLocality) {

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.root);
        ConstraintSet set = new ConstraintSet();

        String[] results = new String[10];
        ListAppart(idLocality);
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


    private void ListAppart(Long idLocality) {
        DatabaseReference mDataBaseRef = FirebaseDatabase.getInstance().getReference();

        Query query = mDataBaseRef.child("appart").orderByChild("idLocality").equalTo(idLocality);

            Toast.makeText(SearchApart.this,"Recherche en cours pour " + locality, Toast.LENGTH_LONG).show();
            query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String idApartment;
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot apart : dataSnapshot.getChildren()) {
                        idApartment = apart.getKey();
                        apparts.add(idApartment);
                        System.out.println("###############################################################################" + idApartment);
//                        ++ idApartment;
//                        apparts.add(new Appart((String) issue.child("type").getValue(), (long) issue.child("nbRooms").getValue(), (long) issue.child("price").getValue(), (String) issue.child("addressStreet").getValue(), (String) issue.child("userId").getValue()));

                        //String type, int nbRooms, int price, String adressStreet, String userId
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void ListLocalities() {

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
                        localities.add(rst);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}