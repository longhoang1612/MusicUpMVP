package music_38.framgia.com.musicup.screen.base;

public interface BasePresenter<T> {
    void setView(T view);

    void onStart();

    void onStop();
}
