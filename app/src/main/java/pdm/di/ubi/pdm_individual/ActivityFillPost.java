package pdm.di.ubi.pdm_individual;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by saraiva on 21-11-2017.
 */

public class ActivityFillPost extends Activity {


    TextView oTV;


    @Override
    public void onCreate (Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        oTV = (TextView) findViewById(R.id.tvTitle);

        Intent iCameFromActivity1 = getIntent();
        oTV.setText(iCameFromActivity1.getStringExtra("string1"));

    }

    public void endActivity (View v){

        finish();
    }


}
