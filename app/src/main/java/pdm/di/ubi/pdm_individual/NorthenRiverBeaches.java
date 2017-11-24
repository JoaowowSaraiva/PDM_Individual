package pdm.di.ubi.pdm_individual;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  ID's da zona norte:
 *  Aveiro:34
 *  Braga:9
 *  Bragança:37
 *  Porto:11
 *  Viana do Castelo:36
 *  Vila Real:38
 *  Viseu:39
 */

public class NorthenRiverBeaches extends AppCompatActivity {


    private WebView oWV;
    private ExpandableListView eListView;
    private ExpandableListAdapter oListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;
    private DBAuxiliar oDBAux;
    private SQLiteDatabase oSQLiteDB;



    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nrb_layout);



        oWV = (WebView) findViewById(R.id.wvMaps);
        oWV.getSettings().setJavaScriptEnabled(true);
        oWV.loadData("<iframe src=\"https://www.google.com/maps/d/embed?mid=1D7X0gLTh1FBibYypH8VmJ5715f8\" width=\"640\" height=\"480\"></iframe>", "text/html", null);



        eListView = (ExpandableListView) findViewById(R.id.expandlvNorte);
        putInitData();
        oListAdapter = new ExpandableListAdapter(this,listDataHeader, listHashMap);
        eListView.setAdapter(oListAdapter);

        eListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){

            @Override
            public boolean onChildClick (ExpandableListView parent, View v, int groupPosition, int childPosition, long id){
                String teste20 = String.valueOf(parent.getItemAtPosition(groupPosition));

                listDataHeader.get(groupPosition);

                String x= oListAdapter.getChild(groupPosition, childPosition).toString();

                //String title = String.valueOf(adapterView.getItemAtPosition(i));
                Toast.makeText(NorthenRiverBeaches.this, x, Toast.LENGTH_SHORT).show();
                return true;

            }


        });


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
                Toast.makeText(this, "Primeiro Item", Toast.LENGTH_SHORT).show();
                Intent iActivity1 = new Intent(getApplicationContext(), Main.class);
                startActivity(iActivity1);
                return true;
            }
            case R.id.PraiasFluviaisNorte: {
                Toast.makeText(this, "Segundo Item", Toast.LENGTH_SHORT).show();
                Intent iActivity2 = new Intent(getApplicationContext(), NorthenRiverBeaches.class);
                startActivity(iActivity2);
                return true;
            }
            case R.id.PraiasFluviaisCentro: {
                Toast.makeText(this, "PraiasCentro", Toast.LENGTH_SHORT).show();
                Intent iActvity3 = new Intent(getApplicationContext(), CenterRiverBeaches.class);
                startActivity(iActvity3);
                return true;
            }
            case R.id.PraiasFluviaisSul: {
                Toast.makeText(this, "PraiaSul", Toast.LENGTH_SHORT).show();
                Intent iActvity4 = new Intent(getApplicationContext(), SouthRiverBeaches.class);
                startActivity(iActvity4);
                return true;
            }
            case R.id.Acores: {
                Toast.makeText(this, "Acores", Toast.LENGTH_SHORT).show();
                Intent iActvity5 = new Intent(getApplicationContext(), AcoresRiverBeaches.class);
                startActivity(iActvity5);
                return true;
            }
            case R.id.Madeira: {
                Toast.makeText(this, "Madeira", Toast.LENGTH_SHORT).show();
                Intent iActvity6 = new Intent(getApplicationContext(), MadeiraRiverBeaches.class);
                startActivity(iActvity6);
                return true;
            }
            case R.id.Destaques: {
                Toast.makeText(this, "PraiaSul", Toast.LENGTH_SHORT).show();
                Intent iActvity7 = new Intent(getApplicationContext(), HighlightsRiverBeaches.class);
                startActivity(iActvity7);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }



    public void putInitData(){
        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        listDataHeader.add("Aveiro");
        listDataHeader.add("Braga");
        listDataHeader.add("Bragança");
        listDataHeader.add("Porto");
        listDataHeader.add("Viana do Castelo");
        listDataHeader.add("Vila Real");
        listDataHeader.add("Viseu");

        List<String> Aveiro = new ArrayList<>();

        oDBAux= new DBAuxiliar(this);
        oSQLiteDB = oDBAux.getReadableDatabase();

        Cursor oCursor = null;
        oCursor = oSQLiteDB.rawQuery("SELECT " + oDBAux.TITLE + " FROM " + oDBAux.TABLE_POSTS + " WHERE " + oDBAux.CATEGORIE + "=" + "34", null );
        oCursor.moveToFirst();


        while(!oCursor.isAfterLast()){
            Aveiro.add(oCursor.getString(0).toString());
            oCursor.moveToNext();
        }

        List<String> Braga = new ArrayList<>();
        Braga.add("sub item do teste2 - 1");
        Braga.add("Sub item do teste2 - 2");


        List<String> Bragança = new ArrayList<>();
        Bragança.add("sub item do teste3 - 1");
        Bragança.add("Sub item do teste3 - 2");
        Bragança.add("Mais uma so para que sim");

        List<String> Porto = new ArrayList<>();
        Porto.add("AYYAAYYUUIII");
        Porto.add("UIUIUIU AIII");

        List<String> VianadoCastelo = new ArrayList<>();
        Porto.add("alalal");

        listHashMap.put(listDataHeader.get(0), Aveiro);
        listHashMap.put(listDataHeader.get(1), Braga);
        listHashMap.put(listDataHeader.get(2), Bragança);
        listHashMap.put(listDataHeader.get(3), Porto);
        listHashMap.put(listDataHeader.get(4), VianadoCastelo);



    }

    }




