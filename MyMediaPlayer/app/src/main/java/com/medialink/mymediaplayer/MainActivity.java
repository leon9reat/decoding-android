package com.medialink.mymediaplayer;

import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private MediaPlayer mPlayer = null;
    private boolean isReady;
    private Button btnPlay, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btn_play);
        btnStop = findViewById(R.id.btn_stop);

        btnPlay.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        initView();
    }

    private void initView() {
        mPlayer = new MediaPlayer();
        ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            mPlayer.setAudioAttributes(attributes);
        } else {
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        AssetFileDescriptor afd = getApplicationContext().getResources()
                .openRawResourceFd(R.raw.pelangi);
        try {
            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }

        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                isReady = true;
                mPlayer.start();
            }
        });
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_play:
                if (!isReady) {
                    mPlayer.prepareAsync();
                } else {
                    if (mPlayer.isPlaying()) {
                        mPlayer.pause();
                    } else {
                        mPlayer.start();
                    }
                }
                break;
            case R.id.btn_stop:
                if (mPlayer.isPlaying() || isReady) {
                    mPlayer.stop();
                    isReady = false;
                }
                break;
            default:
                break;
        }
    }
}
