
package pdm.di.ubi.pdm_individual;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
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
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    ConnectionDetector oCd;
    private DBAuxiliar oDBAux;
    private SQLiteDatabase oSQLiteDB;

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
        oListAdapter = new ExpandableListAdapter(this,listDataHeader, listHashMap);
        eListView.setAdapter(oListAdapter);

        eListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){

            @Override
            public boolean onChildClick (ExpandableListView parent, View v, int groupPosition, int childPosition, long id){
                String teste20 = String.valueOf(parent.getItemAtPosition(groupPosition));

                listDataHeader.get(groupPosition);

               String x= oListAdapter.getChild(groupPosition, childPosition).toString();

                //String title = String.valueOf(adapterView.getItemAtPosition(i));
                Toast.makeText(Main.this, x, Toast.LENGTH_SHORT).show();
                return true;

            }


        });



        /** nav bar **/
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToogle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /** fim da nav bar **/

        //check connection teste
        oCd = new ConnectionDetector(this);

        //como fazer o else?
        if(oCd.isConnected())
            new JSONTask().execute("http://www.praiafluvial.pt/wp-json/wp/v2/posts?per_page=100&filter[orderby]=date&order=desc");







        WebView webView = (WebView) findViewById(R.id.wvMaps);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData("<iframe src=\"https://www.google.com/maps/d/embed?mid=1D7X0gLTh1FBibYypH8VmJ5715f8\" width=\"640\" height=\"480\"></iframe>", "text/html", null);
//https://www.youtube.com/watch?v=7IgV5e6vczQ

    }


    @Override/**Funcao para nav bar**/
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToogle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void putInitData(){
        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        listDataHeader.add("Teste1");
        listDataHeader.add("Teste2");
        listDataHeader.add("Teste3");
        listDataHeader.add("Teste4");

        List<String> Teste1 = new ArrayList<>();
        Teste1.add("Sub item de Teste1");

        List<String> Teste2 = new ArrayList<>();
        Teste2.add("sub item do teste2 - 1");
        Teste2.add("Sub item do teste2 - 2");


        List<String> Teste3 = new ArrayList<>();
        Teste3.add("sub item do teste3 - 1");
        Teste3.add("Sub item do teste3 - 2");
        Teste3.add("Mais uma so para que sim");

        List<String> Teste4 = new ArrayList<>();
        Teste4.add("AYYAAYYUUIII");
        Teste4.add("UIUIUIU AIII");



        listHashMap.put(listDataHeader.get(0), Teste1);
        listHashMap.put(listDataHeader.get(1), Teste2);
        listHashMap.put(listDataHeader.get(2), Teste3);
        listHashMap.put(listDataHeader.get(3), Teste4);



    }


    public void startNewActivity2 (View v){

        Intent iActvity = new Intent(this, Postdisplay.class);
        iActvity.putExtra("string1","Aqui vao os args");
        startActivity(iActvity);
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

