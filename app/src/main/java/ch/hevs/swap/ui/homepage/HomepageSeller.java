package ch.hevs.swap.ui.homepage;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;

import ch.hevs.swap.R;
import ch.hevs.swap.ui.apartment.addApartmentDetails;
import ch.hevs.swap.ui.search.Buyer_Appart;


public class HomepageSeller extends BaseActivity {


    ListView listView;

    private Button addAppartement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_homepage);

        listView=(ListView)findViewById(R.id.listview);

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Maison 1");
        arrayList.add("Maison 2");
        arrayList.add("Maison 3");
        arrayList.add("Maison 4");
        arrayList.add("Maison 5");
        arrayList.add("Maison 6");
        arrayList.add("Maison 7");
        arrayList.add("Maison 8");
        arrayList.add("Maison 9");
        arrayList.add("Maison 10");
        arrayList.add("Maison 11");
        arrayList.add("Maison 12");
        arrayList.add("Maison 13");
        arrayList.add("Maison 14");
        arrayList.add("Maison 15");
        arrayList.add("Maison 16");


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(arrayAdapter);








        final Switch sw = (Switch) findViewById(R.id.Switch);
        sw.setChecked(true);
        sw.setTextOn("Seller");
        sw.setTextOff("Buyer");


        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String statusSwitch1;
                if (sw.isChecked()) {
                    // The toggle is enabled
                    statusSwitch1 = sw.getTextOn().toString();
                    Intent homepageSeller = new Intent (HomepageSeller.this, HomepageSeller.class);
                    startActivity(homepageSeller);
                    finish();


                } else {
                    // The toggle is disabled
                    statusSwitch1 = sw.getTextOff().toString();
                    Intent homepageBuyer = new Intent (HomepageSeller.this, Buyer_Appart.class);
                    startActivity(homepageBuyer);
                    finish();
                }
            }
        });



            //Intialization Button

        addAppartement = (Button) findViewById(R.id.addAppartementFromSeller);

        addAppartement.setOnClickListener((View.OnClickListener) HomepageSeller.this);
            //Here MainActivity.this is a Current Class Reference (context)
        }


        public void onClick(View addApartmentDetails) {

            Intent intent = new Intent(HomepageSeller.this, addApartmentDetails.class);
        }


    }

