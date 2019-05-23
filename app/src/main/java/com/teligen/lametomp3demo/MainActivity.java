package com.teligen.lametomp3demo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jiangdg.lametomp3.Mp3Recorder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Mp3Recorder mMp3Recorder;
    private boolean isStart = false;
    private Button mBtnRec;
    private TextView mTvDbVolumn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化UI
        mBtnRec = (Button) findViewById(R.id.btn_record);
        mTvDbVolumn = (TextView) findViewById(R.id.tv_db_volumn);
        mBtnRec.setOnClickListener(this);

        mMp3Recorder = new Mp3Recorder.Builder().build();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_record) {
            if (!isStart) {
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                String fileName = "audio" + System.currentTimeMillis();
                mMp3Recorder.start(filePath, fileName, new Mp3Recorder.OnAACStreamResultListener() {
                    @Override
                    public void onEncodeResult(byte[] data, int offset, int length, long timestamp) {
                        Log.i("MainActivity", "acc数据流长度：" + data.length);
                    }

                    @Override
                    public void onDBChanged(final int db) {
                        mTvDbVolumn.post(new Runnable() {
                            @Override
                            public void run() {
                                mTvDbVolumn.setText(db + "");
                            }
                        });
                    }
                });

                mBtnRec.setText("停止");
            } else {
                mMp3Recorder.stop();

                mBtnRec.setText("开始");
            }
            isStart = !isStart;
        }
    }

}
