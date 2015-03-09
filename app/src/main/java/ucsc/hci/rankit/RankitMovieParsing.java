package ucsc.hci.rankit;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * Created by rakshit on 3/8/15.
 */
public class RankitMovieParsing {

    public JsonReader reader;

    public DataBox ParseJson(InputStream instream) throws IOException, UnsupportedEncodingException {
        DataBox movieData = new DataBox();

        Reader name_reader = null;
        name_reader = new InputStreamReader(instream, "UTF-8");

        reader = new JsonReader(name_reader);

        try {
            movieData = ParseJsonInternal(reader);

        } finally {
            reader.close();
        }

        return movieData;

    }

    public DataBox ParseJsonInternal(JsonReader reader) throws IOException {

        DataBox movieData = new DataBox();


        Log.d("Json Parsing", "Inside ParseJsonInternal function");

        reader.beginObject();

        while (reader.hasNext()) {
            //String name = reader.nextName();
            //Log.d("Json Parsing", "Inside first stage of Json object reading");

          //  try{
           //     String name = reader.nextName();

//            }finally {
  //              reader.beginObject();

    //        }



           // if (name.equals("data")) {
           //     Log.d("Json Parsing", "Inside Current Observation");
                //reader.beginArray();
                while (reader.hasNext()) {

                    Log.d("Json Parsing", "Inside While loop for reader.hasNext");
                    String l1_name = reader.nextName();
                    Log.d("Json Parsing", l1_name);

                    reader.beginArray();
                    while (reader.hasNext()) {

                        reader.beginObject();

                        while (reader.hasNext()) {
                            String l2_name = reader.nextName();


                            if (l2_name.equals("name")) {
                                movieData.name = reader.nextString();
                                Log.d("Json Parsing", "Testing right now.");
                            } else {
                                reader.skipValue();
                            }
                        }
                        reader.endObject();
                    }

                //}
                reader.endArray();
           // } else {
            //    reader.skipValue();
            }


        }
        reader.endObject();

        return movieData;
    }
}
