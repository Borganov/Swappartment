package ch.hevs.swap.ui.apartment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;

import ch.hevs.swap.R;
import ch.hevs.swap.ui.homepage.BaseActivity;
import ch.hevs.swap.ui.homepage.HomepageBuyer;
import ch.hevs.swap.ui.homepage.HomepageSeller;

public class SellerApartmentDetails extends BaseActivity {

    private Button btnGetImage;
    private ImageView imageView;
    private EditText editText;


    //FIREBASE
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_apartment_details);

        //Init view
        btnGetImage = (Button)findViewById(R.id.btn_get_img);
        editText = (EditText)findViewById(R.id.id_apartment);
        imageView= (ImageView)findViewById(R.id.imageView2);


        //Firebase init
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btnGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageById();
            }
        });
    }

    private void getImageById() {

        storageReference.child("apartment/images/1/c901f9e9-3167-4b62-a47f-2d36a404c013").getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
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

    public void onBackPressed() {
        this.startActivity(new Intent(this, HomepageSeller.class));
    }

}
