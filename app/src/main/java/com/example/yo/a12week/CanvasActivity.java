package com.example.yo.a12week;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

/**
 * Created by yo on 2017-05-18.
 */

public class CanvasActivity extends AppCompatActivity {
    MyCanvas2 myCanvas;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCanvas = (MyCanvas2)findViewById(R.id.canvas);
    }

    public void onClick(View v){
        myCanvas.setOperationType((String)v.getTag());
    }

}
