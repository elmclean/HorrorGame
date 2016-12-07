package com.example.elmclean.horrorgame;

/**
 * Created by elmclean on 10/24/2016.
 */

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class MusicService extends Service implements MediaPlayer.OnErrorListener {
    public static final String TAG = "MusicTag";
    private final IBinder mBinder = new ServiceBinder();
    MediaPlayer mPlayer = null;
    private int length = 0;
    public static final String MUSIC_NAME = "MUSIC_NAME";
    public String songName = "";

    public MusicService() {
    }

    public class ServiceBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        songName = extras.getString("MUSIC_NAME");

        if(mPlayer != null) {
            if(mPlayer.isPlaying()) {  // stop the music already playing
                stopMusic();
            }
        }

        // select the song to play
        if(songName.equals("lost_chair")) {
            mPlayer = MediaPlayer.create(this, R.raw.lost_chair);
        } else if(songName.equals("rumor")){
            mPlayer = MediaPlayer.create(this, R.raw.rumor);
        } else if(songName.equals("miller_house")) {
            mPlayer = MediaPlayer.create(this, R.raw.miller_house);
        } else if(songName.equals("undermine")) {
            mPlayer = MediaPlayer.create(this, R.raw.undermine);
        } else if(songName.equals("warehouse")) {
            mPlayer = MediaPlayer.create(this, R.raw.warehouse);
        } else if(songName.equals("walls")) {
            mPlayer = MediaPlayer.create(this, R.raw.walls);
        } else if(songName.equals("bone_crush")) {
            mPlayer = MediaPlayer.create(this, R.raw.bone_crush);
        }

        mPlayer.setOnErrorListener(this);

        if (mPlayer != null) {
            mPlayer.setLooping(true);
            mPlayer.setVolume(100, 100);
        }

        mPlayer.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int
                    extra) {
                onError(mPlayer, what, extra);
                return true;
            }
        });

        mPlayer.start();  // start the music
        return START_STICKY;
    }

    public void pauseMusic() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            length = mPlayer.getCurrentPosition();
        }
    }

    public void resumeMusic() {
        if (mPlayer.isPlaying() == false) {
            mPlayer.seekTo(length);
            mPlayer.start();
        }
    }

    public void stopMusic() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {

        Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }
        return false;
    }
}
