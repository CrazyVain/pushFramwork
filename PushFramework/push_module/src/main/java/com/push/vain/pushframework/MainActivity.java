package com.push.vain.pushframework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.push.vain.pushframework.core.PushService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String MIPUSH_APP_ID = "2882303761517162583";
    private static final String MIPUSH_APP_KEY = "5251716214583";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_jpush).setOnClickListener(this);
        findViewById(R.id.btn_mi_push).setOnClickListener(this);
        findViewById(R.id.btn_hw_push).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jpush:
                PushService.getInstance().registerJpush(this);
                break;
            case R.id.btn_mi_push:
                PushService.getInstance().registerMiPush(this, MIPUSH_APP_ID, MIPUSH_APP_KEY, "");
                break;
            case R.id.btn_hw_push:

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
