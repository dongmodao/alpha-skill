package com.dongmodao.alpha.skill;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dongmodao.alpha.skill.utils.LogUtils;

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
