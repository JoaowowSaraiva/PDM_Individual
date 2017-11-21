
package pdm.di.ubi.pdm_individual;

import android.content.Intent;
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
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    private DBAuxiliar oDBAux;
    private SQLiteDatabase oSQLiteDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        oDBAux = new DBAuxiliar(this);


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
/**

        JSONTask jk = new JSONTask();
        String img = "http://www.praiafluvial.pt/wp-content/uploads/2017/11/Praia-Fluvial-ana-de-aviz-3-2.jpg";
        byte[] b = new byte[500];
        try {
            b = jk.getImgFromUrl(img);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageView image = (ImageView) findViewById(R.id.imgid);
        Bitmap bMap = BitmapFactory.decodeByteArray(b, 0, b.length);
        image.setImageBitmap(bMap);

**/


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
                String coordinatesURL ="";
                Aux aux2 = new Aux();

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

