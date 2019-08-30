package ch.hevs.swap.ui.homepage;


import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import ch.hevs.swap.R;


public class HomepageBuyer extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_homepage);

        final Switch sw = (Switch) findViewById(R.id.Switch);
        sw.setChecked(false);
        sw.setTextOn("Seller");
        sw.setTextOff("Buyer");



        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String statusSwitch1;
                if (sw.isChecked()) {
                    // The toggle is enabled


                    statusSwitch1 = sw.getTextOn().toString();
                    Intent homepageSeller = new Intent (HomepageBuyer.this, HomepageSeller.class);
                    startActivity(homepageSeller);
                    finish();


                } else {
                    // The toggle is disabled

                    statusSwitch1 = sw.getTextOff().toString();
                    Intent homepageBuyer = new Intent (HomepageBuyer.this, HomepageBuyer.class);
                    startActivity(homepageBuyer);
                    finish();
                }
            }
        });

    }

}