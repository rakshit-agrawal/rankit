package ucsc.hci.rankit;

import java.util.List;

/**
 * Created by rakshit on 3/9/15.
 */
public interface PerformJsonOperations {
    // onPostExecute displays the results of the AsyncTask.
    void onPostExecute(List<MovieDataBox> result);
}
