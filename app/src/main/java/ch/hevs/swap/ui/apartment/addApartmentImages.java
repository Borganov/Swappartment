package ch.hevs.swap.ui.apartment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import ch.hevs.swap.R;
import ch.hevs.swap.data.models.AppartController;


public class addApartmentImages extends AppCompatActivity {

    private static final int SWIPE_MIN_DISTANCE = 10;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private int index = 0;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;


    //VARIABLES
    private Button btnChoose, btnUpload;


    private Uri filepath;
    private String apartmentKey;
    private ImageView imageView;

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
        btnChoose = (Button)findViewById(R.id.btnChoose);
        btnUpload = (Button)findViewById(R.id.btnUpload);
        imageView= findViewById(R.id.imageView);


        appartController = new AppartController();

        //get paramas
        Bundle b = getIntent().getExtras();
        apartmentKey = b.getString("key");


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


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

                if(index+1 == imageRes1.length)
                    index = 0;
                else
                    index++;

                imageView.setImageResource(imageRes1[index]);

            }
            public void onSwipeTop() {
                Toast.makeText(addApartmentImages.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                imageView.setImageResource(R.drawable.home2);
                Toast.makeText(addApartmentImages.this, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                imageView.setImageResource(R.drawable.home1);
                Toast.makeText(addApartmentImages.this, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                Toast.makeText(addApartmentImages.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });



    }



    private void uploadImage() {

        String uidNew = UUID.randomUUID().toString();

        if(filepath != null){
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

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
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                }
            });

        }



    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null ){
            filepath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imageView.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }



    }
}
