package pdm.di.ubi.pdm_individual;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class que trata das praias com categoria 25
 * Ilha de são miguel:25
 */

public class AcoresRiverBeaches extends AppCompatActivity {


    private WebView oWV;
    private ExpandableListView eListView;
    private ExpandableListAdapter oListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;
    private DBAuxiliar oDBAux= new DBAuxiliar(this);



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.acores_layout);


        ConnectionDetector oCd = new ConnectionDetector(this);

        if(oCd.isConnected()) {
            oWV = (WebView) findViewById(R.id.wvMaps);
            oWV.getSettings().setJavaScriptEnabled(true);
            oWV.loadData("<iframe src=\"https://www.google.com/maps/d/embed?mid=1t4rWuZzPAElOAYuhd-BaEy6f3tQ\" width=\"640\" height=\"480\"></iframe>", "text/html", null);
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

                Toast.makeText(AcoresRiverBeaches.this, x, Toast.LENGTH_SHORT).show();

                Intent iActvity = new Intent(getApplicationContext(), FullPostActivity.class);
                iActvity.putExtra("title",x);
                startActivity(iActvity);

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
            }  case R.id.Exit:{
                Intent iActivity8 = new Intent(Intent.ACTION_MAIN);
                iActivity8.addCategory(Intent.CATEGORY_HOME);
                iActivity8.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(iActivity8);
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }
//funcao que preenche as listas com os dados
    public void putInitData(){
        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        listDataHeader.add("Ilha de São Miguel");

        //funcao que devolve os titulos da cat 25
        List<String> IlhadeSaoMiguel = oDBAux.getTitlesFromCategorieandTitle(25);
        listHashMap.put(listDataHeader.get(0), IlhadeSaoMiguel);
    }
}
