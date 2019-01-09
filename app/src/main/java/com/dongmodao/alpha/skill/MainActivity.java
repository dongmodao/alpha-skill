package com.dongmodao.alpha.skill;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dongmodao.alpha.skill.utils.LogUtils;
import com.dongmodao.alpha.skill.utils.NetworkUtils;
import com.dongmodao.alpha.skill.utils.PermissionUtils;
import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity---";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!PermissionUtils.hasPermission(this, Manifest.permission.READ_PHONE_STATE))
            PermissionUtils.requestPermission(this, Manifest.permission.READ_PHONE_STATE);

        findViewById(R.id.btn_click).setOnClickListener(v-> {
            if (!PermissionUtils.hasPermission(this, Manifest.permission.READ_PHONE_STATE))
                return;
            Toast.makeText(this, String.format("data = %s; name = %s; IMSI = %s; sim = %d, data = %d; phone = %d",
                    NetworkUtils.isMobileDataEnable(this),
                    NetworkUtils.getOperatorName(this),
                    NetworkUtils.getOperatorIMSI(this),
                    NetworkUtils.getSIMCount(this),
                    NetworkUtils.getDefaultDataID(this),
                    NetworkUtils.getPhoneCount(this)
            ), Toast.LENGTH_SHORT).show();
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
                String name = NetworkUtils.getSIMOperator(this, 0);
                Toast.makeText(this, "name = " + name, Toast.LENGTH_SHORT).show();
                break;
            case R.id.read:
                String name2 = NetworkUtils.getSIMOperator(this, 1);
                Toast.makeText(this, "name = " + name2, Toast.LENGTH_SHORT).show();
                break;
            case R.id.clean:
                break;
        }
        return true;
    }
}
