package pdm.di.ubi.pdm_individual;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by saraiva on 22-11-2017.
 */

public class FullPostActivity extends Activity {


    TextView oTVTitle;
    TextView oTVContent;
    Button oButtonCoordinates;
    DBAuxiliar oDBAux;
    SQLiteDatabase oSQLiteDB;

    @Override
    public void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullpost);


        oDBAux=new DBAuxiliar(this);
        oSQLiteDB = oDBAux.getReadableDatabase();


        oTVTitle = (TextView) findViewById(R.id.tvTitle);
        oTVContent = (TextView) findViewById(R.id.tvContent);
        oButtonCoordinates = (Button) findViewById(R.id.bCoordinates);


        Intent iCameFromActivity1 = getIntent();
        String title_app = iCameFromActivity1.getStringExtra("title");

        Intent iCameFromFillPost = getIntent();
        int id=0, title, content, coordinates;
        id = iCameFromFillPost.getIntExtra("String1", 0);
        Cursor oCursor= null;

        oCursor = oSQLiteDB.rawQuery(" SELECT *" + " FROM " + oDBAux.TABLE_POSTS + " WHERE " + "'" + title_app +"'" + "=" + oDBAux.TITLE, null);
        oCursor.moveToFirst();


        title = oCursor.getColumnIndex(oDBAux.TITLE);
        content = oCursor.getColumnIndex(oDBAux.CONTENT);
        coordinates = oCursor.getColumnIndex(oDBAux.COORDINATES);

        oTVTitle.setText(oCursor.getString(title).toString());
        oTVContent.setText(oCursor.getString(content).toString());


            final Cursor finalOCursor = oCursor;
            final int finalCoordinates = coordinates;
            oButtonCoordinates.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!TextUtils.isEmpty(finalOCursor.getString(finalCoordinates).toString())) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(finalOCursor.getString(finalCoordinates).toString()));
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(FullPostActivity.this, "Ainda não temos coordenadas para este local!", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }

    }