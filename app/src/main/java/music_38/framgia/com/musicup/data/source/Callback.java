package music_38.framgia.com.musicup.data.source;

public interface Callback<T> {
    void getDataSuccess(T data);

    void getDataFailure(Exception e);
}
