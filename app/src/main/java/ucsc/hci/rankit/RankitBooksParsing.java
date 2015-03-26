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
public class RankitBooksParsing {


    public JsonReader reader;

    public JSONObject jsonResponse;

    public List<BooksDataBox> datalist = new ArrayList<BooksDataBox>();





    public List<BooksDataBox> ParseJson(InputStream instream) throws IOException, UnsupportedEncodingException, JSONException {
        BooksDataBox bookData = new BooksDataBox();

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
            //bookData = ParseJsonInternal(reader);
            datalist = ParseJsonFunction(jsonResponse);



            Log.d("ParsebaseNames",datalist.get(datalist.size()-2).name);



        } catch (NullPointerException e) {
        }

        return datalist;

    }

    public List<BooksDataBox> ParseJsonFunction(JSONObject jsonResponse) throws IOException, JSONException {




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
                BooksDataBox bookData = new BooksDataBox();

                try {
                    bookData.name = jsonChildNode.optString("name").toString();
                   // Log.d("Json Parsing", bookData.name);


                } catch (Exception e){
                    bookData.name = "";

                }

                try {
                    bookData.cover_art = jsonChildNode.optString("cover_art").toString();
                    //Log.d("Json Parsing", bookData.cover_art);


                } catch (Exception e){
                    bookData.cover_art = "";

                }

                try {
                    bookData.isbn = jsonChildNode.optString("doi").toString();


                } catch (Exception e){
                    bookData.isbn = "";

                }

                try {
                    bookData.description = jsonChildNode.optString("description").toString();


                } catch (Exception e){
                    bookData.description = "";

                }



                try {
                    bookData.item_id = Integer.parseInt(jsonChildNode.optString("id").toString());


                } catch (Exception e){
                    bookData.item_id = 0;

                }



                try {
                    bookData.author = jsonChildNode.optString("author").toString();


                } catch (Exception e){
                    bookData.author = "";

                }



                try {
                    bookData.publisher = jsonChildNode.optString("publisher").toString();



                } catch (Exception e){
                    bookData.publisher = "";

                }


                try {
                    bookData.publish_year = jsonChildNode.optString("publish_year").toString();


                } catch (Exception e){
                    bookData.publish_year = "";

                }


                try {
                    bookData.category = jsonChildNode.optString("category").toString();



                } catch (Exception e){
                    bookData.category = "";

                }


                try {
                    bookData.amazon_url = jsonChildNode.optString("amazon_url").toString();


                } catch (Exception e){
                    bookData.amazon_url = "";

                }

                try {
                    bookData.wikipedia_url = jsonChildNode.optString("wikipedia_url").toString();


                } catch (Exception e){
                    bookData.wikipedia_url = "";

                }

                Log.d("ParsetimeNames",bookData.name);



                datalist.add(bookData);




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
    public BooksDataBox ParseJson(InputStream instream) throws IOException, UnsupportedEncodingException, JSONException {
        BooksDataBox bookData = new BooksDataBox();

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
            //bookData = ParseJsonInternal(reader);
            bookData = ParseJsonFunction(jsonResponse);

        } catch (NullPointerException e) {
        }

        return bookData;

    }

    public BooksDataBox ParseJsonFunction(JSONObject jsonResponse) throws IOException, JSONException {



        BooksDataBox bookData = new BooksDataBox();




        Log.d("Json Parsing", "Inside ParseJsonInternal function");


        try {


            JSONArray jsonMainNode = jsonResponse.optJSONArray("data");


            int lengthJsonArr = jsonMainNode.length();

            for (int i = 0; i < lengthJsonArr; i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

            try {
                bookData.name = jsonChildNode.optString("name").toString();


            } catch (Exception e){
                bookData.name = "";

            }

                try {
                    bookData.cover_art = jsonChildNode.optString("cover_art").toString();


                } catch (Exception e){
                    bookData.cover_art = "";

                }

                try {
                    bookData.doi = jsonChildNode.optString("doi").toString();


                } catch (Exception e){
                    bookData.doi = "";

                }

                try {
                    bookData.description = jsonChildNode.optString("description").toString();


                } catch (Exception e){
                    bookData.description = "";

                }



                try {
                    bookData.item_id = Integer.parseInt(jsonChildNode.optString("id").toString());


                } catch (Exception e){
                    bookData.item_id = 0;

                }



                try {
                    bookData.director = Integer.parseInt(jsonChildNode.optString("director").toString());


                } catch (Exception e){
                    bookData.director = 0;

                }



                try {
                    bookData.lead_actor_female = Integer.parseInt(jsonChildNode.optString("lead_actor_female").toString());


                } catch (Exception e){
                    bookData.lead_actor_female = 0;

                }


                try {
                    bookData.lead_actor_male = Integer.parseInt(jsonChildNode.optString("lead_actor_male").toString());


                } catch (Exception e){
                    bookData.lead_actor_male = 0;

                }


                try {
                    bookData.genre = Integer.parseInt(jsonChildNode.optString("genre").toString());


                } catch (Exception e){
                    bookData.genre = 0;

                }


                try {
                    bookData.imdb_url = jsonChildNode.optString("imdb_url").toString();


                } catch (Exception e){
                    bookData.imdb_url = "";

                }

                try {
                    bookData.wikipedia_url = jsonChildNode.optString("wikipedia_url").toString();


                } catch (Exception e){
                    bookData.wikipedia_url = "";

                }




            }
        } catch (JSONException e) {

            e.printStackTrace();
        }




        return bookData;


    }

    */



}
