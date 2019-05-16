package ceti.co.copyproj1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_READ_EXTERNAL_STORAGE = 1000;

    MyPagerAdapter adapter;
    ViewPager viewPager;
    boolean fileReadPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);


        //permission 체크
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            fileReadPermission = true;
        }

        //permission 부여 안될 경우 permission 요청
        if (!fileReadPermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
        }

        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // 사진 경로 Uri 가지고 오기 ②
                String uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                fragments.add(PhotoFragment.newInstance(uri));
            }
            cursor.close(); // ③
        }

        // 어댑터
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.updateFragments(fragments);
        viewPager.setAdapter(adapter);

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() < adapter.getCount() -1 ) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });

            }
        };

        Timer timer = new Timer();
        timer.schedule(tt, 0, 3000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                fileReadPermission = true;
        }
    }
}

