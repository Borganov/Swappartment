package ch.hevs.swap.ui.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import ch.hevs.swap.R;
import ch.hevs.swap.ui.search.SearchApart;

public abstract class BaseActivity extends AppCompatActivity {

    GlobalVariable gv;

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.app_bar_switch);
        item.setActionView(R.layout.switch_item);

        Switch mySwitch = item.getActionView().findViewById(R.id.switch_item);


        gv = (GlobalVariable) getApplication();
        mySwitch.setChecked(gv.isBuyer());

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String statusSwitch1;

                if (mySwitch.isChecked()) {
                    gv.setBuyer(true);
                    Intent homepageSeller = new Intent (BaseActivity.this, HomepageSeller.class);
                    startActivity(homepageSeller);
                    finish();




                } else {
                    // The toggle is disabled
                    gv.setBuyer(false);
                    Intent homepageBuyer = new Intent (BaseActivity.this, SearchApart.class);
                    startActivity(homepageBuyer);
                    finish();
                }
            }


        });
        return true;
    }

}









