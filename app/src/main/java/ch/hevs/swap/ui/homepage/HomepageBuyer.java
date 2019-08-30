package ch.hevs.swap.ui.homepage;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import ch.hevs.swap.R;
import ch.hevs.swap.ui.apartment.likedApartments;


public class HomepageBuyer extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_homepage);

        final Switch sw = (Switch) findViewById(R.id.Switch);
        sw.setChecked(false);
        sw.setTextOn("Seller");
        sw.setTextOff("Buyer");

        Button mButton = findViewById(R.id.btnFavoris);

        mButton.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        Intent homepageLiked = new Intent (HomepageBuyer.this, likedApartments.class);
        startActivity(homepageLiked);
    }
}