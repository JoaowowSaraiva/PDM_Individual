package pdm.di.ubi.pdm_individual;

import android.content.Intent;
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


        //vamos testar o json!!!!!

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

                JSONObject e2 = e.getJSONObject("excerpt");

                String teste3 = e2.getString("rendered");
                String teste="";
                teste = String.valueOf(json.length());


                String nova = new String ();
                nova = teste3.replace("&hellip;", "..." ); //unicode &#8230; for ...

                xpp.setInput(new StringReader(nova));
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_DOCUMENT) {
                        System.out.println("Start document");
                    } else if(eventType == XmlPullParser.START_TAG) {
                        System.out.println("Start tag "+xpp.getName());
                    } else if(eventType == XmlPullParser.END_TAG) {
                        System.out.println("End tag "+xpp.getName());
                    } else if(eventType == XmlPullParser.TEXT) {
                        System.out.println("Text "+xpp.getText());
                        return xpp.getText();
                        //temos q fazer concat na string para ler todas as tags
                    }
                    eventType = xpp.next();
                }
                System.out.println("End document");
                return "ups";
                //http://www.java2s.com/Code/Java/Development-Class/ReplacealltheoccurencesofHTMLescapestringswiththerespectivecharacters.htm

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