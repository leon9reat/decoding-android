package com.medialink.mymediaplayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class MediaService extends Service
    implements MediaPlayerCallback{

    private MediaPlayer mPlayer = null;
    private boolean isReady = false;

    public final static String ACTION_CREATE = "com.dicoding.picodiploma.mysound.mediaservice.create";
    public final static String ACTION_DESTROY = "com.dicoding.picodiploma.mysound.mediaservice.destroy";
    final String TAG = MediaService.class.getSimpleName();

    public final static int PLAY = 0;
    public final static int STOP = 1;
    private final Messenger mMessenger = new Messenger(new IncomingHandler(this));


    public MediaService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case ACTION_CREATE:
                    if (mPlayer == null) {
                        init();
                    }
                    break;
                case ACTION_DESTROY:
                    if (mPlayer.isPlaying()) {
                        stopSelf();
                    }
                    break;
            }
        }

        Log.d(TAG, "onStartCommand: ");
        return flags;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return mMessenger.getBinder();
    }

    static class IncomingHandler extends Handler {
        private WeakReference<MediaPlayerCallback> mediaPlayerCallbackWeakReference;

        IncomingHandler(MediaPlayerCallback playerCallback) {
            this.mediaPlayerCallbackWeakReference = new WeakReference<>(playerCallback);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case PLAY:
                    mediaPlayerCallbackWeakReference.get().onPlay();
                    break;
                case STOP:
                    mediaPlayerCallbackWeakReference.get().onStop();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    public void onPlay() {
        if (!isReady) {
            mPlayer.prepareAsync();
        } else {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
                showNotif();
            } else {
                mPlayer.start();
            }
        }
    }

    @Override
    public void onStop() {
        if (mPlayer.isPlaying() || isReady) {
            mPlayer.stop();
            stopNotif();
            isReady = false;
        }
    }

    private void init() {
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        AssetFileDescriptor afd = getApplicationContext()
                .getResources()
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
                showNotif();
            }
        });
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
                return false;
            }
        });
    }

    void showNotif() {
        Intent notifIntent = new Intent(this, MainActivity.class);
        notifIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifIntent, 0);

        String CHANNEL_DEFAULT_IMPORTANCE = "Channel_Test";
        int ONGOING_NOTIFICATION_ID = 1;

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
                .setContentTitle("Title Here")
                .setContentText("Text Here")
                .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                .setContentIntent(pendingIntent)
                .setTicker("Ticker")
                .build();

        createChannel(CHANNEL_DEFAULT_IMPORTANCE);
        startForeground(ONGOING_NOTIFICATION_ID, notification);
    }

    private void createChannel(String CHANNEL_ID) {
        NotificationManager mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Battery", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setShowBadge(false);
            channel.setSound(null, null);
            mNotificationManager.createNotificationChannel(channel);
        }
    }

    void stopNotif() {
        stopForeground(true);
    }
}
