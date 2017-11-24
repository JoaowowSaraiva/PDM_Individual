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

/**
 * Castelo Branco:12
 * Coimbra:14
 * Guarda:10
 * Leiria:18
 * Portalegre:20
 * Santarem:13
 */

public class CenterRiverBeaches extends Activity {



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
        oWV.loadData("<iframe src=\"https://www.google.com/maps/d/embed?mid=1D7X0gLTh1FBibYypH8VmJ5715f8\" width=\"640\" height=\"480\"></iframe>", "text/html", null);



        eListView = (ExpandableListView) findViewById(R.id.expandlvNorte);
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
