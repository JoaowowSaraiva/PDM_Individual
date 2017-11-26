package pdm.di.ubi.pdm_individual;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by saraiva on 22-11-2017.
 */

public class FullPostActivity extends AppCompatActivity {


    TextView oTVTitle;
    TextView oTVContent;
    ImageButton oButtonCoordinates;
    DBAuxiliar oDBAux;
    SQLiteDatabase oSQLiteDB;
    String titleForIntent="";
    String ContentForIntent="";

    @Override
    public void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullpost);


        oDBAux=new DBAuxiliar(this);
        oSQLiteDB = oDBAux.getReadableDatabase();


        oTVTitle = (TextView) findViewById(R.id.tvTitle);
        oTVContent = (TextView) findViewById(R.id.tvContent);
        oButtonCoordinates = (ImageButton) findViewById(R.id.bCoordinates);


        Intent iCameFromActivity1 = getIntent();
        String title_app = iCameFromActivity1.getStringExtra("title");

        Intent iCameFromFillPost = getIntent();
        int id=0, title, content, coordinates;
        id = iCameFromFillPost.getIntExtra("String1", 0);
        Cursor oCursor= null;

        //fazer esta função no DBAux
        oCursor = oSQLiteDB.rawQuery(" SELECT *" + " FROM " + oDBAux.TABLE_POSTS + " WHERE " + "'" + title_app +"'" + "=" + oDBAux.TITLE, null);
        oCursor.moveToFirst();


        title = oCursor.getColumnIndex(oDBAux.TITLE);
        content = oCursor.getColumnIndex(oDBAux.CONTENT);
        coordinates = oCursor.getColumnIndex(oDBAux.COORDINATES);

        titleForIntent=oCursor.getString(title).toString();
        ContentForIntent=oCursor.getString(content).toString();

        oTVTitle.setText(oCursor.getString(title).toString());
        oTVContent.setText(oCursor.getString(content).toString());


            final Cursor finalOCursor = oCursor;
            final int finalCoordinates = coordinates;
        oSQLiteDB.close();
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

        //Despleta o intent que ira tratar de fazer share do post lido.
   public void startShareActivity(View v){

       Intent intent = new Intent(Intent.ACTION_SEND);
       intent.setType("text/plain");
       intent.putExtra(Intent.EXTRA_TEXT, titleForIntent + '\n' + ContentForIntent);
       startActivity(Intent.createChooser(intent, ""));

   }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.Home: {
                Intent iActivity1 = new Intent(getApplicationContext(), Main.class);
                startActivity(iActivity1);
                return true;
            }
            case R.id.PraiasFluviaisNorte: {
                Intent iActivity2 = new Intent(getApplicationContext(), NorthenRiverBeaches.class);
                startActivity(iActivity2);
                return true;
            }
            case R.id.PraiasFluviaisCentro: {
                Intent iActvity3 = new Intent(getApplicationContext(), CenterRiverBeaches.class);
                startActivity(iActvity3);
                return true;
            }
            case R.id.PraiasFluviaisSul: {
                Intent iActvity4 = new Intent(getApplicationContext(), SouthRiverBeaches.class);
                startActivity(iActvity4);
                return true;
            }
            case R.id.Acores: {
                Intent iActvity5 = new Intent(getApplicationContext(), AcoresRiverBeaches.class);
                startActivity(iActvity5);
                return true;
            }
            case R.id.Madeira: {
                Intent iActvity6 = new Intent(getApplicationContext(), MadeiraRiverBeaches.class);
                startActivity(iActvity6);
                return true;
            }
            case R.id.Destaques: {
                Intent iActvity7 = new Intent(getApplicationContext(), HighlightsRiverBeaches.class);
                startActivity(iActvity7);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}