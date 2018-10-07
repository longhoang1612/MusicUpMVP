package music_38.framgia.com.musicup.screen.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;
import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.screen.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int DELAY = 500;
    private static final int PERMISSION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext()
                , Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext()
                , Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean boolean1 = ActivityCompat
                .shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean boolean2 = ActivityCompat
                .shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission1 + permission2 != PackageManager.PERMISSION_GRANTED) {
            if (boolean1 || boolean2) {
                ActivityCompat.requestPermissions(this
                        , new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(this
                        , new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, PERMISSION_REQUEST);
            }
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }, DELAY);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext()
                            , Manifest.permission.READ_EXTERNAL_STORAGE)
                            + ContextCompat.checkSelfPermission(getApplicationContext()
                            , Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent mIntent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(mIntent);
                            }
                        }, DELAY);
                    }
                } else {
                    Toasty.error(this, getResources().getString(R.string.msg_permission_denied)).show();
                    finish();
                }
                break;
        }
    }
}
