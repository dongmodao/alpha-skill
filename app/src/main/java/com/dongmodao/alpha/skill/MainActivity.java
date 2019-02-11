package com.dongmodao.alpha.skill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dongmodao.alpha.skill.utils.FCMUtils;
import com.dongmodao.alpha.skill.utils.LogUtils;
import com.dongmodao.alpha.skill.workmanager.FCMWorker;
import com.dongmodao.alpha.skill.workmanager.FCMWorkerService;

import java.util.concurrent.TimeUnit;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity---";
    private StringBuilder builder = new StringBuilder();
    private TextView mTvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvLog = findViewById(R.id.tv_log);

        findViewById(R.id.btn_click).setOnClickListener(v-> {
            Toast.makeText(this, "debug = " + BuildConfig.DEBUG, Toast.LENGTH_SHORT).show();
        });

        addText(LogUtils.read(this));
        startService(new Intent(this, FCMWorkerService.class));
        setupWorker();
}

    private void setupWorker() {
        PeriodicWorkRequest.Builder photoCheckBuilder =
                new PeriodicWorkRequest.Builder(FCMWorker.class, 15,
                        TimeUnit.MINUTES);
        PeriodicWorkRequest fcmWork = photoCheckBuilder.build();
        WorkManager.getInstance().enqueue(fcmWork);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                FCMUtils.subscribeToTopic(getString(R.string.app_name), new FCMUtils.FCMCallbackListener() {
                    @Override
                    public void onSucceed() {
                        Toast.makeText(MainActivity.this, "订阅成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(MainActivity.this, "订阅失败", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.read:
                setText(LogUtils.read(this));
                break;
            case R.id.clean:
                LogUtils.clean(this);
                break;
            default:
                break;
        }
        return true;
    }

    private void addText(String str) {
        builder.append(str).append("\n");
        mTvLog.setText(builder.toString());
    }

    private void setText(String str) {
        mTvLog.setText(str);
    }
}
