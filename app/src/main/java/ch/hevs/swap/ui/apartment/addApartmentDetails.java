package ch.hevs.swap.ui.apartment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.hevs.swap.R;
import ch.hevs.swap.data.models.AppartController;

public class addApartmentDetails extends AppCompatActivity {

    //Variables
    private EditText designation;
    private EditText nbRooms;
    private EditText price;
    private EditText address;
    private Button addButton;
    private Button cancelButton;
    public EditText locality;

    List<String> localities;


    private AppartController appartController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apartment_details);
       initializeForm();
       appartController = new AppartController();


       localities = ListLocalities();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, localities);

        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.localityField);

        textView.setDropDownVerticalOffset(3);

        textView.setAdapter(adapter);

    }

    private void initializeForm() {
        designation = findViewById(R.id.designationField);
        nbRooms = findViewById(R.id.nbRoomsField);
        price = findViewById(R.id.priceField);
        address = findViewById(R.id.addressField);
        locality = findViewById(R.id.localityField);
        addButton = findViewById(R.id.addButton);
        cancelButton = findViewById(R.id.cancelButton);
        // créer méthode cancelButton
        addButton.setOnClickListener(View -> saveChanges(
                designation.getText().toString(),
                Integer.parseInt(nbRooms.getText().toString()),
                Integer.parseInt(price.getText().toString()),
                address.getText().toString(),
                locality.getText().toString()
        ));
    }

    private void saveChanges(String designation, int nbRooms, int price, String address, String locality){

        System.out.println("Désignation " + designation);
        System.out.println("nbRooms " + nbRooms);
        System.out.println("Price " + price);
        System.out.println("Address :" + address);
        System.out.println("localité : " + locality);
        Long localityID = new Long(localities.indexOf(locality));

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&& id est : "  + localityID);


        String key = appartController.insertNewAppart(designation, nbRooms, price, address, localityID);

        // changement d'activité
        Intent intent = new Intent(this, addApartmentImages.class);
        intent.putExtra("key", key );
        startActivity(intent);

    }




    private ArrayList<String> ListLocalities() {
        ArrayList<String> response = new ArrayList<String>();

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
                        response.add(rst);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return response;
    }


}
