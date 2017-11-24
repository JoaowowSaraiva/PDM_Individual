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
 * Created by saraiva on 24-11-2017.
 */

public class HighlightsRiverBeaches extends Activity {

    private WebView oWV;
    private ExpandableListView eListView;
    private ExpandableListAdapter oListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;
    private DBAuxiliar oDBAux= new DBAuxiliar(this);



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.highlighs_layout);


        eListView = (ExpandableListView) findViewById(R.id.elvDestaques);
        putInitData();
        oListAdapter = new ExpandableListAdapter(this,listDataHeader, listHashMap);
        eListView.setAdapter(oListAdapter);

        eListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){

            @Override
            public boolean onChildClick (ExpandableListView parent, View v, int groupPosition, int childPosition, long id){
                listDataHeader.get(groupPosition);
                String x= oListAdapter.getChild(groupPosition, childPosition).toString();

                Toast.makeText(HighlightsRiverBeaches.this, x, Toast.LENGTH_SHORT).show();

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

        listDataHeader.add("Destaques");

        List<String> Destaques = oDBAux.getTitlesFromCategorieandTitle(33);
        listHashMap.put(listDataHeader.get(0), Destaques);





    }

}
