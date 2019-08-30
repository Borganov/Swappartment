package ch.hevs.swap.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import ch.hevs.swap.ui.apartment.OnSwipeTouchListener;
import ch.hevs.swap.ui.apartment.addApartmentImages;

public class ResultAppart extends AppCompatActivity {

    Queue appartQueue = new Queue();

    ImageView imgAppart;
    TextView txtAppartId;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_appart);

        //GET PARAMETERS
        ArrayList<String> appartKeys = getIntent().getStringArrayListExtra("key");

        appartQueue.fillQueue(appartKeys);

        imgAppart = findViewById(R.id.imgAppart);
        txtAppartId = findViewById(R.id.txtAppartId);

        updateFields();

        final int[] imageRes1 = {
                R.drawable.home1,
                R.drawable.home2
        };

        imgAppart.setOnTouchListener(new OnSwipeTouchListener(ResultAppart.this) {

            public void onTap() {

                if(index+1 == imageRes1.length)
                    index = 0;
                else
                    index++;

                imgAppart.setImageResource(imageRes1[index]);

            }
            public void onSwipeTop() {
                Toast.makeText(ResultAppart.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {

                appartQueue.defile();
                if(!appartQueue.isEmpty())
                {
                    updateFields();
                    // ADD CODE TO SAVE HOUSE TO FAVORITES
                }


                else
                    txtAppartId.setText("You've swiped through all houses!");

                imgAppart.setImageResource(R.drawable.home2);
                Toast.makeText(ResultAppart.this, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                appartQueue.defile();
                if(!appartQueue.isEmpty())
                    updateFields();
                else
                    txtAppartId.setText("You've swiped through all houses!");

                imgAppart.setImageResource(R.drawable.home1);
                Toast.makeText(ResultAppart.this, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                Toast.makeText(ResultAppart.this, "bottom", Toast.LENGTH_SHORT).show();

                openDialog();
            }

        });



    };

    public void updateFields() {
        // get key of appartment //
        FirebaseDatabase mDatabase;
        DatabaseReference mDataBaseRef = FirebaseDatabase.getInstance().getReference("appart/");
        mDatabase = FirebaseDatabase.getInstance();
        String appartKey = appartQueue.getFirst().getInfo().getValeur();
        Query query = mDataBaseRef.child(appartKey); //appart key, first element in queue

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String type;
                    // dataSnapshot is the "issue" node with all children with id 0
                    txtAppartId.setText(dataSnapshot.child("type").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void openDialog() {
        AppartDetails appartDetails = new AppartDetails("test"); //parameter of AppartDetails = description of appartment
        appartDetails.show(getSupportFragmentManager(), "Appartment Details");
    }
}
