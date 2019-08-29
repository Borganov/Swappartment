package ch.hevs.swap.ui.apartment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import ch.hevs.swap.R;
import ch.hevs.swap.data.models.AppartController;

public class addApartmentDetails extends AppCompatActivity {

    //Variables
    private EditText designation;
    private EditText nbRooms;
    private EditText price;
    private EditText address;
    private Button addButton;
    private Button cancelButton;
    public EditText locality;

    private AppartController appartController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apartment_details);
       initializeForm();
       appartController = new AppartController();
    }

    private void initializeForm() {
        designation = findViewById(R.id.designationField);
        nbRooms = findViewById(R.id.nbRoomsField);
        price = findViewById(R.id.priceField);
        address = findViewById(R.id.addressField);
        locality = findViewById(R.id.localityField);
        addButton = findViewById(R.id.addButton);
        cancelButton = findViewById(R.id.cancelButton);
        // créer méthode cancelButton
        addButton.setOnClickListener(View -> saveChanges(
                designation.getText().toString(),
                Integer.parseInt(nbRooms.getText().toString()),
                Integer.parseInt(price.getText().toString()),
                address.getText().toString(),
                locality.getText().toString()
        ));
    }

    private void saveChanges(String designation, int nbRooms, int price, String locality, String address){

        System.out.println(designation);
        System.out.println(nbRooms);
        System.out.println(price);
        System.out.println(address);
        System.out.println(locality);


        String key = appartController.insertNewAppart(designation, nbRooms, price, address);

        // changement d'activité
        Intent intent = new Intent(this, addApartmentImages.class);
        intent.putExtra("key", key );
        startActivity(intent);

    }
}
