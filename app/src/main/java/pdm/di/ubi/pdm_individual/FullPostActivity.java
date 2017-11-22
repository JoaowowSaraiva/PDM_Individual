package pdm.di.ubi.pdm_individual;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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


        Intent iCameFromFillPost = getIntent();
        int id=0, title, content, coordinates;
        id = iCameFromFillPost.getIntExtra("String1", 0);
        Cursor oCursos= null;

        oCursos  = oSQLiteDB.rawQuery("SELECT * FROM " + oDBAux.TABLE_POSTS + " WHERE " + id + "="+ oDBAux.IDPOST ,null);
        oCursos.moveToFirst();


        title = oCursos.getColumnIndex(oDBAux.TITLE);
        content = oCursos.getColumnIndex(oDBAux.CONTENT);
        coordinates = oCursos.getColumnIndex(oDBAux.COORDINATES);

        oTVTitle.setText(oCursos.getString(title).toString());
        oTVContent.setText(oCursos.getString(content).toString());


        final Cursor finalOCursos = oCursos;
        final int finalCoordinates = coordinates;
        oButtonCoordinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(finalOCursos.getString(finalCoordinates).toString() ) );
                startActivity(intent);
            }
        });

    }



}



