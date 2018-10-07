package music_38.framgia.com.musicup.data.source.remote;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.source.Callback;

public class SearchTrackRemoteAsyncTask extends AsyncTask<String, Void, ArrayList<Track>> {
    private Callback mCallBack;
    private Exception mException;
    private String mSearchKey;

    SearchTrackRemoteAsyncTask(Callback callBack, String searchKey) {
        mCallBack = callBack;
        mSearchKey = searchKey;
    }

    @Override
    protected ArrayList<Track> doInBackground(String... strings) {
        ArrayList<Track> tracks = new ArrayList<>();
        try {
            String json = GetDataFromAPI.getJSONFromAPI(strings[0]);
            tracks = GetDataFromAPI.getTrackSearch(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            mException = e;
        }
        return tracks;
    }

    @Override
    protected void onPostExecute(ArrayList<Track> tracks) {
        super.onPostExecute(tracks);
        if (mCallBack == null) {
            return;
        }
        if (mException == null) {
            mCallBack.getDataSuccess(new Genre(tracks));
        } else {
            mCallBack.getDataFailure(mException);
        }
    }
}
