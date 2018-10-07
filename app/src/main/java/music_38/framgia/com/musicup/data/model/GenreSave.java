package music_38.framgia.com.musicup.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GenreSave extends RealmObject implements Parcelable {

    @PrimaryKey
    private String mTitle;
    private String mImage;
    private String mType;
    private RealmList<Track> mTracks;

    public GenreSave() {
    }

    public GenreSave(String title, String image, RealmList<Track> tracks) {
        mTitle = title;
        mImage = image;
        mTracks = tracks;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public RealmList<Track> getTracks() {
        return mTracks;
    }

    public void setTracks(RealmList<Track> tracks) {
        mTracks = tracks;
    }

    public GenreSave(Parcel in) {
        mTitle = in.readString();
        mImage = in.readString();
        mTracks = new RealmList<>();
        mTracks.addAll(in.createTypedArrayList(Track.CREATOR));
        mType = in.readString();
    }

    public GenreSave(GenreBuilder genreBuilder) {
        mTitle = genreBuilder.mTitle;
        mImage = genreBuilder.mImage;
        mTracks = (RealmList<Track>) genreBuilder.mTracks;
        mType = genreBuilder.mType;
    }

    public static final Creator<GenreSave> CREATOR = new Creator<GenreSave>() {
        @Override
        public GenreSave createFromParcel(Parcel in) {
            return new GenreSave(in);
        }

        @Override
        public GenreSave[] newArray(int size) {
            return new GenreSave[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mImage);
        parcel.writeTypedList(mTracks);
        parcel.writeString(mType);
    }

    public static class GenreBuilder {
        private String mTitle;
        private String mImage;
        private List<Track> mTracks;
        private String mType;

        public GenreBuilder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public GenreBuilder setImage(String image) {
            mImage = image;
            return this;
        }

        public GenreBuilder setTracks(List<Track> tracks) {
            mTracks = tracks;
            return this;
        }

        public GenreBuilder setType(String type) {
            mType = type;
            return this;
        }

        public GenreSave build() {
            return new GenreSave(this);
        }
    }
}


