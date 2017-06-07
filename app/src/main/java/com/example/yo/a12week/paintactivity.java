package com.example.yo.a12week;

import android.content.pm.PackageManager;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class paintactivity extends AppCompatActivity {

    MyPainter myPainter;
    CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paintactivity);
        checkPermission();

        myPainter = (MyPainter)findViewById(R.id.mypainter);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    myPainter.checking(1);
                } else {
                    myPainter.checking(0);
                }
            }
        });


    }
    int r,m,sc,sk = 0;
    public void onClick(View v) {
        if (v.getId() == R.id.erase) {
            myPainter.clear();
        } else if (v.getId() == R.id.open) {
            myPainter.clear();
            myPainter.Openfile(getExternalPath() + "MyPainter.jpeg");
        } else if (v.getId() == R.id.save) {
            Log.d("BEOM4", "저장버튼눌림" + getExternalPath());
            if (myPainter.Save(getExternalPath() + "MyPainter.jpeg")) {
                Toast.makeText(getApplicationContext(), "저장완료", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "저장실패", Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.rotate) {
            if (r == 0) {
                checkBox.setChecked(true);
                myPainter.Rotating("rotate");
                r++;
            } else {
                checkBox.setChecked(false);
                myPainter.Rotating("clear");
                r = 0;
            }
        } else if (v.getId() == R.id.move) {
            if (m == 0) {
                checkBox.setChecked(true);
                myPainter.Moving("move");
                m++;
            } else {
                checkBox.setChecked(false);
                myPainter.Moving("clear");
                m = 0;
            }
        } else if (v.getId() == R.id.scale) {
            if (sc == 0) {
                checkBox.setChecked(true);
                myPainter.Scaling("scale");
                sc++;
            } else {
                checkBox.setChecked(false);
                myPainter.Scaling("clear");
                sc = 0;
            }
        } else if (v.getId() ==R.id.skew) {
            if (sk == 0) {
                checkBox.setChecked(true);
                myPainter.Skewing("skew");
                sk++;
            } else {
                checkBox.setChecked(false);
                myPainter.Skewing("clear");
                sk = 0;
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Bluring").setCheckable(true);
        menu.add(0, 2, 0, "Coloring").setCheckable(true);
        menu.add(0, 3, 0, "Pen Width Big").setCheckable(true);
        menu.add(0, 4, 0, "Pen Color RED");
        menu.add(0, 5, 0, "Pen Color BLUE");

        menu.setGroupCheckable(1,true,false);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem Item){
        switch (Item.getItemId()){
            case 1 :
                if(!Item.isChecked()) {
                    myPainter.Bluring(1);
                    Item.setChecked(true);
                }else{
                    myPainter.Bluring(0);
                    Item.setChecked(false);
                }
                return true;
            case 2 :
                if(!Item.isChecked()){
                    myPainter.Coloring(1);
                    Item.setChecked(true);
                }else{
                    myPainter.Coloring(0);
                    Item.setChecked(false);
                }
                return true;
            case 3 :
                if(!Item.isChecked()){
                    myPainter.setPensize(6);
                    Item.setChecked(true);
                }else{
                    myPainter.setPensize(3);
                    Item.setChecked(false);
                }
                return true;
            case 4 : myPainter.setPenColor(Color.RED);
                return true;
            case 5 : myPainter.setPenColor(Color.BLUE);
                return true;
        }
        return super.onOptionsItemSelected(Item);
    }
    public String getExternalPath() {
        String sdPath = "";
        String ext = Environment.getExternalStorageState();
        if (ext.equals(Environment.MEDIA_MOUNTED)) {
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
            Log.d("PATH", sdPath);
        } else {
            sdPath = getFilesDir() + "";
            Toast.makeText(getApplicationContext(), sdPath, Toast.LENGTH_LONG).show();
        }
        return sdPath;
    }
    void checkPermission() {
        int permissioninfo = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissioninfo == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "SDCard 쓰기 권한 있음", Toast.LENGTH_SHORT).show();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(getApplicationContext(), "권한의 필요성 설명", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }
}
