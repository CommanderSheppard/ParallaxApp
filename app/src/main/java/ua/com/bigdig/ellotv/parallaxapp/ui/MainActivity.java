package ua.com.bigdig.ellotv.parallaxapp.ui;

/**
 * Created by MishaRJ on 04.10.2015.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.concurrent.ExecutionException;

import ua.com.bigdig.ellotv.parallaxapp.R;
import ua.com.bigdig.ellotv.parallaxapp.model.ArtistEntity;
import ua.com.bigdig.ellotv.parallaxapp.utils.AsyncUploadImage;
import ua.com.bigdig.ellotv.parallaxapp.utils.JsonHandler;


public class MainActivity extends AppCompatActivity {
    private ScrollView scrollView;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Object o = null;
        try {
            o = new JsonHandler().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        scrollView = (ScrollView) findViewById(R.id.scrollView1);
        linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        ArtistEntity [] tempo = (ArtistEntity[]) o;
        for (int i = 0; i < tempo.length; i++) {
            ImageView tempoView = new ImageView(getApplicationContext());
            new AsyncUploadImage(tempoView).execute(tempo[i].getPictureLink());
            tempoView.setAdjustViewBounds(true);
            linearLayout.addView(tempoView);

        }
        scrollView.addView(linearLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
