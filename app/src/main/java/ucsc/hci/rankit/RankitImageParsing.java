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
public class RankitImageParsing {


    public JsonReader reader;

    public JSONObject jsonResponse;

    public List<ImageDataBox> datalist = new ArrayList<ImageDataBox>();





    public List<ImageDataBox> ParseJson(InputStream instream) throws IOException, UnsupportedEncodingException, JSONException {
        ImageDataBox imageData = new ImageDataBox();

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
            //imageData = ParseJsonInternal(reader);
            datalist = ParseJsonFunction(jsonResponse);



            Log.d("ParsebaseNames",datalist.get(datalist.size()-2).name);



        } catch (NullPointerException e) {
        }

        return datalist;

    }

    public List<ImageDataBox> ParseJsonFunction(JSONObject jsonResponse) throws IOException, JSONException {




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
                ImageDataBox imageData = new ImageDataBox();

                try {
                    imageData.name = jsonChildNode.optString("name").toString();
                   // Log.d("Json Parsing", imageData.name);


                } catch (Exception e){
                    imageData.name = "";

                }

                try {
                    imageData.image_file = jsonChildNode.optString("image_file").toString();
                    //Log.d("Json Parsing", imageData.cover_art);


                } catch (Exception e){
                    imageData.image_file = "";

                }

                try {
                    imageData.url = jsonChildNode.optString("url").toString();


                } catch (Exception e){
                    imageData.url = "";

                }

                try {
                    imageData.description = jsonChildNode.optString("description").toString();


                } catch (Exception e){
                    imageData.description = "";

                }



                try {
                    imageData.item_id = Integer.parseInt(jsonChildNode.optString("id").toString());


                } catch (Exception e){
                    imageData.item_id = 0;

                }



                try {
                    imageData.creator = jsonChildNode.optString("creator").toString();


                } catch (Exception e){
                    imageData.creator = "";

                }



                try {
                    imageData.ratings = jsonChildNode.optString("ratings").toString();



                } catch (Exception e){
                    imageData.ratings = "";

                }


                try {
                    imageData.creator_website = jsonChildNode.optString("creator_website").toString();


                } catch (Exception e){
                    imageData.creator_website = "";

                }


                try {
                    imageData.category = jsonChildNode.optString("category").toString();



                } catch (Exception e){
                    imageData.category = "";

                }


                try {
                    imageData.date_created = jsonChildNode.optString("date_created").toString();


                } catch (Exception e){
                    imageData.date_created = "";

                }

                Log.d("ParsetimeNames",imageData.name);



                datalist.add(imageData);




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


        Log.d("Json Parsing", datalist.get(datalist.size() - 1).name);


        return datalist;


    }



    /*
    public ImageDataBox ParseJson(InputStream instream) throws IOException, UnsupportedEncodingException, JSONException {
        ImageDataBox imageData = new ImageDataBox();

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
            //imageData = ParseJsonInternal(reader);
            imageData = ParseJsonFunction(jsonResponse);

        } catch (NullPointerException e) {
        }

        return imageData;

    }

    public ImageDataBox ParseJsonFunction(JSONObject jsonResponse) throws IOException, JSONException {



        ImageDataBox imageData = new ImageDataBox();




        Log.d("Json Parsing", "Inside ParseJsonInternal function");


        try {


            JSONArray jsonMainNode = jsonResponse.optJSONArray("data");


            int lengthJsonArr = jsonMainNode.length();

            for (int i = 0; i < lengthJsonArr; i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

            try {
                imageData.name = jsonChildNode.optString("name").toString();


            } catch (Exception e){
                imageData.name = "";

            }

                try {
                    imageData.cover_art = jsonChildNode.optString("cover_art").toString();


                } catch (Exception e){
                    imageData.cover_art = "";

                }

                try {
                    imageData.doi = jsonChildNode.optString("doi").toString();


                } catch (Exception e){
                    imageData.doi = "";

                }

                try {
                    imageData.description = jsonChildNode.optString("description").toString();


                } catch (Exception e){
                    imageData.description = "";

                }



                try {
                    imageData.item_id = Integer.parseInt(jsonChildNode.optString("id").toString());


                } catch (Exception e){
                    imageData.item_id = 0;

                }



                try {
                    imageData.director = Integer.parseInt(jsonChildNode.optString("director").toString());


                } catch (Exception e){
                    imageData.director = 0;

                }



                try {
                    imageData.lead_actor_female = Integer.parseInt(jsonChildNode.optString("lead_actor_female").toString());


                } catch (Exception e){
                    imageData.lead_actor_female = 0;

                }


                try {
                    imageData.lead_actor_male = Integer.parseInt(jsonChildNode.optString("lead_actor_male").toString());


                } catch (Exception e){
                    imageData.lead_actor_male = 0;

                }


                try {
                    imageData.genre = Integer.parseInt(jsonChildNode.optString("genre").toString());


                } catch (Exception e){
                    imageData.genre = 0;

                }


                try {
                    imageData.imdb_url = jsonChildNode.optString("imdb_url").toString();


                } catch (Exception e){
                    imageData.imdb_url = "";

                }

                try {
                    imageData.wikipedia_url = jsonChildNode.optString("wikipedia_url").toString();


                } catch (Exception e){
                    imageData.wikipedia_url = "";

                }




            }
        } catch (JSONException e) {

            e.printStackTrace();
        }




        return imageData;


    }

    */



}
