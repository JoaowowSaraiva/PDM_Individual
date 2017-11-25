package pdm.di.ubi.pdm_individual;

import android.app.Activity;
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
 * Castelo Branco:12
 * Coimbra:14
 * Guarda:10
 * Leiria:18
 * Portalegre:20
 * Santarem:13
 */

public class CenterRiverBeaches extends AppCompatActivity {



    private WebView oWV;
    private ExpandableListView eListView;
    private ExpandableListAdapter oListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;
    private DBAuxiliar oDBAux= new DBAuxiliar(this);



    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.crb_layout);


        ConnectionDetector oCd = new ConnectionDetector(this);
        if(oCd.isConnected()) {
            oWV = (WebView) findViewById(R.id.webView);
            oWV.getSettings().setJavaScriptEnabled(true);
            oWV.loadData("<iframe src=\"https://www.google.com/maps/d/embed?mid=1D7X0gLTh1FBibYypH8VmJ5715f8\" width=\"640\" height=\"480\"></iframe>", "text/html", null);
        }
        else{
            oWV = (WebView) findViewById(R.id.webView);
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
                Toast.makeText(CenterRiverBeaches.this, x, Toast.LENGTH_SHORT).show();
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

        listDataHeader.add("Castelo Branco:");
        listDataHeader.add("Coimbra");
        listDataHeader.add("Guarda");
        listDataHeader.add("Leiria");
        listDataHeader.add("Portalegre");
        listDataHeader.add("Santarem");

        List<String> CasteloBrando = oDBAux.getTitlesFromCategorieandTitle(12);

        List<String> Coimbra = oDBAux.getTitlesFromCategorieandTitle(14);

        List<String> Guarda = oDBAux.getTitlesFromCategorieandTitle(10);

        List<String> Leiria = oDBAux.getTitlesFromCategorieandTitle(18);

        List<String> Portalere = oDBAux.getTitlesFromCategorieandTitle(20);

        List<String> Santarem = oDBAux.getTitlesFromCategorieandTitle(13);

        listHashMap.put(listDataHeader.get(0), CasteloBrando);
        listHashMap.put(listDataHeader.get(1), Coimbra);
        listHashMap.put(listDataHeader.get(2), Guarda);
        listHashMap.put(listDataHeader.get(3), Leiria);
        listHashMap.put(listDataHeader.get(4), Portalere);
        listHashMap.put(listDataHeader.get(5), Santarem);



    }

}
