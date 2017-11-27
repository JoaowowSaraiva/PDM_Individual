
package pdm.di.ubi.pdm_individual;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


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
import java.util.List;

public class Main extends AppCompatActivity {

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
    private ListView oLV;
    Cursor oCursor = null;
    ArrayAdapter<String> itemAdapter, itemAdapter2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        oDBAux=new DBAuxiliar(this);
        oSQLiteDB = oDBAux.getReadableDatabase();


        oLV = (ListView) findViewById(R.id.listviewID);


        ArrayList<String> titles = oDBAux.getLastestsTitlePosts();



        itemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, titles);

        oLV.setAdapter(itemAdapter);

        oLV.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String title = String.valueOf(adapterView.getItemAtPosition(i));

                        Intent iActvity = new Intent(getApplicationContext(), FullPostActivity.class);
                        iActvity.putExtra("title",title);
                        startActivity(iActvity);

                    }
                }

        );



        //check connection teste
        oCd = new ConnectionDetector(this);
        //como fazer o else?
          if(oCd.isConnected())
           new JSONTask().execute("http://www.praiafluvial.pt/wp-json/wp/v2/posts?per_page=100&filter[orderby]=date&order=desc");



    }

    public static Intent getOpenFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);

        try{
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if(applicationInfo.enabled) {

                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
            }catch(PackageManager.NameNotFoundException ignored){
            }

        return new Intent(Intent.ACTION_VIEW, uri);

    }

    public void startFB (View v){

        startActivity(getOpenFacebookIntent(getPackageManager(), "https://www.facebook.com/pequenosparaisospt/"));
    }

    public void startInstagram (View v){

        Uri uri = Uri.parse("https://www.instagram.com/praias_fluviais/");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/praias_fluviais/")));
        }

    }

    //abre o intent para abrir o canal de youtube do site, se ñ existir a app abre no browser
    public void startYoutube(View v){
        String url = "https://www.youtube.com/channel/UCFddNBlKlyYfpSj_YgDwjFw";

        Intent intent=null;
        try {
            intent =new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }

    }

    //começa a activity para enviar um email, se não existir nenhuma app q consiga resolver este intent envia um toast ao user
    public void startSendMail (View v){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"praiasfluviaispt@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Assunto..");
        i.putExtra(Intent.EXTRA_TEXT   , "Ola! ...");
        try {
            startActivity(Intent.createChooser(i, "Enviar email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Main.this, "Não existem aplicações instaladas para enviar emails!.", Toast.LENGTH_SHORT).show();
        }

    }

/** nav menu **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    /** nav menu operaçoes **/
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
            case R.id.PraiasFluviaisSul: {;
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
                finish();
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //Uma das classes mais fundamentais desta app
    //TaskAsyncrona para tratar da url que contem as informações do site em formato Json, esta thread recebe uma string e devolve um Array de Posts
    class JSONTask extends AsyncTask<String, String, ArrayList<Posts> >{

        ArrayList<Posts> aPosts = new ArrayList<Posts>();

        @Override
        protected ArrayList<Posts> doInBackground(String... params) {

            HttpURLConnection connection=null;
            BufferedReader reader = null;

            try {
                //leitura da info vinda do URL passado em parametro
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
                 *  Falta tratar das img e dos videos!
                 */
                JSONArray jsonArray = new JSONArray(buffer.toString());
                String jsonArraySize="";
                jsonArraySize = String.valueOf(jsonArray.length());

                String coordinatesURL ="";
                Aux aux2 = new Aux();


                //lê ou Re lê os posts se encontrar algum novo vai add e ignora os velhos
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
                    title = title.replace("&#8211;", "-");

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
                        Log.e("ERROR:", "INSERT POST FAILED!");


                }


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
        @Override
        protected void onPostExecute(ArrayList<Posts> result) {
            super.onPostExecute(result);

            boolean flag=false;

            flag = oDBAux.insertArrayPosts(result);

            if(flag == true)
                Log.i("Check", "All post inserted");
            if(flag== false)
                Log.e("Error", "Probably missing internet connecting!");
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

