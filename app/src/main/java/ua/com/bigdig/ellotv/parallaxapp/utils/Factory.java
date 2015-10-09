package ua.com.bigdig.ellotv.parallaxapp.utils;

/**
 * Created by MishaRJ on 09.10.2015.
 */
public class Factory {
    private static JsonHandler instance = null;
    public static JsonHandler getJsonHandler(){
        if (instance == null){
            instance = new JsonHandler();
        }
    return instance;
    }
}
