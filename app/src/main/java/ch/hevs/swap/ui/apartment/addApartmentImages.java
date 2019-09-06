package ch.hevs.swap.ui.apartment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import ch.hevs.swap.R;
import ch.hevs.swap.data.models.AppartController;
import ch.hevs.swap.ui.homepage.BaseActivity;
import ch.hevs.swap.ui.homepage.HomepageSeller;


public class addApartmentImages extends BaseActivity {

    private static final int SWIPE_MIN_DISTANCE = 10;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private int index;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;


    //VARIABLES
    private Button  btnAdd, btnDone;
    private Uri filepath;
    private String apartmentKey;
    private ImageView imageView;
    private ArrayList<String> appartPics;

    private AppartController appartController;

    private final int PICK_IMAGE_REQUEST = 71;

    //FIREBASE
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apartment_images);

        //Firebase init
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //Init view
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnDone = (Button)findViewById(R.id.btnDone);
        imageView= findViewById(R.id.imageView);


        appartPics = new ArrayList<String>();
        appartController = new AppartController();
        index = 0;

        //get paramas
        Bundle b = getIntent().getExtras();
        apartmentKey = b.getString("key");


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });


        //Liste des photos pour l'appartement créé
        FirebaseDatabase mDatabase;
        DatabaseReference mDataBaseRef = FirebaseDatabase.getInstance().getReference("appart/"+apartmentKey);
        mDatabase = FirebaseDatabase.getInstance();

        Query query = mDataBaseRef.child("imgs");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String url;
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        url = (String) issue.getValue();
                        appartPics.add(url);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        /* ############################################ */
        /* ############################################ */

        final int[] imageRes1 = {
                R.drawable.home1,
                R.drawable.home2
        };

        final int[] imageRes2 = {
                R.drawable.appart1,
                R.drawable.appart2
        };

        imageView.setOnTouchListener(new OnSwipeTouchListener(addApartmentImages.this) {

            public void onTap() {


                if(index+1 == appartPics.size())
                    index = 0;
                else
                    index++;

                setImageViewById(apartmentKey,appartPics.get(index));
                //imageView.setImageResource(imageRes1[index]);

            }
        });
    }


    //Ajout de l'image à l'appartement
    private void uploadImage() {

        String uidNew = UUID.randomUUID().toString();

        if(filepath != null){
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            appartPics.add(uidNew);

            StorageReference ref = storageReference.child("apartment/images/" + apartmentKey+"/" + uidNew);
            ref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(addApartmentImages.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    appartController.addImageLinkToAppart(apartmentKey, uidNew);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(addApartmentImages.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Téléchargement : "+(int)progress+"%");
                }
            });

        }
    }

    // Choix de l'image
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choisir une image"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null ){
            filepath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imageView.setImageBitmap(bitmap);
                uploadImage();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    //Affichage de l'image
    private void setImageViewById(String id_appartment,String id_image) {

        storageReference.child("apartment/images/"+id_appartment+"/"+id_image).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, imageView.getWidth(),
                        imageView.getHeight(), false));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                System.out.println(exception.getMessage());
            }
        });

    }

    //Bouton terminé
    public void done() {
        Intent intent = new Intent(this, HomepageSeller.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        this.startActivity(new Intent(this,HomepageSeller.class));
    }

}
