package music_38.framgia.com.musicup.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Genre implements Parcelable {

    private String mTitle;
    private int mImage;
    private String mType;
    private List<Track> mTracks;

    public Genre() {
    }

    public Genre(String title, int image, String type) {
        mTitle = title;
        mImage = image;
        mType = type;
    }

    public Genre(List<Track> tracks) {
        mTracks = tracks;
    }

    public Genre(String title, List<Track> tracks, String type) {
        mTitle = title;
        mTracks = tracks;
        mType = type;
    }

    public Genre(String title, int image, List<Track> tracks) {
        mTitle = title;
        mImage = image;
        mTracks = tracks;
    }

    public Genre(Parcel in) {
        mTitle = in.readString();
        mImage = in.readInt();
        mTracks = in.createTypedArrayList(Track.CREATOR);
        mType = in.readString();
    }

    public Genre(GenreBuilder genreBuilder) {
        mTitle = genreBuilder.mTitle;
        mImage = genreBuilder.mImage;
        mTracks = genreBuilder.mTracks;
        mType = genreBuilder.mType;
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeInt(mImage);
        parcel.writeTypedList(mTracks);
        parcel.writeString(mType);
    }

    public static class GenreBuilder {
        private String mTitle;
        private int mImage;
        private List<Track> mTracks;
        private String mType;

        public GenreBuilder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public GenreBuilder setImage(int image) {
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

        public Genre build() {
            return new Genre(this);
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    public void setTracks(List<Track> tracks) {
        mTracks = tracks;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "mTitle='" + mTitle + '\'' +
                ", mImage=" + mImage +
                ", mType='" + mType + '\'' +
                ", mTracks=" + mTracks +
                '}';
    }
}


