package ch.hevs.swap.ui.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ch.hevs.swap.R;
import ch.hevs.swap.data.models.Appart;
import ch.hevs.swap.data.models.UserController;
import ch.hevs.swap.ui.apartment.OnSwipeTouchListener;
import ch.hevs.swap.ui.apartment.addApartmentImages;
import ch.hevs.swap.ui.homepage.BaseActivity;

public class ResultAppart extends BaseActivity {

    //Country currency
    Locale locale = new Locale("fr", "CH");

    //FIREBASE VARIABLES
    FirebaseStorage storage;
    StorageReference storageReference;

    //Each knot in the queue represents an apartment id
    Queue appartQueue = new Queue();

    //Elements in the layout
    ImageView imgAppart;
    TextView txtAppartDesignation;
    TextView txtAppartPrice;

    //ArrayList of all image ids
    ArrayList<String> appartPics;

    //Current index of appartPics ArrayList
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_appart);
        appartPics = new ArrayList<String>();

        //Firebase init
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //GET PARAMETERS
        ArrayList<String> appartKeys = getIntent().getStringArrayListExtra("key");
        UserController userController = new UserController();
        appartQueue.fillQueue(appartKeys);

        imgAppart = findViewById(R.id.imgAppart);
        imgAppart.setTag((Integer)999);


        txtAppartDesignation = findViewById(R.id.txtAppartDesignation);
        txtAppartPrice = findViewById(R.id.txtAppartPrice);

        updateFields();

        imgAppart.setOnTouchListener(new OnSwipeTouchListener(ResultAppart.this) {

            public void onTap() {

                if((Integer)imgAppart.getTag() != R.drawable.house) //if the image is the "empty" image, don't allow the tap
                {
                    if(index+1 == appartPics.size())
                        index = 0;
                    else
                        index++;

                    setImageViewById(appartQueue.getFirst().getInfo().getValeur(),appartPics.get(index));
                }



            }

            public void onSwipeTop() {

                //take current knot, defile it and add it to the end with file
                Knot end = appartQueue.getFirst();
                appartQueue.defile();
                appartQueue.file(end);
                updateFields();

            }

            public void onSwipeRight() {
                //save in database that the user is interested in the home
                userController.addApartViewed(appartQueue.getFirst().getInfo().getValeur(),true);

                appartQueue.defile();
                if(!appartQueue.isEmpty())
                    updateFields();

                else
                {
                    imgAppart.setImageResource(R.drawable.house);
                    imgAppart.setTag(R.drawable.house);
                    txtAppartDesignation.setText("Vous avez parcouru tous les biens!");
                    txtAppartPrice.setText("");
                }
            }

            public void onSwipeLeft() {
                //save in database that the user is not interested in the home
                userController.addApartViewed(appartQueue.getFirst().getInfo().getValeur(),false);
                appartQueue.defile();

                if(!appartQueue.isEmpty())
                    updateFields();

                else
                {
                    imgAppart.setImageResource(R.drawable.house);
                    imgAppart.setTag(R.drawable.house);
                    txtAppartDesignation.setText("Vous avez parcouru tous les biens!");
                    txtAppartPrice.setText("");
                }
            }

            public void onSwipeBottom() {
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
                    txtAppartDesignation.setText("Nom : " + dataSnapshot.child("designation").getValue().toString());

                    //SOURCE OF FORMATING : https://kodejava.org/how-do-i-format-a-number-as-currency-string/
                    NumberFormat format = NumberFormat.getCurrencyInstance(locale);
                    String currency = format.format(Double.parseDouble(dataSnapshot.child("price").getValue().toString()));

                    txtAppartPrice.setText("Prix : " + currency);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // UPDATE IMAGES
        mDataBaseRef = FirebaseDatabase.getInstance().getReference("appart/"+appartKey);
        mDatabase = FirebaseDatabase.getInstance();

        Query queryImg = mDataBaseRef.child("imgs");

        appartPics = new ArrayList<String>();

        queryImg.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String url;
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        url = (String) issue.getValue();
                        appartPics.add(url);
                     }

                    setImageViewById(appartQueue.getFirst().getInfo().getValeur(),appartPics.get(0));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void openDialog() {

        String appartKey = appartQueue.getFirst().getInfo().getValeur();

        FirebaseDatabase mDatabase;
        DatabaseReference mDataBaseRef = FirebaseDatabase.getInstance().getReference("appart/");
        mDatabase = FirebaseDatabase.getInstance();

        Query queryDesignation = mDataBaseRef.child(appartKey);

        queryDesignation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String information = "";

                    for (DataSnapshot apart : dataSnapshot.getChildren()) {
                        information = "Nom de l'appartement : " + dataSnapshot.child("designation").getValue().toString() + "\n" +
                                        "Adresse : " + dataSnapshot.child("addressStreet").getValue().toString() + "\n" +
                                        "Nombre de pièces : " + dataSnapshot.child("nbRooms").getValue().toString();
                    }

                    //open modal appartDetails with the information of the appartment
                    AppartDetails appartDetails = new AppartDetails(information); //parameter of AppartDetails = description of appartment
                    appartDetails.show(getSupportFragmentManager(), "Détail de l'appartement");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //show image
    private void setImageViewById(String id_appartment,String id_image) {

        storageReference.child("apartment/images/"+id_appartment+"/"+id_image).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                // Use the bytes to display the image
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgAppart.setImageBitmap(Bitmap.createScaledBitmap(bmp, imgAppart.getWidth(),
                        imgAppart.getHeight(), false));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }

    public void onBackPressed() {
        this.startActivity(new Intent(this, SearchApart.class));
    }
}
