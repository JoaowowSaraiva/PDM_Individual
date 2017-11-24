
package pdm.di.ubi.pdm_individual;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends AppCompatActivity {
//https://www.youtube.com/watch?v=j-3L3CgYXkU

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    ConnectionDetector oCd;
    private DBAuxiliar oDBAux;
    private SQLiteDatabase oSQLiteDB;
    private ImageButton teste;

    //teste expandable listview
    private ExpandableListView eListView;
    private ExpandableListAdapter oListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        oDBAux = new DBAuxiliar(this);



        eListView = (ExpandableListView) findViewById(R.id.elvTeste);
        putInitData();
        oListAdapter = new ExpandableListAdapter(this, listDataHeader, listHashMap);
        eListView.setAdapter(oListAdapter);

        eListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                listDataHeader.get(groupPosition);
                String x = oListAdapter.getChild(groupPosition, childPosition).toString();

                Toast.makeText(Main.this, x, Toast.LENGTH_SHORT).show();

                Intent iActvity = new Intent(getApplicationContext(), FullPostActivity.class);
                iActvity.putExtra("title", x);
                startActivity(iActvity);

                return true;

            }


        });



        //check connection teste
        oCd = new ConnectionDetector(this);

        //como fazer o else?
          if(oCd.isConnected())
           new JSONTask().execute("http://www.praiafluvial.pt/wp-json/wp/v2/posts?per_page=100&filter[orderby]=date&order=desc");






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


    /**Funcao para nav bar primeira**/
    /**
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToogle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

**/

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

        Aveiro = oDBAux.getTitlesFromCategorieandTitle(34); // posts de aveiro


        List<String> Braga = new ArrayList<>();
        Braga = oDBAux.getTitlesFromCategorieandTitle(9);

        List<String> Bragança = new ArrayList<>();
        Bragança = oDBAux.getTitlesFromCategorieandTitle(37);

        List<String> Porto = new ArrayList<>();
        Porto = oDBAux.getTitlesFromCategorieandTitle(11);


        List<String> VianadoCastelo = new ArrayList<>();
        VianadoCastelo = oDBAux.getTitlesFromCategorieandTitle(36);

        List<String> VilaReal = new ArrayList<>();
        VilaReal = oDBAux.getTitlesFromCategorieandTitle(38);

        List<String> Viseu = new ArrayList<>();
        Viseu = oDBAux.getTitlesFromCategorieandTitle(39);

        listHashMap.put(listDataHeader.get(0), Aveiro);
        listHashMap.put(listDataHeader.get(1), Braga);
        listHashMap.put(listDataHeader.get(2), Bragança);
        listHashMap.put(listDataHeader.get(3), Porto);
        listHashMap.put(listDataHeader.get(4), VianadoCastelo);
        listHashMap.put(listDataHeader.get(5), VilaReal);
        listHashMap.put(listDataHeader.get(6), Viseu);



    }


    public void startNewActivity2 (View v){

        Intent iActvity = new Intent(this, Postdisplay.class);
        iActvity.putExtra("string1","Aqui vao os args");
        startActivity(iActvity);
    }

    public void testShare(View v){
        Intent intent = this.getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
        if(intent == null)
            Toast.makeText(this, "Ups!", Toast.LENGTH_SHORT).show();
        else {
            Intent iFaceShare = new Intent(Intent.ACTION_SEND);
            iFaceShare.setType("text/plain");
            iFaceShare.putExtra(Intent.EXTRA_TEXT, "Teste from app");
            startActivity(Intent.createChooser(iFaceShare, "Title for the dialog"));

        }
    }

    public void startNorthenRiverBeachesActivity (View v){

        Intent iActivty = new Intent (this, NorthenRiverBeaches.class);
        startActivity(iActivty);
    }

    public void startCenterRiverBeachesActivity (View v){

        Intent iActivty = new Intent (this, CenterRiverBeaches.class);
        startActivity(iActivty);
    }


    public void startSouthRiverBeachesActivity(View v){

        Intent iActivity = new Intent (this, SouthRiverBeaches.class);
        startActivity(iActivity);

    }

    public void startAcoresRiverBeachesActivity(View v){
        Intent iActivity = new Intent (this, AcoresRiverBeaches.class);
        startActivity(iActivity);
    }

    public void startMadeiraRiverBeachesActivity(View v){
        Intent iActivity = new Intent (this, MadeiraRiverBeaches.class);
        startActivity(iActivity);
    }

    public void startHighlighsRivverBeachesActivity(View v){

        Intent iActivity = new Intent(this, HighlightsRiverBeaches.class);
        startActivity(iActivity);
    }


    class JSONTask extends AsyncTask<String, String, ArrayList<Posts> >{


        ArrayList<Posts> aPosts = new ArrayList<Posts>();

        @Override
        protected ArrayList<Posts> doInBackground(String... params) {

            HttpURLConnection connection=null;
            BufferedReader reader = null;

            try {

                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line ="";
                while( (line = reader.readLine()) !=null ){
                    buffer.append(line);
                }


                /**
                 *
                 *  Falta tratar das img e dos videos!
                 */
                JSONArray jsonArray = new JSONArray(buffer.toString());
                String jsonArraySize="";
                jsonArraySize = String.valueOf(jsonArray.length());

                int x = 0;
                int y=0;
                String coordinatesURL ="";
                Aux aux2 = new Aux();


                //fazer a verificação de posts novos. Secalhar até ja esta! xD
                for(int i=0; i<jsonArray.length(); i++) {
                    Posts oPosts = new Posts();
                    JSONObject oJsonObject = jsonArray.getJSONObject(i);

                    JSONObject oJsonObjectContent = oJsonObject.getJSONObject("content");
                    String content = oJsonObjectContent.getString("rendered");

                    coordinatesURL = aux2.getCoordinatesURL(content);


                    JSONObject oJsonTitle = oJsonObject.getJSONObject("title");
                    String title = oJsonTitle.getString("rendered");

                    String date = oJsonObject.getString("date");

                    JSONObject oJsonExcerpt = oJsonObject.getJSONObject("excerpt");
                    String excerpt = oJsonExcerpt.getString("rendered");


                    JSONArray oJsonArrayCategories = oJsonObject.getJSONArray("categories");
                    int categories = oJsonArrayCategories.getInt(0);
                    int id = oJsonObject.getInt("id");


                    //parsing
                    Aux oParsing = new Aux();
                    content = oParsing.parseContent(content);
                    date = oParsing.parseDate(date);
                    excerpt = oParsing.parseExcerpt(excerpt);
                    title = title.replace("&#8211;", "-"); //testing

                    oPosts.setCategorie(categories);
                    oPosts.setContent(content);
                    oPosts.setExcerpt(excerpt);
                    oPosts.setDate(date);
                    oPosts.setId(id);
                    oPosts.setTitle(title);
                    oPosts.setCoordinates(coordinatesURL);
                    oPosts.setImg(null);

                    boolean b = false;

                        b = aPosts.add(oPosts);

                    if(b==false)
                        System.out.println("Erro Insert");
                    y++;
                    System.out.println(content);
                }

              //  System.out.println("URLCOORDENADAS: " + coordinatesURL);
              //  System.out.println(aPosts.toString())
                System.out.println("Yaqui:fim " + y);


                return aPosts;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } finally {
                if(connection != null)
                    connection.disconnect();

                try {
                    if(reader != null)
                        reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;

        }
        //ja n devo precisar disto
        @Override
        protected void onPostExecute(ArrayList<Posts> result) {
            super.onPostExecute(result);


            boolean flag=false;


            System.out.println("EXECUTING onPostExecute!");
            flag = oDBAux.insertArrayPosts(result);
            System.out.println("LEAVEVING FUNC");
            if(flag == true)
                System.out.println("A MIRACLE HAPPENNED!!!!");
            if(flag== false)
                System.out.println("Works fine (crash!)");
        }

/**
        public byte[] getImgFromUrl (String url) throws IOException {

            URL imageUrl = new URL(url);
            URLConnection urlConnection = imageUrl.openConnection();

            InputStream is = urlConnection.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            ByteArrayOutputStream baf = new ByteArrayOutputStream();
            byte[] data = new byte[500];
            int current = 0;

            while ((current = bis.read()) != -1) {
                //baf.append((byte) current);
                baf.write(data, 0, current);

            }

            return baf.toByteArray();




        }

**/
    }

}

