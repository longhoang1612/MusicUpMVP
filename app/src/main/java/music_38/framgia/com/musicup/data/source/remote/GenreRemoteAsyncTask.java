package music_38.framgia.com.musicup.data.source.remote;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import music_38.framgia.com.musicup.data.model.Genre;
import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.source.Callback;


public class GenreRemoteAsyncTask extends AsyncTask<String, Void, ArrayList<Track>> {
    private Callback mCallBack;
    private Exception mException;
    private Genre mGenre;

    GenreRemoteAsyncTask(Callback callBack, Genre genre) {
        mCallBack = callBack;
        mGenre = genre;
    }

    @Override
    protected ArrayList<Track> doInBackground(String... strings) {
        ArrayList<Track> tracks = new ArrayList<>();
        try {
            String json = GetDataFromAPI.getJSONFromAPI(strings[0]);
            tracks = GetDataFromAPI.getTrackFromAPI(json);
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
            mGenre.setTracks(tracks);
            mCallBack.getDataSuccess(mGenre);
        } else {
            mCallBack.getDataFailure(mException);
        }
    }
}
