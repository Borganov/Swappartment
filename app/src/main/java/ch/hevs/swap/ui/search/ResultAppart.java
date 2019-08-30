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

    ImageView imgAppart;
    TextView txtAppartId;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // manual creation of queue of appartment id //

        Knot k1 = new Knot(new Info(1));
        Knot k2 = new Knot(new Info(2));
        Knot k3 = new Knot(new Info(3));
        Knot k4 = new Knot(new Info(4));

        Queue q1 = new Queue();

        q1.file(k1);
        q1.file(k2);
        q1.file(k3);
        q1.file(k4);

        // end of manual creation

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_appart);

        imgAppart = findViewById(R.id.imgAppart);
        txtAppartId = findViewById(R.id.txtAppartId);
//        mSearchField = findViewById(R.id.searchField);

        txtAppartId.setText(Integer.toString(q1.getFirst().getInfo().getValeur()));

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

                q1.defile();
                if(!q1.isEmpty())
                {
                    txtAppartId.setText(Integer.toString(q1.getFirst().getInfo().getValeur()));
                    // ADD CODE TO SAVE HOUSE TO FAVORITES
                }

                else
                    txtAppartId.setText("You've swiped through all houses!");

                imgAppart.setImageResource(R.drawable.home2);
                Toast.makeText(ResultAppart.this, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                q1.defile();
                if(!q1.isEmpty())
                    txtAppartId.setText(Integer.toString(q1.getFirst().getInfo().getValeur()));
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

    public void openDialog() {
        AppartDetails appartDetails = new AppartDetails("test"); //parameter of AppartDetails = description of appartment
        appartDetails.show(getSupportFragmentManager(), "Appartment Details");
    }
}
