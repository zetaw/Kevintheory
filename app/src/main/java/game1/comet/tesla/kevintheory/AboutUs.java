package game1.comet.tesla.kevintheory;

/*
 * Copyright (c) 2015.
 * Coded by Tesla.
 * All rights reserved.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class AboutUs extends AppCompatActivity {


    private Animation Rotate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        TextView[] textViews = new TextView[5];

        textViews[0] = (TextView) findViewById(R.id.AboutUs1);
        textViews[1] = (TextView) findViewById(R.id.AboutUs2);
        textViews[2] = (TextView) findViewById(R.id.AboutUs3);
        textViews[3] = (TextView) findViewById(R.id.AboutUs4);
        textViews[4] = (TextView) findViewById(R.id.AboutUs5);

        Animation showTextsFromLeft = AnimationUtils.loadAnimation(this, R.anim.show_texts_from_left);
        Animation showTextsFromRight = AnimationUtils.loadAnimation(this, R.anim.show_texts_from_right);
        Rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);

        boolean bool = true;
        for (TextView Text : textViews) {
            if (bool) {
                Text.startAnimation(showTextsFromLeft);
            } else {
                Text.startAnimation(showTextsFromRight);
            }
            bool = !bool;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_us, menu);
        return true;
    }

    public void returnToMainFromAbout(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        TextView text = (TextView) findViewById(R.id.textView5);
        int action = event.getAction();
        Log.d("MainActivity", "onTouchEvent Action = " + action);
        String txt;
        txt = getString(R.string.text21) + String.format("(%.0f,%.0f)", event.getX(), event.getY());
        text.setText(txt);
        return super.onTouchEvent(event);
    }

    public void doSendSMSTo(View v) {
        v.startAnimation(Rotate);
        Toast.makeText(AboutUs.this, "抱歉，冰封为了保护隐私已禁用电话啦！", Toast.LENGTH_SHORT).show();
//        String phoneNumber = "18081925082";
//        String message = getString(R.string.text30);
//        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
//        intent.putExtra("sms_body", message);
//        startActivity(intent);
    }

    public void callMe(View v) {
        v.startAnimation(Rotate);
        Toast.makeText(AboutUs.this, "抱歉，冰封为了保护隐私已禁用电话啦！", Toast.LENGTH_SHORT).show();
//        Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:18081925082"));
//        call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        startActivity(call);
    }

}
