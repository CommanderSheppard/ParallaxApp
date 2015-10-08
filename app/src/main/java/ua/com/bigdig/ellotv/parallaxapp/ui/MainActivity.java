package ua.com.bigdig.ellotv.parallaxapp.ui;

/**
 * Created by MishaRJ on 04.10.2015.
 */

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

import ua.com.bigdig.ellotv.parallaxapp.R;
import ua.com.bigdig.ellotv.parallaxapp.model.ArtistEntity;
import ua.com.bigdig.ellotv.parallaxapp.utils.CustomTypefaceSpan;
import ua.com.bigdig.ellotv.parallaxapp.utils.JsonHandler;


@SuppressWarnings("unchecked")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Object o = null;
        try {
            o = new JsonHandler().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView1);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        ArtistEntity[] tempo = (ArtistEntity[]) o;
        // if (tempo == null) {tempo = new ArtistEntity[]{};} uncomment this if it possible to get null from tempo; should be checked;
        for (int i = 0; i < tempo.length; i++) {
            ImageView tempoView = new ImageView(this);
            FrameLayout tempoFrame = new FrameLayout(this);
            TextView someTextView = new TextView(this);
            someTextView.setBackgroundColor(Color.parseColor("#99000000"));
            someTextView.setGravity(Gravity.CENTER | Gravity.BOTTOM);
            int titleLength = tempo[i].getTitle().length();
            int artistsLength = tempo[i].getArtists().length();
//unused!   int countLength = String.valueOf(tempo[i].getView_count()).length();
            Typeface robotoBold = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");
            Typeface robotoLight = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
            Typeface robotoRegular = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
            someTextView.setTextColor(Color.WHITE);
            SpannableStringBuilder allText = new SpannableStringBuilder(tempo[i].getTitle() +
                    '\n' + tempo[i].getArtists() + '\n' + "Просмотров: " + tempo[i].getView_count() + '\n');
            allText.setSpan(new CustomTypefaceSpan("", robotoBold), 0, titleLength, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            allText.setSpan(new CustomTypefaceSpan("", robotoLight), titleLength, titleLength + artistsLength, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            allText.setSpan(new CustomTypefaceSpan("", robotoRegular), titleLength + artistsLength, allText.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            someTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
            someTextView.setText(allText);
            Picasso.with(this).load(tempo[i].getPictureLink()).into(tempoView);
            Picasso.with(this).setIndicatorsEnabled(true);
            tempoFrame.addView(tempoView);
            tempoFrame.addView(someTextView);
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
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
