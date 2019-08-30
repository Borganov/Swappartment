package ch.hevs.swap.ui.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import ch.hevs.swap.R;

public abstract class BaseActivity extends AppCompatActivity {


    private Switch switchBuyerSeller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_homepage);



//        ToggleButton toggle = (ToggleButton) findViewById(R.id.Switch);
        switchBuyerSeller = (Switch) findViewById(R.id.Switch);
        switchBuyerSeller.setChecked(true);
        switchBuyerSeller.setTextOn("Buyer");
        switchBuyerSeller.setTextOff("Seller");


        switchBuyerSeller.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String statusSwitch1;
                if (switchBuyerSeller.isChecked()) {
                    // The toggle is enabled
                    statusSwitch1 = switchBuyerSeller.getTextOn().toString();
                    Intent homepageSeller = new Intent (BaseActivity.this, HomepageSeller.class);
                    startActivity(homepageSeller);
                    finish();




                } else {
                    // The toggle is disabled
                    statusSwitch1 = switchBuyerSeller.getTextOff().toString();
                    Intent homepageBuyer = new Intent (BaseActivity.this, HomepageBuyer.class);
                    startActivity(homepageBuyer);
                    finish();
                }
            }
        });



    }







}

