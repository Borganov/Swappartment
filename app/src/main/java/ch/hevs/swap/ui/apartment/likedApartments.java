package ch.hevs.swap.ui.apartment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ch.hevs.swap.R;
import ch.hevs.swap.data.models.UserController;

public class likedApartments extends AppCompatActivity {
    private ListView mListView;
    private ArrayList<String> apartLiked = new ArrayList<>();
    private UserController userController = new UserController();
    private String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
            "Sophie", "Tristan", "Ulric", "Vincent", "Willy", "Xavier",
            "Yann", "Zoé"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_apartments);
        mListView = (ListView) findViewById(R.id.listView);

        //android.R.layout.simple_list_item_1 est une vue disponible de base dans le SDK android,
        //Contenant une TextView avec comme identifiant "@android:id/text1"

        apartLiked = userController.apartLiked();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(likedApartments.this,
                android.R.layout.simple_list_item_1, apartLiked);
        mListView.setAdapter(adapter);
    }
}
