package ch.hevs.swap.ui.apartment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import ch.hevs.swap.R;

public class addApartmentDetails extends AppCompatActivity {

    //Variables
    private EditText designation;
    private EditText nbRooms;
    private EditText price;
    private EditText address;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apartment_details);
       initializeForm();
    }

    private void initializeForm() {
        designation = findViewById(R.id.designationField);
        nbRooms = findViewById(R.id.nbRoomsField);
        price = findViewById(R.id.priceField);
        address = findViewById(R.id.addressField);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(View -> saveChanges(
                designation.getText().toString(),
                nbRooms.getText().toString(),
                price.getText().toString(),
                address.getText().toString()
        ));
    }

    private void saveChanges(String designation, String nbRooms, String price, String address){

        System.out.println(designation);
        System.out.println(nbRooms);
        System.out.println(price);
        System.out.println(address);

        // changement d'activité
        Intent intent = new Intent(this, addApartmentImages.class);
        startActivity(intent);

    }
}
