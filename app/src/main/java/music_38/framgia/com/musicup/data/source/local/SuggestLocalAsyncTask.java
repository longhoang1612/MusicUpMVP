package music_38.framgia.com.musicup.data.source.local;

import android.os.AsyncTask;

import java.util.ArrayList;

import music_38.framgia.com.musicup.data.source.Callback;
import music_38.framgia.com.musicup.utils.Constants;

public class SuggestLocalAsyncTask extends AsyncTask<String, String, ArrayList<String>> {

    private Callback mCallBack;

    public SuggestLocalAsyncTask(Callback callBack) {
        mCallBack = callBack;
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        ArrayList<String> suggests = new ArrayList<>();
        String suggest1 = Constants.SuggestKey.SUGGEST_1;
        String suggest2 = Constants.SuggestKey.SUGGEST_2;
        String suggest3 = Constants.SuggestKey.SUGGEST_3;
        String suggest4 = Constants.SuggestKey.SUGGEST_4;
        String suggest5 = Constants.SuggestKey.SUGGEST_5;

        suggests.add(suggest1);
        suggests.add(suggest2);
        suggests.add(suggest3);
        suggests.add(suggest4);
        suggests.add(suggest5);

        return suggests;
    }

    @Override
    protected void onPostExecute(ArrayList<String> suggests) {
        super.onPostExecute(suggests);
        if (mCallBack == null) {
            return;
        }
        mCallBack.getDataSuccess(suggests);

    }
}
