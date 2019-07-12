package ch.hevs.swap.ui.apartment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ch.hevs.swap.R;

public class SellerApartmentDetails extends AppCompatActivity {

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

        String url = "https://firebasestorage.googleapis.com/v0/b/swap-appartements.appspot.com/o/apartment%2Fimages%2F1%2Fc901f9e9-3167-4b62-a47f-2d36a404c013?alt=media&token=a33c560f-512b-4e33-82fe-5498a9f2dc9c";
        System.out.println("--------------------"+url);
       // String url = "https://www.presse-citron.net/wordpress_prod/wp-content/uploads/2018/11/meilleure-banque-image.jpg";

        Picasso.with(this).load(url).into(imageView);


        btnGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageById();
            }
        });
    }

    private void getImageById() {


        System.out.println(storageReference.child("apartment/images/1/").getDownloadUrl());



    }


}
