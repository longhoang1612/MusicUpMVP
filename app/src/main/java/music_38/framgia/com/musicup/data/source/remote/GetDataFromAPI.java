package music_38.framgia.com.musicup.data.source.remote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import music_38.framgia.com.musicup.data.model.Track;
import music_38.framgia.com.musicup.data.model.TypeTrack;
import music_38.framgia.com.musicup.utils.Constants;

class GetDataFromAPI {

    static String getJSONFromAPI(String urlString) throws IOException {
        HttpURLConnection httpURLConnection;
        URL url = new URL(urlString);
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(Constants.SoundCloud.METHOD_GET);
        httpURLConnection.setConnectTimeout(Constants.SoundCloud.CONNECTION_TIMEOUT);
        httpURLConnection.setReadTimeout(Constants.SoundCloud.READ_TIMEOUT);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.connect();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        bufferedReader.close();
        String jsonString = stringBuilder.toString();
        httpURLConnection.disconnect();
        return jsonString;
    }

    static ArrayList<Track> getTrackFromAPI(String jsonString) throws JSONException {
        ArrayList<Track> tracks = new ArrayList<>();
        JSONObject root = new JSONObject(jsonString);
        JSONArray collection = root.getJSONArray(Track.TrackJSON.COLLECTION);
        for (int i = 0; i < collection.length(); i++) {
            JSONObject jsonObject = collection.getJSONObject(i)
                    .getJSONObject(Track.TrackJSON.TRACK);
            Track track = new Track(jsonObject);
            track.setTypeTrack(TypeTrack.TYPE_ONLINE);
            tracks.add(track);
        }

        return tracks;
    }

    static ArrayList<Track> getTrackSearch(String jsonString) throws JSONException {
        ArrayList<Track> tracks = new ArrayList<>();
        JSONObject root = new JSONObject(jsonString);
        JSONArray collection = root.getJSONArray(Track.TrackJSON.COLLECTION);
        for (int i = 0; i < collection.length(); i++) {
            JSONObject jsonObject = collection.getJSONObject(i);
            Track track = new Track(jsonObject);
            track.setTypeTrack(TypeTrack.TYPE_ONLINE);
            tracks.add(track);
        }
        return tracks;
    }
}
