package pdm.di.ubi.pdm_individual;

import android.content.ContentValues;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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


    class JSONTask extends AsyncTask<String, String, String >{


        @Override
        protected String doInBackground(String... params) {

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

                XmlPullParser xpp;
                XmlPullParserFactory factory;

                factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                xpp = factory.newPullParser();


                JSONArray json = new JSONArray(buffer.toString());


                JSONObject e = json.getJSONObject(0);


                String teste2 = e.getString("title");

                JSONObject e2 = e.getJSONObject("content");

                String teste3 = e2.getString("rendered");
                String teste="";
                teste = String.valueOf(json.length());

                teste3 = teste3.replaceAll("<script.*</script>", "");
                teste3 = teste3.replaceAll("<!--.*-->", "");
                String sWithProblems = "<p style=" + '"' + "text-align: justify;" + '"' + ">";
                String sWithProblems2 = "<h2 style=" + '"' + "text-align: justify;" + '"' + ">";
                String sWithProblems3 = "<h3 style=" + '"' + "text-align: justify;" + '"' + ">";

                String para_teste = "<!--Ola mundo nos estamos aqui http://quickadsense.com/ -->";

                para_teste = para_teste.replaceAll("<!--.*-->", "");
                System.out.println("PARA_TESTE: " + para_teste);

                //teste3 = teste3.replace(sWithProblems, "");
                //teste3 = teste3.replace(sWithProblems2, "");
                //teste3 = teste3.replace(sWithProblems3, "");
              //  teste3 = teste3.replace("</p>" , "");
               // teste3 = teste3.replace("</h3>", "");
                //teste3 = teste3.replace("</h2>", "");
                //teste3 = teste3.replace("</script>", "");
                //teste3 = teste3.replace("</div>", "");
                //https://stackoverflow.com/questions/11255353/java-best-way-to-grab-all-strings-between-two-strings-regex

                System.out.println(teste3);


                String nova = new String ();
                nova = teste3.replace("&hellip;", "..." ); //unicode &#8230; for ...
                //<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                //nova = teste3.replace("<script async src=" + '"' + "//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js" + '"' +"</script>" , "");

                nova = nova.replace("(adsbygoogle = window.adsbygoogle || []).push({});", "");

                xpp.setInput(new StringReader(nova));
                int eventType = xpp.getEventType();

                String resultado = new String();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_DOCUMENT) {
                        System.out.println("");
                    } else if(eventType == XmlPullParser.START_TAG) {
                        System.out.println("");
                    } else if(eventType == XmlPullParser.END_TAG) {
                        System.out.println("");
                    } else if(eventType == XmlPullParser.TEXT) {
                        System.out.println("Text "+xpp.getText());

                        resultado = resultado + xpp.getText();
                        //temos q fazer concat na string para ler todas as tags
                    }
                    eventType = xpp.next();
                }
                System.out.println("End document");
                System.out.println("RESULTADOOO: " + resultado);


                return resultado;


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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tvData.setText(result);

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
