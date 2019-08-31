package ch.hevs.swap.ui.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import ch.hevs.swap.R;
import ch.hevs.swap.data.models.Appart;
import ch.hevs.swap.data.models.UserController;
import ch.hevs.swap.ui.apartment.OnSwipeTouchListener;
import ch.hevs.swap.ui.apartment.addApartmentImages;

public class ResultAppart extends AppCompatActivity {

    //FIREBASE
    FirebaseStorage storage;
    StorageReference storageReference;

    Queue appartQueue = new Queue();

    ImageView imgAppart;
    TextView txtAppartId;

    ArrayList<String> appartPics;

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


        txtAppartId = findViewById(R.id.txtAppartId);


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
                    //imageView.setImageResource(imageRes1[index]);
                }



            }
            public void onSwipeTop() {

                Toast.makeText(ResultAppart.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                String test = appartQueue.getFirst().getInfo().getValeur();

                userController.addApartViewed(appartQueue.getFirst().getInfo().getValeur(),true);
                appartQueue.defile();
                if(!appartQueue.isEmpty())
                {
                    updateFields();
                    // ADD CODE TO SAVE HOUSE TO FAVORITES
                }

                else
                {
                    imgAppart.setImageResource(R.drawable.house);
                    imgAppart.setTag(R.drawable.house);
                    txtAppartId.setText("You've swiped through all houses!");
                }


                Toast.makeText(ResultAppart.this, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                userController.addApartViewed(appartQueue.getFirst().getInfo().getValeur(),false);
                appartQueue.defile();
                if(!appartQueue.isEmpty())
                    updateFields();
                else
                {
                    imgAppart.setImageResource(R.drawable.house);
                    imgAppart.setTag(R.drawable.house);
                    txtAppartId.setText("You've swiped through all houses!");
                }


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
                    txtAppartId.setText(dataSnapshot.child("designation").getValue().toString());
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
        System.out.println("#################################### :    " + appartKey);

        appartPics = new ArrayList<String>();

        queryImg.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String url;
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        url = (String) issue.getValue();
                        appartPics.add(url);
                        System.out.println("######################################     " + url);

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
        AppartDetails appartDetails = new AppartDetails("test"); //parameter of AppartDetails = description of appartment
        appartDetails.show(getSupportFragmentManager(), "Appartment Details");
    }

    //Affichage de l'image
    private void setImageViewById(String id_appartment,String id_image) {

        storageReference.child("apartment/images/"+id_appartment+"/"+id_image).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image
                System.out.println("Here I am ");
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgAppart.setImageBitmap(Bitmap.createScaledBitmap(bmp, imgAppart.getWidth(),
                        imgAppart.getHeight(), false));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                System.out.println("Here we are");
                System.out.println(exception.getMessage());
            }
        });

    }
}
