package com.bignerdranch.android.alarmapp.Utility;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

/**
 * Created by DaHoon on 2017-01-24.
 */

public class AlarmSoundPlay {

    private Context mContext;
    private static MediaPlayer player;

    public AlarmSoundPlay(Context context){
        mContext = context;
    }

    public void soundPlay() {
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        player = new MediaPlayer();

        try {
            player.setDataSource(mContext, alert);
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        } catch (SecurityException e1) {
            e1.printStackTrace();
        } catch (IllegalStateException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        final AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            player.setAudioStreamType(AudioManager.STREAM_ALARM);
            player.setLooping(false);

            try {
                player.prepare();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.start();
        }
    }

    public void cancelPlay() {
        if(player.isPlaying()){
            player.stop();
            player.reset();
        }
    }
}
