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
    private DBAuxiliar oDBAux= new DBAuxiliar(this);
    private SQLiteDatabase oSQLiteDB;



    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nrb_layout);


        ConnectionDetector oCd = new ConnectionDetector(this);

        if(oCd.isConnected()) {
            oWV = (WebView) findViewById(R.id.wvMaps);
            oWV.getSettings().setJavaScriptEnabled(true);
            oWV.loadData("<iframe src=\"https://www.google.com/maps/d/embed?mid=1D7X0gLTh1FBibYypH8VmJ5715f8\" width=\"640\" height=\"480\"></iframe>", "text/html", null);
        }
        else{
            oWV = (WebView) findViewById(R.id.wvMaps);
            oWV.getSettings().setJavaScriptEnabled(true);
            oWV.loadData("Sem connecção a Internet para exibir o mapa!", "text/plain", null);
        }

        eListView = (ExpandableListView) findViewById(R.id.expandableListView);
        putInitData();
        oListAdapter = new ExpandableListAdapter(this,listDataHeader, listHashMap);
        eListView.setAdapter(oListAdapter);

        eListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){

            @Override
            public boolean onChildClick (ExpandableListView parent, View v, int groupPosition, int childPosition, long id){

                listDataHeader.get(groupPosition);

                String x= oListAdapter.getChild(groupPosition, childPosition).toString();

                Intent iActvity = new Intent(getApplicationContext(), FullPostActivity.class);
                iActvity.putExtra("title",x);
                startActivity(iActvity);

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

        List<String> Aveiro = oDBAux.getTitlesFromCategorieandTitle(34);

        List<String> Braga = oDBAux.getTitlesFromCategorieandTitle(9);

        List<String> Bragança = oDBAux.getTitlesFromCategorieandTitle(37);

        List<String> Porto = oDBAux.getTitlesFromCategorieandTitle(11);

        List<String> VianadoCastelo = oDBAux.getTitlesFromCategorieandTitle(36);

        List<String> VilaReal = oDBAux.getTitlesFromCategorieandTitle(38);

        List<String> Viseu = oDBAux.getTitlesFromCategorieandTitle(39);

        listHashMap.put(listDataHeader.get(0), Aveiro);
        listHashMap.put(listDataHeader.get(1), Braga);
        listHashMap.put(listDataHeader.get(2), Bragança);
        listHashMap.put(listDataHeader.get(3), Porto);
        listHashMap.put(listDataHeader.get(4), VianadoCastelo);
        listHashMap.put(listDataHeader.get(5), VilaReal);
        listHashMap.put(listDataHeader.get(6), Viseu);


    }

    }





