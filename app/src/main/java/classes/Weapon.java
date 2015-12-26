package classes;

import android.support.annotation.DrawableRes;

import static game1.comet.tesla.kevintheory.MainActivity.getCounterInt;
import static game1.comet.tesla.kevintheory.MainActivity.plusPlusCounterInt;
import static game1.comet.tesla.kevintheory.MainActivity.setCounterText;

/*
 * Copyright (c) 2015.
 * Coded by Tesla.
 * All rights reserved.
 */
public class Weapon{
    private final int resourse;
    //        造成的伤害值
    private final int hurtValue;

    //        private ImageView imageView;
    private final int exciteValue;
    private final int studyValue;
    private final int healthValue;

    public Weapon(@DrawableRes final int resourse, final int exciteValue, final int healthValue, final int studyValue, final int hurtValue) {
        this.resourse = resourse;
        this.exciteValue = exciteValue;
        this.healthValue = healthValue;
        this.studyValue = studyValue;
//            imageView = new ImageView(MainActivity.this);
        this.hurtValue=hurtValue;
    }

    public Weapon(@DrawableRes final int resourse, final int exciteValue, final int healthValue, final int studyValue) {
        this.resourse = resourse;
        this.exciteValue = exciteValue;
        this.healthValue = healthValue;
        this.studyValue = studyValue;
        this.hurtValue = 1;
    }

    public long toShoot(){
//            setContentView(imageView);
//            imageView.startAnimation(bulletMove);
        plusPlusCounterInt();
        setCounterText();
//            setProgresses(exciteValue, studyValue, healthValue);
        return getCounterInt();
    }

    public int getExciteValue() {
        return exciteValue;
    }

    public int getStudyValue() {
        return studyValue;
    }

    public int getHealthValue() {
        return healthValue;
    }

    public int toAttack(){
        return hurtValue;
    }

    public @DrawableRes int getResourse() {
        return resourse;
    }
}
