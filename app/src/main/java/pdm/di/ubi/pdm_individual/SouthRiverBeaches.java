package pdm.di.ubi.pdm_individual;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * BEJA:17
 * EVORA:35
 * FARO:16
 * SETUBAL:32
 */

public class SouthRiverBeaches extends Activity {



    private WebView oWV;
    private ExpandableListView eListView;
    private ExpandableListAdapter oListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;
    private DBAuxiliar oDBAux= new DBAuxiliar(this);



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nrb_layout);



        oWV = (WebView) findViewById(R.id.wvMaps);
        oWV.getSettings().setJavaScriptEnabled(true);
        oWV.loadData("<iframe src=\"https://www.google.com/maps/d/embed?mid=1zPFE2SRYK14f_MiYj9zK9oLngzk\" width=\"640\" height=\"480\"></iframe>", "text/html", null);



        eListView = (ExpandableListView) findViewById(R.id.expandlvNorte);
        putInitData();
        oListAdapter = new ExpandableListAdapter(this,listDataHeader, listHashMap);
        eListView.setAdapter(oListAdapter);

        eListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){

            @Override
            public boolean onChildClick (ExpandableListView parent, View v, int groupPosition, int childPosition, long id){
                listDataHeader.get(groupPosition);
                String x= oListAdapter.getChild(groupPosition, childPosition).toString();

                Toast.makeText(SouthRiverBeaches.this, x, Toast.LENGTH_SHORT).show();

                Intent iActvity = new Intent(getApplicationContext(), FullPostActivity.class);
                iActvity.putExtra("title",x);
                startActivity(iActvity);

                return true;

            }


        });


    }



    public void putInitData(){
        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        listDataHeader.add("Beja");
        listDataHeader.add("Evora");
        listDataHeader.add("Faro");
        listDataHeader.add("Setubal");


        List<String> Beja = oDBAux.getTitlesFromCategorieandTitle(17);

        List<String> Evora = oDBAux.getTitlesFromCategorieandTitle(35);

        List<String> Faro = oDBAux.getTitlesFromCategorieandTitle(16);

        List<String> Setubal = oDBAux.getTitlesFromCategorieandTitle(32);



        listHashMap.put(listDataHeader.get(0), Beja);
        listHashMap.put(listDataHeader.get(1), Evora);
        listHashMap.put(listDataHeader.get(2), Faro);
        listHashMap.put(listDataHeader.get(3), Setubal);




    }

}


