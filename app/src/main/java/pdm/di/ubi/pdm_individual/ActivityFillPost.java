package pdm.di.ubi.pdm_individual;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by saraiva on 21-11-2017.
 */

public class ActivityFillPost extends Activity {


    TextView oTV;
    TextView oTV2;
    Button oBtn;


    @Override
    public void onCreate (Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        oTV = (TextView) findViewById(R.id.tvTitle);
        oTV2 = (TextView) findViewById(R.id.tvContent);
        oBtn = (Button) findViewById(R.id.bLerMais);


        Intent iCameFromActivity1 = getIntent();
        //oTV.setText(iCameFromActivity1.getStringExtra("string1"));


        DBAuxiliar oDBAux = new DBAuxiliar(this);

        SQLiteDatabase oSQLiteDB = oDBAux.getReadableDatabase();
        Cursor oCursor = null;
        oCursor = oSQLiteDB.rawQuery(" SELECT *" + " FROM " + oDBAux.TABLE_POSTS + " WHERE " + "2492" + "=" + oDBAux.IDPOST, null);
        oCursor.moveToFirst();
        int excerpt, title;

        excerpt = oCursor.getColumnIndex(oDBAux.EXCERPT);
        title = oCursor.getColumnIndex(oDBAux.TITLE);


        oTV.setText(oCursor.getString(title).toString());
        oTV2.setText(oCursor.getString(excerpt).toString());






    }


    public void activityLerMais(View v){
            int id=2492;

            Intent iActvity = new Intent(this, FullPostActivity.class);
            iActvity.putExtra("String1", id);
            startActivity(iActvity);

    }

}
