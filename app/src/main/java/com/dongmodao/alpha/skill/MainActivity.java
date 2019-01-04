package com.dongmodao.alpha.skill;

import android.accounts.Account;
import android.content.ContentResolver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dongmodao.alpha.skill.jni.JNIUtils;
import com.dongmodao.alpha.skill.syncadapter.java.SyncAdapter;
import com.dongmodao.alpha.skill.syncadapter.java.SyncAdapterUtils;
import com.dongmodao.alpha.skill.utils.LogUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity---";

    // Instance fields
    Account mAccount;
    ContentResolver mResolver;

    TextView mTvLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAccount = SyncAdapterUtils.CreateSyncAccount(this);

        mTvLog = findViewById(R.id.tv_log);
        // Get the content resolver for your app
        mResolver = getContentResolver();
        /*
         * Turn on periodic syncing
         */
        ContentResolver.addPeriodicSync(
                mAccount,
                SyncAdapterUtils.AUTHORITY,
                Bundle.EMPTY,
                SyncAdapterUtils.SYNC_INTERVAL);

        String string = LogUtils.read(this);
        mTvLog.setText(string);

        findViewById(R.id.btn_click).setOnClickListener(v->{
            String str = JNIUtils.getRealStr("origin string");
            Log.e(TAG, "onCreate: new string = " + str );
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
                Bundle settingsBundle = new Bundle();
                settingsBundle.putBoolean(
                        ContentResolver.SYNC_EXTRAS_MANUAL, true);
                settingsBundle.putBoolean(
                        ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
                /*
                 * Request the sync for the default account, authority, and
                 * manual sync settings
                 */
                ContentResolver.requestSync(mAccount, SyncAdapterUtils.AUTHORITY, settingsBundle);
                break;
            case R.id.read:
                String string = LogUtils.read(this);
                mTvLog.setText(string);
                break;
            case R.id.clean:
                LogUtils.clean(this);
                break;
        }

        return true;
    }
}
