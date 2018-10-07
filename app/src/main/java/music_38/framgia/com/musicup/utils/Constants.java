package music_38.framgia.com.musicup.utils;

import music_38.framgia.com.musicup.R;

public class Constants {

    public static final String URI_IMAGE_PLAYLIST
            = "android.resource://music_38.framgia.com.musicup/drawable/image_top_country";
    public static final String URI_IMAGE
            = "android.resource://music_38.framgia.com.musicup/drawable/image_default";

    public class Genre {
        public static final String ALL_MUSIC = "All Music";
        public static final String ALL_AUDIO = "All Audio";
        public static final String ALTERNATIVE_ROCK = "Alternative Rock";
        public static final String AMBIENT = "Ambient";
        public static final String CLASSICAL = "Classical";
        public static final String COUNTRY = "Country";
        public static final int IMAGE_ALL_MUSIC
                = R.drawable.image_all_music;
        public static final int IMAGE_ALL_AUDIO
                = R.drawable.image_all_audio;
        public static final int IMAGE_ALTERNATIVE_ROCK
                = R.drawable.image_top_rock;
        public static final int IMAGE_COUNTRY
                = R.drawable.image_top_country;
        public static final int IMAGE_AMBIENT
                = R.drawable.image_ambient;
        public static final int IMAGE_CLASSICAL
                = R.drawable.image_all_music;
    }

    public class SuggestKey {
        public static final String SUGGEST_1 = "Sơn Tùng";
        public static final String SUGGEST_2 = "Lạc trôi";
        public static final String SUGGEST_3 = "Người lạ ơi";
        public static final String SUGGEST_4 = "Sau tất cả";
        public static final String SUGGEST_5 = "Khu tao sống";
    }

    public class SoundCloud {
        public static final String BASE_URL = "https://api-v2.soundcloud.com/";
        public static final String PARAM_KIND = "charts?kind=top";
        public static final String PARAM_GENRE = "&genre=soundcloud";
        public static final String PARAM_CLIENT_ID = "&client_id=";
        public static final String PARAM_TYPE = "%3Agenres%3A";
        public static final String PARAM_LIMIT = "&limit=";
        public static final String LIMIT = "20";
        public static final String METHOD_GET = "GET";
        public static final String SEARCH = "search";
        public static final String QUERY_SEARCH = "/tracks?q=";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
    }

    public class Stream {
        public static final String STREAM_URL = "http://api.soundcloud.com/tracks/";
        public static final String STREAM = "/stream";
        public static final String STREAM_CLIENT_ID = "?client_id=";
    }
}
