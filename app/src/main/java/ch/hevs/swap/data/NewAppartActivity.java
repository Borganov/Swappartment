package ch.hevs.swap.data;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ch.hevs.swap.R;

public class NewAppartActivity extends AppCompatActivity {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]


    private EditText mDesignation;
    private EditText mNbRooms;
    private EditText mPrice;
    private EditText mAddress;

    private Button mInsertAppart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_appart);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mDesignation = findViewById(R.id.designation);
        mNbRooms = findViewById(R.id.nbRooms);
        mPrice = findViewById(R.id.price);
        mAddress = findViewById(R.id.address);

        mInsertAppart = findViewById(R.id.insertAppart);

        mInsertAppart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertAppart();
            }
        });
    }

    private void insertAppart() {

        final String designation = mDesignation.getText().toString();
        final int nbRooms = Integer.parseInt(mNbRooms.getText().toString());
        final double price = Double.parseDouble(mPrice.getText().toString());
        final String address = mAddress.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(designation)) {
            mDesignation.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        mDatabase.child("appart").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        insertNewAppart(designation, nbRooms, price, address);

                        // Finish this Activity, back to the stream

                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    // [START write_fan_out]
    private void insertNewAppart(String designation, int nbRooms, double price, String address) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        Appart appart = new Appart(designation, nbRooms, price, address);
        //mDatabase.child("appart").push();
        String key = mDatabase.child("appart").push().getKey();

        mDatabase.child("/appart/" + key).setValue(appart);

        //String key = mDatabase.child("appart").push().getKey();
        //Map<String, Object> postValues = appart.toMap();

        //Map<String, Object> childUpdates = new HashMap<>();
        //childUpdates.put("/appart/" + key, appart);

        //childUpdates.put("/posts/" + key, postValues);
        //childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        //mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]
}
