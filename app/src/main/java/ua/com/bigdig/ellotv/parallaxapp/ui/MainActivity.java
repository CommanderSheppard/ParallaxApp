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
import ua.com.bigdig.ellotv.parallaxapp.utils.Factory;


@SuppressWarnings("unchecked")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Factory.getJsonHandler().isDataLoaded()) {
           /* System.out.println("dataLoaded false" + '\n' + '\n');
            System.out.println(" __________________________________ NOW ITS LOADED FROM NET VIEW _________________________________________________________________________________________________"+'\n'+'\n');*/
            Object o = null;
            try {
                o = Factory.getJsonHandler().execute().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            createView((ArtistEntity[]) o);
            Factory.getJsonHandler().setDataLoaded(true);

        } else {
           /* System.out.println("dataLoaded true" + '\n' + '\n');
            System.out.println(" __________________________________ NOW ITS LOADED FROM CACHE VIEW _________________________________________________________________________________________________"+'\n'+'\n');*/
            createView(Factory.getJsonHandler().getArtistArray());
        }
    }


    private void createView(ArtistEntity[] artistsArray) {
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView1);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        // if (artistsArray == null) {artistsArray = new ArtistEntity[]{};} uncomment this if it possible to get null from artistsArray; should be checked;
        for (int i = 0; i < artistsArray.length; i++) {

            ImageView artistView = new ImageView(this);
            FrameLayout frameLayout = new FrameLayout(this);

            TextView artistInfo = new TextView(this);
            artistInfo.setBackgroundColor(Color.parseColor("#99000000"));
            artistInfo.setGravity(Gravity.CENTER | Gravity.BOTTOM);
            artistInfo.setTextColor(Color.WHITE);
            artistInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));

            int titleLength = artistsArray[i].getTitle().length();
            int artistsLength = artistsArray[i].getArtists().length();

            Typeface robotoBold = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");
            Typeface robotoLight = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
            Typeface robotoRegular = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");


            SpannableStringBuilder allText = new SpannableStringBuilder(artistsArray[i].getTitle() +
                    '\n' + artistsArray[i].getArtists() + '\n' + "Просмотров: " + artistsArray[i].getView_count() + '\n');
            allText.setSpan(new CustomTypefaceSpan("", robotoBold), 0, titleLength, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            allText.setSpan(new CustomTypefaceSpan("", robotoLight), titleLength, titleLength + artistsLength, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            allText.setSpan(new CustomTypefaceSpan("", robotoRegular), titleLength + artistsLength, allText.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            artistInfo.setText(allText);

            Picasso.with(this).load(artistsArray[i].getPictureLink()).into(artistView);
            Picasso.with(this).setIndicatorsEnabled(true);

            frameLayout.addView(artistView);
            frameLayout.addView(artistInfo);

            artistView.setAdjustViewBounds(true);
            linearLayout.addView(frameLayout);
        }
        scrollView.addView(linearLayout);

    }
}
