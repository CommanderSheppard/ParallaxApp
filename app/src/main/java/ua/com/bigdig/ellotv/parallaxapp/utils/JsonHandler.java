package ua.com.bigdig.ellotv.parallaxapp.utils;

/**
 * Created by MishaRJ on 04.10.2015.
 */

import android.os.AsyncTask;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import ua.com.bigdig.ellotv.parallaxapp.model.ArtistEntity;


public class JsonHandler extends AsyncTask {

    private static boolean dataLoaded = false;

    private ArtistEntity[] artistEntities;

    private JSONArray getAndPrepareJSONfromServer() {
        URL link = null;
        BufferedReader br = null;
        StringBuilder jsonAnswer = new StringBuilder();
        JSONObject jsonObj;
        JSONArray jsonItemsArray = null;

        try {
            link = new URL("http://ellotv.bigdig.com.ua/api/home/video");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            br = new BufferedReader(new InputStreamReader(link.openStream(), "UTF-8"));
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
        try {
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                jsonAnswer.append(nextLine);
            }
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
        try {
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                jsonAnswer.append(nextLine);
            }
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(jsonAnswer.toString());
            jsonObj = (JSONObject) obj;
            JSONObject data = (JSONObject) jsonObj.get("data");
            jsonItemsArray = (JSONArray) data.get("items");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonItemsArray;
    }

    private ArtistEntity[] initAndValidate(JSONArray jsonArray) {

        artistEntities = new ArtistEntity[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); i++) {
            ArtistEntity iterationTempo = new ArtistEntity();

            JSONObject tempoObject = (JSONObject) (jsonArray.get(i));

            if (tempoObject.get("id") == null) {
                iterationTempo.setId(0);
            } else {
                iterationTempo.setId((long) tempoObject.get("id"));
            }
            if (tempoObject.get("slug") == null) {
                iterationTempo.setSlug("");
            } else {
                iterationTempo.setSlug((String) tempoObject.get("slug"));
            }
            if (tempoObject.get("title") == null) {
                iterationTempo.setTitle("");
            } else {
                iterationTempo.setTitle((String) tempoObject.get("title"));
            }
            if (tempoObject.get("picture") == null) {
                iterationTempo.setPictureLink("");
            } else {
                iterationTempo.setPictureLink((String) tempoObject.get("picture"));
            }
            if (tempoObject.get("source") == null) {
                iterationTempo.setSource(0);
            } else {
                iterationTempo.setSource((long) tempoObject.get("source"));
            }
            if (tempoObject.get("youtube_id") == null) {
                iterationTempo.setYoutube_id("");
            } else {
                iterationTempo.setYoutube_id((String) tempoObject.get("youtube_id"));
            }
            if (tempoObject.get("view_count") == null) {
                iterationTempo.setView_count(0);
            } else {
                iterationTempo.setView_count((long) tempoObject.get("view_count"));
            }
            if (tempoObject.get("like_count") == null) {
                iterationTempo.setLike_count(0);
            } else {
                iterationTempo.setLike_count((long) tempoObject.get("like_count"));
            }
            if (tempoObject.get("artists") == null) {
                iterationTempo.setArtists(new String[]{""});
            } else {
                JSONArray artistsArray = (JSONArray) tempoObject.get("artists");
                String[] artists = new String[artistsArray.size()];
                for (int j = 0; j < artistsArray.size(); j++) {
                    JSONObject localTempo = (JSONObject) artistsArray.get(j);
                    artists[j] = (String) localTempo.get("name");
                }
                iterationTempo.setArtists(artists);
            }
            artistEntities[i] = (iterationTempo);
            //  System.out.println(iterationTempo.toString());  //could be safely deleted
        }
        return artistEntities;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        //  System.out.println("Async is executed");
        return initAndValidate(getAndPrepareJSONfromServer());
    }

    public ArtistEntity[] getArtistArray(){
        return  artistEntities;
    }

    public boolean isDataLoaded() {
        return dataLoaded;
    }

    public void setDataLoaded(boolean dataLoaded) {
        this.dataLoaded = dataLoaded;
    }
}
