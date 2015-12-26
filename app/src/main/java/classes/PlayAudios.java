package classes;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import game1.comet.tesla.kevintheory.R;

/*
 * Copyright (c) 2015.
 * Coded by Tesla.
 * All rights reserved.
 */
public class PlayAudios {

    private Context context;
    private SoundPool soundPool;
    private int boomVoice;
    private int shootVoice;
    private MediaPlayer mediaPlayer;

    public PlayAudios(Context c) {
        this.context = c;
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 1);
        boomVoice = soundPool.load(context, R.raw.boom_hit, 1);
        shootVoice = soundPool.load(context, R.raw.small_hit, 1);
    }

    private void setMediaPlayer(final boolean loopOrNot){
        //        mediaPlayer.setVolume(1,1);
        mediaPlayer.start();
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mp.stop();
                mp.reset();
//                mediaPlayer.release();
                return false;
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
//                如果重复就设置重复。
                if(loopOrNot){
                    mediaPlayer.reset();
                    mediaPlayer.start();
                }
                else {
                    mediaPlayer.release();
                }
            }
        });
    }

//    音乐，必须设置循环与否。
    public void playBackGroundMusicEastProject(boolean loopOrNot) {
        mediaPlayer = MediaPlayer.create(context, R.raw.bgm_east_project1);
        setMediaPlayer(loopOrNot);
    }

    public void playBackGroundMusicBadApple(boolean loopOrNot) {
        mediaPlayer = MediaPlayer.create(context, R.raw.bgm_bad_apple);
        setMediaPlayer(loopOrNot);
    }

//    音效
    public void playSoundShoot(){
        soundPool.play(shootVoice, 2, 2, 0, 0, 1);
        Log.d(Constants.MY_TAG, "small voice is played!");
    }

    public void playSoundBoom(){
        soundPool.play(boomVoice, 2, 2, 0, 0, 1);
        Log.d(Constants.MY_TAG, "Boom voice is played!");
    }

    public void stop() {
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

}
