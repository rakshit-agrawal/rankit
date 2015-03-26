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
public class RankitMusicParsing {


    public JsonReader reader;

    public JSONObject jsonResponse;

    public List<MusicDataBox> datalist = new ArrayList<MusicDataBox>();





    public List<MusicDataBox> ParseJson(InputStream instream) throws IOException, UnsupportedEncodingException, JSONException {
        MusicDataBox musicData = new MusicDataBox();

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
            //musicData = ParseJsonInternal(reader);
            datalist = ParseJsonFunction(jsonResponse);



            Log.d("ParsebaseNames",datalist.get(datalist.size()-2).name);



        } catch (NullPointerException e) {
        }

        return datalist;

    }

    public List<MusicDataBox> ParseJsonFunction(JSONObject jsonResponse) throws IOException, JSONException {




        Log.d("Json Parsing", "Inside music parsing");


        try {


            /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
            /*******  Returns null otherwise.  *******/
            JSONArray jsonMainNode = jsonResponse.optJSONArray("data");

            /*********** Process each JSON Node ************/

            int lengthJsonArr = jsonMainNode.length();

            for (int i = 0; i < lengthJsonArr; i++) {
                /****** Get     Object for each JSON node.***********/
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                /******* Fetch node values **********/
                MusicDataBox musicData = new MusicDataBox();

                try {
                    musicData.name = jsonChildNode.optString("name").toString();
                    // Log.d("Json Parsing", musicData.name);


                } catch (Exception e){
                    musicData.name = "";

                }

                try {
                    musicData.album_art = jsonChildNode.optString("cover_art").toString();
                    //Log.d("Json Parsing", musicData.cover_art);


                } catch (Exception e){
                    musicData.album_art = "";

                }

                try {
                    musicData.publish_year = jsonChildNode.optString("publish_year").toString();


                } catch (Exception e){
                    musicData.publish_year = "";

                }




                try {
                    musicData.label = jsonChildNode.optString("label").toString();


                } catch (Exception e){
                    musicData.label = "";

                }

                try {
                    musicData.description = jsonChildNode.optString("description").toString();


                } catch (Exception e){
                    musicData.description = "";

                }



                try {
                    musicData.item_id = Integer.parseInt(jsonChildNode.optString("id").toString());


                } catch (Exception e){
                    musicData.item_id = 0;

                }



                try {
                    musicData.artist = jsonChildNode.optString("artist").toString();


                } catch (Exception e){
                    musicData.artist = "";

                }



                try {
                    musicData.composer = jsonChildNode.optString("composer").toString();



                } catch (Exception e){
                    musicData.composer = "";

                }


                try {
                    musicData.lyricist = jsonChildNode.optString("lyricist").toString();


                } catch (Exception e){
                    musicData.lyricist = "";

                }


                try {
                    musicData.genre = jsonChildNode.optString("genre").toString();



                } catch (Exception e){
                    musicData.genre = "";

                }


                try {
                    musicData.beats_url = jsonChildNode.optString("beats_url").toString();


                } catch (Exception e){
                    musicData.beats_url = "";

                }

                try {
                    musicData.wikipedia_url = jsonChildNode.optString("wikipedia_url").toString();


                } catch (Exception e){
                    musicData.wikipedia_url = "";

                }

                Log.d("ParsetimeNames",musicData.name);



                datalist.add(musicData);




                Log.d("ParsetimeNames",datalist.get(datalist.size()-1).name);

                for(int j = 0;j<datalist.size();j++){
                    Log.d("InsideLoopNames",datalist.get(j).name);

                }






            }
        } catch (JSONException e) {

            e.printStackTrace();
        }

        for(int i = 0;i<datalist.size();i++){
            Log.d("ParseLoopNames",datalist.get(i).name);

        }


       // Log.d("Json Parsing", datalist.get(datalist.size() - 1).name);


        return datalist;


    }



    /*
    public MusicDataBox ParseJson(InputStream instream) throws IOException, UnsupportedEncodingException, JSONException {
        MusicDataBox musicData = new MusicDataBox();

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
            //musicData = ParseJsonInternal(reader);
            musicData = ParseJsonFunction(jsonResponse);

        } catch (NullPointerException e) {
        }

        return musicData;

    }

    public MusicDataBox ParseJsonFunction(JSONObject jsonResponse) throws IOException, JSONException {



        MusicDataBox musicData = new MusicDataBox();




        Log.d("Json Parsing", "Inside ParseJsonInternal function");


        try {


            JSONArray jsonMainNode = jsonResponse.optJSONArray("data");


            int lengthJsonArr = jsonMainNode.length();

            for (int i = 0; i < lengthJsonArr; i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

            try {
                musicData.name = jsonChildNode.optString("name").toString();


            } catch (Exception e){
                musicData.name = "";

            }

                try {
                    musicData.cover_art = jsonChildNode.optString("cover_art").toString();


                } catch (Exception e){
                    musicData.cover_art = "";

                }

                try {
                    musicData.doi = jsonChildNode.optString("doi").toString();


                } catch (Exception e){
                    musicData.doi = "";

                }

                try {
                    musicData.description = jsonChildNode.optString("description").toString();


                } catch (Exception e){
                    musicData.description = "";

                }



                try {
                    musicData.item_id = Integer.parseInt(jsonChildNode.optString("id").toString());


                } catch (Exception e){
                    musicData.item_id = 0;

                }



                try {
                    musicData.director = Integer.parseInt(jsonChildNode.optString("director").toString());


                } catch (Exception e){
                    musicData.director = 0;

                }



                try {
                    musicData.lead_actor_female = Integer.parseInt(jsonChildNode.optString("lead_actor_female").toString());


                } catch (Exception e){
                    musicData.lead_actor_female = 0;

                }


                try {
                    musicData.lead_actor_male = Integer.parseInt(jsonChildNode.optString("lead_actor_male").toString());


                } catch (Exception e){
                    musicData.lead_actor_male = 0;

                }


                try {
                    musicData.genre = Integer.parseInt(jsonChildNode.optString("genre").toString());


                } catch (Exception e){
                    musicData.genre = 0;

                }


                try {
                    musicData.imdb_url = jsonChildNode.optString("imdb_url").toString();


                } catch (Exception e){
                    musicData.imdb_url = "";

                }

                try {
                    musicData.wikipedia_url = jsonChildNode.optString("wikipedia_url").toString();


                } catch (Exception e){
                    musicData.wikipedia_url = "";

                }




            }
        } catch (JSONException e) {

            e.printStackTrace();
        }




        return musicData;


    }

    */



}
