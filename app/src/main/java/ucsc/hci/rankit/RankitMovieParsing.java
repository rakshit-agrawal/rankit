package ucsc.hci.rankit;

import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakshit on 3/8/15.
 */
public class RankitMovieParsing {


    public JsonReader reader;

    public JSONObject jsonResponse;

    public List<MovieDataBox> datalist = new ArrayList<MovieDataBox>();



    public List<MovieDataBox> ParseJson(InputStream instream) throws IOException, UnsupportedEncodingException, JSONException {
        MovieDataBox movieData = new MovieDataBox();

        Reader name_reader = null;
        name_reader = new InputStreamReader(instream, "UTF-8");


        BufferedReader r = new BufferedReader(new InputStreamReader(instream));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }

        //reader = new JsonReader(name_reader);
        jsonResponse = new JSONObject(total.toString());

        try {
            //movieData = ParseJsonInternal(reader);
            datalist = ParseJsonFunction(jsonResponse);

        } catch (NullPointerException e) {
        }

        return datalist;

    }

    public List<MovieDataBox> ParseJsonFunction(JSONObject jsonResponse) throws IOException, JSONException {



        MovieDataBox movieData = new MovieDataBox();




        Log.d("Json Parsing", "Inside ParseJsonInternal function");


        try {


            /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
            /*******  Returns null otherwise.  *******/
            JSONArray jsonMainNode = jsonResponse.optJSONArray("data");

            /*********** Process each JSON Node ************/

            int lengthJsonArr = jsonMainNode.length();

            for (int i = 0; i < lengthJsonArr; i++) {
                /****** Get Object for each JSON node.***********/
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                /******* Fetch node values **********/

                try {
                    movieData.name = jsonChildNode.optString("name").toString();
                   // Log.d("Json Parsing", movieData.name);


                } catch (Exception e){
                    movieData.name = "";

                }

                try {
                    movieData.cover_art = jsonChildNode.optString("cover_art").toString();
                    //Log.d("Json Parsing", movieData.cover_art);


                } catch (Exception e){
                    movieData.cover_art = "";

                }

                try {
                    movieData.doi = jsonChildNode.optString("doi").toString();


                } catch (Exception e){
                    movieData.doi = "";

                }

                try {
                    movieData.description = jsonChildNode.optString("description").toString();


                } catch (Exception e){
                    movieData.description = "";

                }



                try {
                    movieData.item_id = Integer.parseInt(jsonChildNode.optString("id").toString());


                } catch (Exception e){
                    movieData.item_id = 0;

                }



                try {
                    movieData.director = jsonChildNode.optString("director").toString();


                } catch (Exception e){
                    movieData.director = "";

                }



                try {
                    movieData.lead_actor_female = jsonChildNode.optString("lead_actor_female").toString();



                } catch (Exception e){
                    movieData.lead_actor_female = "";

                }


                try {
                    movieData.lead_actor_male = jsonChildNode.optString("lead_actor_male").toString();


                } catch (Exception e){
                    movieData.lead_actor_male = "";

                }


                try {
                    movieData.genre = jsonChildNode.optString("genre").toString();



                } catch (Exception e){
                    movieData.genre = "";

                }


                try {
                    movieData.imdb_url = jsonChildNode.optString("imdb_url").toString();


                } catch (Exception e){
                    movieData.imdb_url = "";

                }

                try {
                    movieData.wikipedia_url = jsonChildNode.optString("wikipedia_url").toString();


                } catch (Exception e){
                    movieData.wikipedia_url = "";

                }



                datalist.add(movieData);






            }
        } catch (JSONException e) {

            e.printStackTrace();
        }


        Log.d("Json Parsing", datalist.get(datalist.size() - 1).name);


        return datalist;


    }



    /*
    public MovieDataBox ParseJson(InputStream instream) throws IOException, UnsupportedEncodingException, JSONException {
        MovieDataBox movieData = new MovieDataBox();

        Reader name_reader = null;
        name_reader = new InputStreamReader(instream, "UTF-8");


        BufferedReader r = new BufferedReader(new InputStreamReader(instream));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }

        //reader = new JsonReader(name_reader);
        jsonResponse = new JSONObject(total.toString());

        try {
            //movieData = ParseJsonInternal(reader);
            movieData = ParseJsonFunction(jsonResponse);

        } catch (NullPointerException e) {
        }

        return movieData;

    }

    public MovieDataBox ParseJsonFunction(JSONObject jsonResponse) throws IOException, JSONException {



        MovieDataBox movieData = new MovieDataBox();




        Log.d("Json Parsing", "Inside ParseJsonInternal function");


        try {


            JSONArray jsonMainNode = jsonResponse.optJSONArray("data");


            int lengthJsonArr = jsonMainNode.length();

            for (int i = 0; i < lengthJsonArr; i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

            try {
                movieData.name = jsonChildNode.optString("name").toString();


            } catch (Exception e){
                movieData.name = "";

            }

                try {
                    movieData.cover_art = jsonChildNode.optString("cover_art").toString();


                } catch (Exception e){
                    movieData.cover_art = "";

                }

                try {
                    movieData.doi = jsonChildNode.optString("doi").toString();


                } catch (Exception e){
                    movieData.doi = "";

                }

                try {
                    movieData.description = jsonChildNode.optString("description").toString();


                } catch (Exception e){
                    movieData.description = "";

                }



                try {
                    movieData.item_id = Integer.parseInt(jsonChildNode.optString("id").toString());


                } catch (Exception e){
                    movieData.item_id = 0;

                }



                try {
                    movieData.director = Integer.parseInt(jsonChildNode.optString("director").toString());


                } catch (Exception e){
                    movieData.director = 0;

                }



                try {
                    movieData.lead_actor_female = Integer.parseInt(jsonChildNode.optString("lead_actor_female").toString());


                } catch (Exception e){
                    movieData.lead_actor_female = 0;

                }


                try {
                    movieData.lead_actor_male = Integer.parseInt(jsonChildNode.optString("lead_actor_male").toString());


                } catch (Exception e){
                    movieData.lead_actor_male = 0;

                }


                try {
                    movieData.genre = Integer.parseInt(jsonChildNode.optString("genre").toString());


                } catch (Exception e){
                    movieData.genre = 0;

                }


                try {
                    movieData.imdb_url = jsonChildNode.optString("imdb_url").toString();


                } catch (Exception e){
                    movieData.imdb_url = "";

                }

                try {
                    movieData.wikipedia_url = jsonChildNode.optString("wikipedia_url").toString();


                } catch (Exception e){
                    movieData.wikipedia_url = "";

                }




            }
        } catch (JSONException e) {

            e.printStackTrace();
        }




        return movieData;


    }

    */



}
