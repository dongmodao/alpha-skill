package com.dongmodao.alpha.skill;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SubscriptionInfo;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dongmodao.alpha.skill.utils.FCMUtils;
import com.dongmodao.alpha.skill.utils.LogUtils;
import com.dongmodao.alpha.skill.utils.NetworkUtils;
import com.dongmodao.alpha.skill.utils.PermissionUtils;
import com.dongmodao.alpha.skill.utils.SIMUtils;
import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity---";
    private StringBuilder builder = new StringBuilder();
    private TextView mTvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvLog = findViewById(R.id.tv_log);

        if (!PermissionUtils.hasPermission(this, Manifest.permission.READ_PHONE_STATE))
            PermissionUtils.requestPermission(this, Manifest.permission.READ_PHONE_STATE);

        findViewById(R.id.btn_click).setOnClickListener(v-> {
            String topic = "MY_TOPIC";
            FCMUtils.subscribeToTopic(topic, new FCMUtils.FCMCallbackListener() {
                @Override
                public void onSucceed() {
                    Log.e(TAG, "onSucceed: 订阅成功");
                    addText(String.format("订阅 %s 成功", topic));
                }

                @Override
                public void onFail() {
                    Log.e(TAG, "onSucceed: 订阅失败");
                    addText(String.format("订阅 %s 成功", topic));
                }
            });
        });
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
                String name = SIMUtils.getOperator(this, 0);
                Toast.makeText(this, "operator = " + name, Toast.LENGTH_SHORT).show();
                break;
            case R.id.read:
                String name2 = SIMUtils.getOperator(this, 1);
                Toast.makeText(this, "operator = " + name2, Toast.LENGTH_SHORT).show();
                break;
            case R.id.clean:
                break;
        }
        return true;
    }

    private void addText(String str) {
        builder.append(str + "\n");
        mTvLog.setText(builder.toString());

    }
}
