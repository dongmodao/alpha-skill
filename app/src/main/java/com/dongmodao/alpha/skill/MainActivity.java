package com.dongmodao.alpha.skill;

import android.accounts.Account;
import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dongmodao.alpha.skill.skills.syncadapter.SyncAdapterUtils;
import com.dongmodao.alpha.skill.utils.FCMUtils;
import com.dongmodao.alpha.skill.utils.LogUtils;
import com.dongmodao.alpha.skill.workmanager.FCMWorker;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import static com.dongmodao.alpha.skill.skills.syncadapter.SyncAdapterUtils.AUTHORITY;
import static com.dongmodao.alpha.skill.skills.syncadapter.SyncAdapterUtils.SYNC_INTERVAL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity---";
    private StringBuilder builder = new StringBuilder();
    private TextView mTvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvLog = findViewById(R.id.tv_log);
        Account mAccount = SyncAdapterUtils.CreateSyncAccount(this);

        findViewById(R.id.btn_click).setOnClickListener(v-> {
//            setupWorker();
            /*
             * Turn on periodic syncing
             */
            ContentResolver.addPeriodicSync(
                    mAccount,
                    AUTHORITY,
                    Bundle.EMPTY,
                    SYNC_INTERVAL);
        });


}

    private void setupWorker() {
//        PeriodicWorkRequest.Builder photoCheckBuilder =
//                new PeriodicWorkRequest.Builder(FCMWorker.class, 15,
//                        TimeUnit.MINUTES);
//        PeriodicWorkRequest fcmWork = photoCheckBuilder.build();
//        WorkManager.getInstance().enqueue(fcmWork);
//        // tag
//        WorkManager.getInstance().cancelAllWorkByTag("tag");
//        // id
//        WorkManager.getInstance().cancelWorkById(fcmWork.getId());
        OneTimeWorkRequest.Builder builder = new OneTimeWorkRequest.Builder(FCMWorker.class)
                .setConstraints(new Constraints.Builder().setRequiresCharging(true)
                        .setRequiredNetworkType(NetworkType.CONNECTED).build());
        WorkManager.getInstance().enqueue(builder.build());
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
