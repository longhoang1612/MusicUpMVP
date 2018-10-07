package music_38.framgia.com.musicup.data.model;

import android.support.annotation.StringDef;

import static music_38.framgia.com.musicup.data.model.TypeGenre.ALL_AUDIO;
import static music_38.framgia.com.musicup.data.model.TypeGenre.ALL_MUSIC;
import static music_38.framgia.com.musicup.data.model.TypeGenre.ALTERNATIVE_ROCK;
import static music_38.framgia.com.musicup.data.model.TypeGenre.AMBIENT;
import static music_38.framgia.com.musicup.data.model.TypeGenre.CLASSICAL;
import static music_38.framgia.com.musicup.data.model.TypeGenre.COUNTRY;

@StringDef({ALL_MUSIC, ALL_AUDIO, AMBIENT, COUNTRY, ALTERNATIVE_ROCK, CLASSICAL})
public @interface TypeGenre {
    String ALL_MUSIC = "all-music";
    String ALL_AUDIO = "all-audio";
    String AMBIENT = "ambient";
    String COUNTRY = "country";
    String ALTERNATIVE_ROCK = "alternative-rock";
    String CLASSICAL = "classical";
}
