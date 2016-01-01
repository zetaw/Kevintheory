package game1.comet.tesla.kevintheory;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Start extends AppCompatActivity {

    private TextView[] Texts = new TextView[4];

    private void waitForTime(long time){
        try {
            Thread.sleep(time);}catch (InterruptedException e){
            e.printStackTrace();
        }
    }

//    private void Fuck_StartGame(){
//        StartGame.setVisibility(View.VISIBLE);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Texts[0] = (TextView) findViewById(R.id.show_view2);
        Texts[1] = (TextView) findViewById(R.id.show_view3);
        Texts[2] = (TextView) findViewById(R.id.show_view4);
        Texts[3] = (TextView) findViewById(R.id.show_view5);
        ImageView logo = (ImageView) findViewById(R.id.Logo);
        Button startGame = (Button) findViewById(R.id.Login);

        Animation showLogo = AnimationUtils.loadAnimation(this, R.anim.show_logo);
        Animation showLoginButton = AnimationUtils.loadAnimation(this, R.anim.show_login_button);
        Animation showTextsFromLeft = AnimationUtils.loadAnimation(this, R.anim.show_texts_from_left);
        Animation showTextsFromRight = AnimationUtils.loadAnimation(this, R.anim.show_texts_from_right);

        logo.startAnimation(showLogo);
        startGame.startAnimation(showLoginButton);

        toFloat();

        boolean bool = true;
        for (TextView Text : Texts) {
            if(bool) {
                Text.startAnimation(showTextsFromLeft);
            }
            else {
                Text.startAnimation(showTextsFromRight);
            }
            bool = !bool;
        }

//        new Thread(){
//            @Override
//            public void run() {
//                TextView[] Texts = new TextView[4];
//
//                Texts[0] = (TextView)findViewById(R.id.show_view2);
//                Texts[1] = (TextView)findViewById(R.id.show_view3);
//                Texts[2] = (TextView)findViewById(R.id.show_view4);
//                Texts[3] = (TextView)findViewById(R.id.show_view5);
//
//                boolean bool = true;
//                for (TextView Text : Texts) {
//                    waitForTime(800);
//                    if(bool)
//                    {
//                        Text.startAnimation(ShowTextsFromLeft);
//                    }else {
//                        Text.startAnimation(ShowTextsFromRight);
//                    }
//                    bool = !bool;
//                }
//            }
//        }.start();
//
//        new Thread(){
//            @Override
//            public void run() {
//                waitForTime(2000);
////                Button startGame;
////                startGame = (Button)findViewById(R.id.Login);
////                startGame.setVisibility(View.VISIBLE);
//                startGame.startAnimation(ShowLogo);
//            }
//        }.start();

//        ShowLogo.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {}
//            @Override
//            public void onAnimationRepeat(Animation animation) {}
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    public void gettingStarted(View v) {
        Intent intent = new Intent(Start.this, MainActivity.class);
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

    private void toFloat(){
        ImageView NC = (ImageView) findViewById(R.id.ncFloat);
        NC.setBackgroundResource(R.drawable.nc_floating);

        AnimationDrawable NCFloat = (AnimationDrawable) NC.getBackground();
        NCFloat.start();
    }
}
