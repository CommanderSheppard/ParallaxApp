package ua.com.bigdig.ellotv.parallaxapp.ui;

/**
 * Created by MishaRJ on 04.10.2015.
 */

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import ua.com.bigdig.ellotv.parallaxapp.R;
import ua.com.bigdig.ellotv.parallaxapp.model.ArtistEntity;
import ua.com.bigdig.ellotv.parallaxapp.utils.AsyncUploadImage;
import ua.com.bigdig.ellotv.parallaxapp.utils.JsonHandler;


public class MainActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private ScrollView scrollView;
    private int screenHeight;

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
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        screenHeight = display.getHeight();
        scrollView = (ScrollView) findViewById(R.id.scrollView1);
        linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        ArtistEntity[] tempo = (ArtistEntity[]) o;
        for (int i = 0; i < tempo.length; i++) {

            ImageView tempoView = new ImageView(getApplicationContext());
            FrameLayout tempoFrame = new FrameLayout(getApplicationContext());

            TextView videoTextView = new TextView(getApplicationContext());
            TextView artistTextView = new TextView(getApplicationContext());
            TextView countTextView = new TextView(getApplicationContext());

            countTextView.setBackgroundColor(Color.parseColor("#99000000"));

            videoTextView.setGravity(Gravity.CENTER | Gravity.TOP);
            artistTextView.setGravity(Gravity.CENTER | Gravity.TOP);
            countTextView.setGravity(Gravity.CENTER | Gravity.BOTTOM);
// /5 + /26 &&  /5 + /12 was good
            videoTextView.setPadding(0, screenHeight / 5 + screenHeight / 26, 0, 0);
            artistTextView.setPadding(0, screenHeight / 5 + screenHeight / 12, 0, 0);


            videoTextView.setText(tempo[i].getTitle());
            artistTextView.setText(tempo[i].getArtists());
            countTextView.setText("Просмотров: " + tempo[i].getView_count());

            videoTextView.setTextColor(Color.WHITE);
            artistTextView.setTextColor(Color.WHITE);
            countTextView.setTextColor(Color.WHITE);
            videoTextView.setTextSize(screenHeight/36);
            artistTextView.setTextSize(screenHeight/36);
            countTextView.setTextSize(screenHeight/36);

            videoTextView.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf"));
            artistTextView.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf"));
            countTextView.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf"));


            new AsyncUploadImage(tempoView).execute(tempo[i].getPictureLink());
            tempoFrame.addView(tempoView);
            tempoFrame.addView(countTextView);
            tempoFrame.addView(videoTextView);
            tempoFrame.addView(artistTextView);
            tempoView.setAdjustViewBounds(true);
            linearLayout.addView(tempoFrame);
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
