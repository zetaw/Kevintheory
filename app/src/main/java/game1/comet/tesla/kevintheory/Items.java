package game1.comet.tesla.kevintheory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import classes.Constants;

public class Items extends AppCompatActivity {

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        int i,j = 0;
        Intent intent = getIntent();
        boolean[] stateGot = intent.getBooleanArrayExtra("isShown");
        ImageView[] photos = new ImageView[5];
        ImageView bossPhoto = (ImageView)findViewById(R.id.bossImage);

        TextView rest = (TextView)findViewById(R.id.Rest);
        photos[0] = (ImageView)findViewById(R.id.excite2);
        photos[1] = (ImageView)findViewById(R.id.excite);
        photos[2] = (ImageView)findViewById(R.id.learn2);
        photos[3] = (ImageView)findViewById(R.id.learn);
        photos[4] = (ImageView)findViewById(R.id.normalpic);

        for (i = 0; i < stateGot.length; i++) {
            if(stateGot[i]){
                j++;
            } else {
                photos[i].setImageResource(R.drawable.lock);
            }
        }

        if(j == stateGot.length) {
            rest.setText(R.string.text17);
        }
//        else {
//            rest.setText(R.string.text18);
//        }

        if(MainActivity.ifTheBossIsFought()){
            bossPhoto.setVisibility(View.VISIBLE);
        }
//        else {
//            bossPhoto.setVisibility(View.INVISIBLE);
//        }

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Items.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }
        });
//        TabHost Items = (TabHost)findViewById(R.id.tabHost);
//        Items.addTab(Items.newTabSpec("tab1").setIndicator("兴奋状态",getResources().getDrawable(R.drawable.bullet1)).setContent(R.id.tab1));
//        Items.addTab(Items.newTabSpec("tab2").setIndicator("学习状态",getResources().getDrawable(R.drawable.bullet1)).setContent(R.id.tab2));
//        Items.addTab(Items.newTabSpec("tab3").setIndicator("普通状态",getResources().getDrawable(R.drawable.bullet1)).setContent(R.id.tab3));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_items, menu);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        TextView text = (TextView)findViewById(R.id.text12121212);
        int action = event.getAction();
        Log.d(Constants.MY_TAG, "onTouchEvent Action = " + action);
        String txt;
        txt = getString(R.string.text21)+String.format("(%.0f,%.0f)", event.getX(), event.getY());
        text.setText(txt);
        return super.onTouchEvent(event);
    }

    public void returnToMainFromItems(View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

}