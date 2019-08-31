package ch.hevs.swap.ui.homepage;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ch.hevs.swap.R;
import ch.hevs.swap.ui.apartment.likedApartments;


public class HomepageBuyer extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_homepage);
    }

    @Override
    public void onClick(View v) {

    }

    public void onBackPressed() {
        this.startActivity(new Intent(this, HomepageBuyer.class));
    }
}