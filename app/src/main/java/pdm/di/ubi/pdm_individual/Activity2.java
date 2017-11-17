package pdm.di.ubi.pdm_individual;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class Activity2 extends Activity {

    TextView oTVteste;
    /** Called  when the activity is first created **/

    @Override
    public void onCreate (Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_layout);
        oTVteste = (TextView) findViewById(R.id.textView_title);

        Intent iCameFromActivity1 = getIntent();
        oTVteste.setText(iCameFromActivity1.getStringExtra("string1"));

    }

    public void endActivity (View v){

        finish();
    }

}

