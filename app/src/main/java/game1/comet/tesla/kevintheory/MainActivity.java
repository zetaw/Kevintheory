package game1.comet.tesla.kevintheory;

/**
 * Copyright (c) 2015.
 * All rights reserved.
 */

/**
 *董海辰的Swift代码
 *UIView.animateWithDurations(0.6,{
 *imageView.frame.origins.y+=350;
 *})
 *注：原本在这里的注释放到readme.txt里面去了
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//大量的import别被吓着了
import classes.Constants;
import classes.PlayAudios;
import classes.Weapon;

import static game1.comet.tesla.kevintheory.R.drawable.back5;
import static game1.comet.tesla.kevintheory.R.drawable.back_kurumi;
import static game1.comet.tesla.kevintheory.R.drawable.bullet1;
import static game1.comet.tesla.kevintheory.R.drawable.bullet10;
import static game1.comet.tesla.kevintheory.R.drawable.bullet2;
import static game1.comet.tesla.kevintheory.R.drawable.bullet3;
import static game1.comet.tesla.kevintheory.R.drawable.bullet4;
import static game1.comet.tesla.kevintheory.R.drawable.bullet5;
import static game1.comet.tesla.kevintheory.R.drawable.bullet6;
import static game1.comet.tesla.kevintheory.R.drawable.bullet7;
import static game1.comet.tesla.kevintheory.R.drawable.bullet8;
import static game1.comet.tesla.kevintheory.R.drawable.bullet9;
import static game1.comet.tesla.kevintheory.R.drawable.crazy;
import static game1.comet.tesla.kevintheory.R.drawable.crazy2;
import static game1.comet.tesla.kevintheory.R.drawable.learn1;
import static game1.comet.tesla.kevintheory.R.drawable.learn2;
import static game1.comet.tesla.kevintheory.R.drawable.noting;
import static game1.comet.tesla.kevintheory.R.string.unlocked;

//import MyViews.PointOnTheScreen;

public class MainActivity extends AppCompatActivity {

    private static int cntback = 0;
//    每个属性的值
    private static int cntProExcite = 10;
    private static int cntProHealth = 30;
    private static int cntProStudy = 30;
//    每个属性的进度条控件
    private ProgressBar health;
    private ProgressBar excite;
    private ProgressBar study;

    private static long counterInt = 0;
    private static boolean isOpened = false;
    private static boolean isBossFought = false;
    private static boolean isAboutUsShowed = false;
//    图片是否出现过，为了缩减代码量就改成friedly了
    private static boolean[] stateGot;
//   就是图片数量计数器
    private static short cntOnlyOnceUse;
    //    目前是哪个武器
    private static int cntchange = 0;
    private boolean backPressed = false;

    private static ArrayList<Weapon> weapons = new ArrayList<>();
    private ImageView bullet;
    private ImageView state;
    private static TextView counter;
    private TextView toTalk;
    //    private PointOnTheScreen pointOnTheScreen;
//    动画
    private Animation bulletRrotate;
    private Animation bossAppear;
    private Animation bulletMove;
    private Animation goToRight;
    private Animation goToLeft;

    private PlayAudios playAudios;

//    发布或者调试的时候要修改，方便起见把这个方法放最上面
    public void shootWeapon(View v) {

        Log.d(Constants.MY_TAG,""+isBossFought);
        Log.d(Constants.MY_TAG,"cntOnlyOnceUse = "+cntOnlyOnceUse+",the value should be "+stateGot.length);
//        打Boss，调试时注释上面的if，发布时注释下面的if。
        if((cntOnlyOnceUse>=stateGot.length)&&(!isBossFought)){
//        if(!isBossFought){
            goFightBoss();
        }

        playAudios.playSoundShoot();
        bullet.startAnimation(bulletMove);
        Weapon weapon = weapons.get(cntchange);
        setProgresses(weapon.getExciteValue(), weapon.getHealthValue(), weapon.getStudyValue());
        weapon.toShoot();

//        改图片，并判断是否解锁
        if (cntProHealth >= 150) {
            if (cntProExcite >= 170) {
                if (cntProExcite >= 230) {
                    state.setImageResource(crazy2);
                    toTalk(R.string.toast_text1);
                    checkUnlockState(0);
                } else {
                    state.setImageResource(crazy);
                    toTalk(R.string.toast_text2);
                    checkUnlockState(1);
                }
            }
            if (cntProStudy >= 170) {
                if (cntProStudy >= 230) {
                    state.setImageResource(learn2);
                    toTalk(R.string.toast_text3);
                    checkUnlockState(2);
                } else {
                    state.setImageResource(learn1);
                    toTalk(R.string.toast_text4);
                    checkUnlockState(3);
                }
            }
        }
        cntback++;

//        恢复原状，在太久没反应时
        if (cntback == 10) {
            state.setImageResource(noting);
            state.startAnimation(bossAppear);
            toTalk(R.string.toast_text5);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onlyDoOnce();

        excite = (ProgressBar)findViewById(R.id.progressBar);
        excite.setMax(Constants.LIMITE_OF_VALUES);
        health = (ProgressBar)findViewById(R.id.progressBar2);
        health.setMax(Constants.LIMITE_OF_VALUES);
        study = (ProgressBar)findViewById(R.id.progressBar3);
        study.setMax(Constants.LIMITE_OF_VALUES);

        RelativeLayout main = (RelativeLayout)findViewById(R.id.main);
        Button buttonToAboutUs = (Button) findViewById(R.id.goToAboutUs);
        bullet = (ImageView)findViewById(R.id.bullet);
        state = (ImageView)findViewById(R.id.normal);
        counter = (TextView)findViewById(R.id.counter);
        toTalk = (TextView)findViewById(R.id.talker);

        bossAppear = AnimationUtils.loadAnimation(this,R.anim.boss_appear);
        bulletMove = AnimationUtils.loadAnimation(this, R.anim.shoot);
        bulletRrotate = AnimationUtils.loadAnimation(this,R.anim.rotate);
        goToLeft = AnimationUtils.loadAnimation(this,R.anim.show_weapons_from_right);
        goToRight = AnimationUtils.loadAnimation(this,R.anim.show_weapons_from_left);

//        pointOnTheScreen = new PointOnTheScreen(this);
//        pointOnTheScreen.setAlpha((float) 0.0);
//        setContentView(pointOnTheScreen);

        if (ifTheBossIsFought()){
            main.setBackgroundResource(back5);
            buttonToAboutUs.setVisibility(View.VISIBLE);
            if (!isAboutUsShowed) {
                isAboutUsShowed = true;
                buttonToAboutUs.startAnimation(bossAppear);
            }
        }
//        else {
//            main.setBackgroundResource(back3);
//            buttonToAboutUs.setVisibility(View.INVISIBLE);
//        }

        setProgresses();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(0,0,0,"汪老师");
        menu.add(0,1,1,"真白");
        menu.add(0,2,2,"邵老师");
        menu.add(0,3,3,"何老师");
        menu.add(0,4,4,"狂三");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//noinspection SimplifiableIfStatement
        switch (id) {
            case 0:
                toTalk(getString(R.string.shoot_ms_wang));
                state.setImageResource(bullet6);
                return true;
            case 1:
                toTalk(getString(R.string.shoot_mashiro));
                state.setImageResource(bullet2);
                return true;
            case 2:
                toTalk(getString(R.string.shoot_mr_shao));
                state.setImageResource(bullet5);
                return true;
            case 3:
                toTalk(getString(R.string.shoot_mr_he));
                state.setImageResource(bullet8);
                return true;
            case 4:
                toTalk("❤");
                state.setImageResource(back_kurumi);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        toTalk("欢迎回来");
        setProgresses();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Constants.fightBossId:
                if(resultCode == RESULT_OK){
                    isBossFought = data.getBooleanExtra(Constants.fightBossKey,false);
                    Log.e(Constants.MY_TAG,"isBossFought = "+isBossFought);
                }
                break;
            default:
                break;
        }

        Log.e(Constants.MY_TAG,"这不可能，为什么会这样？");
        constructGameData();
    }

    @Override
    public void onBackPressed() {
        if(!backPressed){
            toast(R.string.back);
            backPressed = true;
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action!=2) {
            Log.d(Constants.MY_TAG,"onTouchEvent Action = " + action);
        }

        String txt;
        txt = getString(R.string.text21)+String.format("(%.0f,%.0f)", event.getX(), event.getY());
        toTalk(txt);
        return super.onTouchEvent(event);
    }

    private void setProgresses(){
        cntProHealth %= Constants.LIMITE_OF_VALUES;
        cntProExcite %= Constants.LIMITE_OF_VALUES;
        cntProStudy %= Constants.LIMITE_OF_VALUES;

        excite.setProgress(cntProExcite);
        health.setProgress(cntProHealth);
        study.setProgress(cntProStudy);

        Log.d(Constants.MY_TAG, "cntProHealth = " + cntProHealth + ",cntProExcite = " + cntProExcite + ",cntProStudy = " + cntProStudy);
    }

    private void setProgresses(int e,int h,int s){
        cntProExcite += e;
        cntProHealth += h;
        cntProStudy += s;

        setProgresses();
    }

    private void toast(String txt){
        Toast.makeText(this,txt,Toast.LENGTH_SHORT).show();
    }

    private void toast(@StringRes int id){
        toast(getString(id));
    }

//    说字符串里面的话
    private void toTalk(@StringRes int id) {
        toTalk.setVisibility(View.VISIBLE);
        toTalk.setText(getString(id));
    }

//    说话
    private void toTalk(String txt) {
        toTalk.setVisibility(View.VISIBLE);
        toTalk.setText(txt);
    }

    private void checkUnlockState(int i){
        if (!stateGot[i]) {
            stateGot[i] = true;
            state.startAnimation(bossAppear);
            cntOnlyOnceUse++;
            Log.d(Constants.MY_TAG,"cntOnlyOnceUse = "+cntOnlyOnceUse+",the value should be "+stateGot.length);
            toTalk(unlocked);
        }
    }

    //    就是全部本来已经解锁了的全部消除，失败付出代价
    private void constructGameData(){
        for (int i = 0; i < stateGot.length; i++ ) {
            stateGot[i] = false;
        }
        stateGot[stateGot.length-1] = true;
//        一切回到解放前
        setCntOnlyOnceUse();
    }

    static short getCntOnlyOnceUse() {
        return cntOnlyOnceUse;
    }

//    初始化图片计数器
    private static void setCntOnlyOnceUse(){
        cntOnlyOnceUse = 1;
    }

    private void goFightBoss(){
//        isBossFought = true;
        toast(R.string.text32);
        Intent intent = new Intent(MainActivity.this, FightBoss.class);
        startActivityForResult(intent, Constants.fightBossId);
//        finish();
        playAudios.stop();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

//    只执行一次的代码,就是登陆界面
    private void onlyDoOnce(){
        if(!isOpened) {
//          第一次打开后马上设置已打开过
            isOpened = true;
//          初始化当前武器
            cntchange = 1;
//          画廊判断数组的定义与初始化
            stateGot = new boolean[5];
//          本来就已经有的图片
            constructGameData();
//            初始化解锁的数量
            setCntOnlyOnceUse();

            Intent Start = new Intent(MainActivity.this, Start.class);
            startActivity(Start);
            toast(R.string.text19);

            weapons.add(new Weapon(bullet1, 28, -10, -11  ));
            weapons.add(new Weapon(bullet2, 31, -15, -12, 3));
            weapons.add(new Weapon(bullet3, -12, 40, -14, 6));
            weapons.add(new Weapon(bullet4, -16, -22, 37, 9));
            weapons.add(new Weapon(bullet5, -16, -3, 35, 12));
            weapons.add(new Weapon(bullet6, -15, -10, 32,15));
            weapons.add(new Weapon(bullet7, 15, -3, 10, 12));
            weapons.add(new Weapon(bullet8, 5, -20, 25, 9));
            weapons.add(new Weapon(bullet9, 20, -20, 10, 6));
            weapons.add(new Weapon(bullet10, -5, -5, 20, 3));
        }
        else {
            playAudios = new PlayAudios(this);
            playAudios.playBackGroundMusicBadApple(true);
        }
    }

    public static void plusPlusCounterInt() {
        counterInt++;
    }

    public static long getCounterInt() {
        return counterInt;
    }
    public static void setCounterText(){
        counter.setText("" + counterInt);
    }


//    }

    static boolean ifTheBossIsFought(){
        return isBossFought;
    }

    static ArrayList<Weapon> getWeapons() {
        return weapons;
    }

//    按钮点击事件
    public void showPhotos(View v){
        v.startAnimation(bulletRrotate);
        Intent intent = new Intent(MainActivity.this, Items.class);
//        Bundle bundle = new Bundle();
//        bundle.putBooleanArray("isShown",stateGot);
        intent.putExtra("isShown", stateGot);
        startActivity(intent);
        finish();
        playAudios.stop();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    public void showWeapons(View v){
        v.startAnimation(bulletRrotate);
        Intent intent = new Intent(MainActivity.this, Weapons.class);
        startActivity(intent);
        finish();
        playAudios.stop();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    public void showAboutus(View v){
        v.startAnimation(bulletRrotate);
        Intent intent = new Intent(MainActivity.this, AboutUs.class);
        startActivity(intent);
        finish();
        playAudios.stop();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    public void changeWeapon(View v) {
        if(R.id.goRight == v.getId()) {
            cntchange++;
            bullet.startAnimation(goToLeft);
        }else if(R.id.goLeft == v.getId()){
            cntchange--;
            bullet.startAnimation(goToRight);
        }
        else {
            return;
        }
        if(cntchange<0){
            cntchange+=weapons.size();
        }
        cntchange %= weapons.size();
        Log.d(Constants.MY_TAG,"weapons.size = " +
                weapons.size() + ",cntchange=" +
                cntchange);
        bullet.setImageResource(weapons.get(cntchange).getResourse());
    }

}
