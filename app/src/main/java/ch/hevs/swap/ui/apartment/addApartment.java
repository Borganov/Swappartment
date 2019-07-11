package ch.hevs.swap.ui.apartment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import ch.hevs.swap.R;

public class addApartment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apartment);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imagesRef = storageRef.child('img')
    }
}
