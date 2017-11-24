package pdm.di.ubi.pdm_individual;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by saraiva on 23-11-2017.
 */

public class Postdisplay extends AppCompatActivity {

    TextView oTV;
    ListView oLV;
    DBAuxiliar oDBAux;
    SQLiteDatabase oSQLiteDB;
    Cursor oCursor;
    ArrayAdapter<String> itemAdapter, itemAdapter2;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.postlist);


        oDBAux=new DBAuxiliar(this);
        oSQLiteDB = oDBAux.getReadableDatabase();

        oLV = (ListView) findViewById(R.id.listviewID);

        oCursor  = oSQLiteDB.rawQuery("SELECT * FROM " + oDBAux.TABLE_POSTS + " WHERE " + oDBAux.CATEGORIE + "=" + "12" ,null);
        oCursor.moveToFirst();
        ArrayList<String> titles = new ArrayList<String>();

        while(!oCursor.isAfterLast()){


           titles.add(oCursor.getString(2).toString());

            oCursor.moveToNext();
        }

        System.out.println(titles);
        itemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, titles);

        oLV.setAdapter(itemAdapter);


        oLV.setOnItemClickListener(
               new AdapterView.OnItemClickListener(){
                   @Override
                   public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                       String title = String.valueOf(adapterView.getItemAtPosition(i));


                       Intent iActvity = new Intent(getApplicationContext(), FullPostActivity.class);
                       iActvity.putExtra("title",title);
                       startActivity(iActvity);


                   }
               }


        );

    }

}