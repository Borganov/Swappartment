package ch.hevs.swap.ui.search;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ch.hevs.swap.R;

public class Buyer_Appart extends AppCompatActivity {

    private EditText mSearchField;

    private Button mBtnLaunchSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_appart);

        mSearchField = findViewById(R.id.searchField);
        mBtnLaunchSearch = findViewById(R.id.btnLaunchSearch);

        mBtnLaunchSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    public void search() {

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.root);
        ConstraintSet set = new ConstraintSet();

        String[] results = {
                "home1",
                "home2",
                "home3"
        };

        for(int i = 0; i<results.length; i++)
        {
            TextView view = new TextView(this);
            view.setId(View.generateViewId());
            view.setText(results[i]);
            layout.addView(view, i);
            set.clone(layout);
            set.connect(view.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP, 60+(i*70));
            set.applyTo(layout);
        }

    }
}
