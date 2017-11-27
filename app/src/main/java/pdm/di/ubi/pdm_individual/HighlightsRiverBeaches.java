package pdm.di.ubi.pdm_individual;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class que trata dos Posts da Categoria 33 ( Destaques )
 */

public class HighlightsRiverBeaches extends AppCompatActivity {


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
                //x = ao titulo
                String x= oListAdapter.getChild(groupPosition, childPosition).toString();

                Toast.makeText(HighlightsRiverBeaches.this, x, Toast.LENGTH_SHORT).show();

                Intent iActvity = new Intent(getApplicationContext(), FullPostActivity.class);
                iActvity.putExtra("title",x);
                startActivity(iActvity);

                return true;

            }


        });


    }


//preenche a lista e os seus filhos com informação
    public void putInitData(){
        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        listDataHeader.add("Destaques");
        //funcao que retorna Os titutlos da categoria
        List<String> Destaques = oDBAux.getTitlesFromCategorieandTitle(33);
        listHashMap.put(listDataHeader.get(0), Destaques);

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
            case R.id.Exit:{
                Intent iActivity8 = new Intent(Intent.ACTION_MAIN);
                iActivity8.addCategory(Intent.CATEGORY_HOME);
                iActivity8.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(iActivity8);
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
