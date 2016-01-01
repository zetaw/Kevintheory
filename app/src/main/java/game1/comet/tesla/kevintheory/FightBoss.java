package game1.comet.tesla.kevintheory;

/**
 * Copyright (c) 2015.
 * Coded by Tesla.
 * All rights reserved.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.StringRes;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import classes.Constants;
import classes.PlayAudios;
import classes.Weapon;

public class FightBoss extends AppCompatActivity {

    private ImageView bullet;
    private ImageView boss;
    private Button shootButton;
    private int blood;
    private boolean isTimeUp = false;
    private boolean isBossFought = false;
    private int cntChange = 0;
    private ProgressBar bossBlood;
    private ArrayList<Weapon> weaponsInFightBossActivity;
    private Animation goToLeft;
    private Animation goToRight;
    private Animation bulletMove;
    private PlayAudios playAudios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight_boss);

        weaponsInFightBossActivity = MainActivity.getWeapons();
//        blood = 5;
        blood = Constants.LIMITE_OF_VALUES * 2;
        isTimeUp = false;

        goToLeft = AnimationUtils.loadAnimation(this, R.anim.show_weapons_from_right);
        goToRight = AnimationUtils.loadAnimation(this, R.anim.show_weapons_from_left);
        bulletMove = AnimationUtils.loadAnimation(this,R.anim.shoot);

        bullet = (ImageView)findViewById(R.id.bossBullet);
        boss = (ImageView) findViewById((R.id.boss));
        shootButton = (Button)findViewById(R.id.shootInFightBoss);
        bossBlood = (ProgressBar) findViewById(R.id.bossBlood);
        bossBlood.setMax(blood);
        bossBlood.setProgress(blood);

//        振动，检查是否允许了
        if(PermissionChecker.checkCallingOrSelfPermission(this,
                "android.permission.VIBRATE") ==
                PermissionChecker.PERMISSION_GRANTED) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(1000);
        }

//        音效系统初始化
        playAudios = new PlayAudios(this);
        playAudios.playBackGroundMusicEastProject(true);

        boss.startAnimation(AnimationUtils.loadAnimation(this, R.anim.boss_appear));
        toast(R.string.text33);
//        计时退出
        judgeIfTheTimeIsUp(20000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fight_boss, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setBossBloodProgress(){
        blood -= weaponsInFightBossActivity.get(cntChange).toAttack();
        bossBlood.setProgress(blood);
    }

//    返回主Activity
    private void goBackInFightBoss(){
        playAudios.stop();
//        Intent intent = new Intent(this,MainActivity.class);
        Intent intent = new Intent();
        intent.putExtra(Constants.fightBossKey,isBossFought);
        setResult(RESULT_OK,intent);
//        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

//    新建一个线程，等待一定时间之后修改isTimeUp的值
    private void judgeIfTheTimeIsUp(final long time){
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isTimeUp = true;
            }
        }.start();
    }

//    按钮监听器和toast的发送器
    public void changeWeaponInFightBoss(View v) {
        if(R.id.bossGoRight == v.getId()) {
            cntChange++;
            bullet.startAnimation(goToLeft);
        }else if(R.id.bossGoLeft == v.getId()){
            cntChange--;
            bullet.startAnimation(goToRight);
        }
        else
            return;
        if(cntChange <0){
            cntChange +=weaponsInFightBossActivity.size();
        }
        cntChange %= weaponsInFightBossActivity.size();
        Log.w(Constants.MY_TAG, "weaponsInFightBossActivity.size = " +
                weaponsInFightBossActivity.size() + ",cntChange=" + cntChange);
        bullet.setImageResource(weaponsInFightBossActivity.get(cntChange).getResourse());
    }

    public void returnToMainFromFightBoss(View v){
        toast(R.string.text34);
        goBackInFightBoss();
    }

    public void shootWeaponInFightBoss(View v) {
        playAudios.playSoundShoot();
        bullet.startAnimation(bulletMove);

        if(isTimeUp){
            toast(R.string.text35);
            shootButton.setVisibility(View.INVISIBLE);
            Log.d(Constants.MY_TAG,"Fight boss failed.");
        }
        else {
            setBossBloodProgress();
            Log.d(Constants.MY_TAG, "blood=" + blood);
            if(blood<=0){
                boss.setVisibility(View.INVISIBLE);
                isBossFought = true;
                playAudios.playSoundBoom();
                Log.d(Constants.MY_TAG, "Fight boss succeeded.");
                toast(R.string.text36);
                goBackInFightBoss();
            }
        }
    }

    private void toast(String txt){
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }

    private void toast(@StringRes int id){
        toast(getString(id));
    }
}
