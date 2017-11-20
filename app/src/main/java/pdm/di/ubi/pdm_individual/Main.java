package pdm.di.ubi.pdm_individual;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Main extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    Button b_check;
    ConnectionDetector oCd;
    private TextView tvData;
    public DBAuxiliar oDBAux = new DBAuxiliar(this);
    private SQLiteDatabase oSQLiteDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        /** nav bar **/
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToogle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /** fim da nav bar **/

        //check connection teste
        b_check = (Button) findViewById(R.id.b_check);
        oCd = new ConnectionDetector(this);
        b_check.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                if(oCd.isConnected()){
                    Toast.makeText(Main.this, "Connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Main.this, "Not Connected!", Toast.LENGTH_SHORT).show();
                }
            }


        });
        //fim do botao teste check


        Button btnHit = (Button) findViewById(R.id.btnHit);
        //ctrl+alt+shift+t field
        tvData = (TextView) findViewById(R.id.tvJsonItem);


        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONTask().execute("http://www.praiafluvial.pt/wp-json/wp/v2/posts?per_page=100&filter[orderby]=date&order=desc");
            }
        });

    }


    @Override/**Funcao para nav bar**/
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToogle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class JSONTask extends AsyncTask<String, String, ArrayList<Posts> >{


        ArrayList<Posts> aPosts = new ArrayList<Posts>();
        ArrayList<Posts> aPosts33 = new ArrayList<Posts>();

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
 *              Falta tratar das coordenadas e das img
 *
                Coordinates = coordinates;
                Img = img;
           **/
                JSONArray jsonArray = new JSONArray(buffer.toString());
                String jsonArraySize="";
                jsonArraySize = String.valueOf(jsonArray.length());
                int x = 0;
                int y=0;
                for(int i=0; i<jsonArray.length(); i++) {
                    Posts oPosts = new Posts();
                    JSONObject oJsonObject = jsonArray.getJSONObject(i);

                    JSONObject oJsonObjectContent = oJsonObject.getJSONObject("content");
                    String content = oJsonObjectContent.getString("rendered");

                    JSONObject oJsonTitle = oJsonObject.getJSONObject("title");
                    String title = oJsonTitle.getString("rendered");

                    String date = oJsonObject.getString("date");
                    String slug = oJsonObject.getString("slug");

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


                   // System.out.println("POST FULL: " + "TITLE: " + title + "DATE: " + date + "SLUG: " + slug + "Categories: " + categories + "Id: " + id + "Excerpt: " + excerpt + "Content: " + content);

                    oPosts.setCategorie(categories);
                    oPosts.setContent(content);
                    oPosts.setExcerpt(excerpt);
                    oPosts.setDate(date);
                    oPosts.setId(id);
                    oPosts.setSlugpk(slug);
                    oPosts.setTitle(title);
                    oPosts.setCoordinates("");
                    oPosts.setImg("");

                    boolean b = false;
                   // if(categories!=33)
                      b = aPosts.add(oPosts);

                    if(b==false/** && categories!=33 **/)
                        System.out.println("Erro Insert");
                    y++;
                }

                System.out.println(aPosts.toString());
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
            System.out.println("LEAVEING FUNC");
                if(flag == true)
                    System.out.println("A MIRACLE HAPPENNED!!!!");
                if(flag== false)
                    System.out.println("Works fine (chrash)");



        }
    }


    public void startActivity (View v){

        Intent iActvity = new Intent(this, Activity2.class);

        //execute("http://www.praiafluvial.pt/wp-json/wp/v2/posts?per_page=1")
       // JSONTask jk = new JSONTask();
        //String result = "ui e isto passa?";
        //String result = jk.doInBackground("http://www.praiafluvial.pt/wp-json/wp/v2/posts?per_page=1");



        iActvity.putExtra("string1","temos a burra nas coves");
        startActivity(iActvity);
    }
}
