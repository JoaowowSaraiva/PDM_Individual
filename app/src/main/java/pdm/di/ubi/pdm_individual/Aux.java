package pdm.di.ubi.pdm_individual;

import android.database.sqlite.SQLiteDatabase;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by saraiva on 16-11-2017.
 */

public class Aux{

    public Aux() {
    }

    public String parseContent (String content) throws XmlPullParserException, IOException {

        XmlPullParser xpp;
        XmlPullParserFactory factory;

        factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        xpp = factory.newPullParser();

        content = content.replace("&nbsp;", " ");
        content = content.replace("&hellip;", "... " );//unicode &#8230; for ...
        content = content.replaceAll("<script.*</script>", "");
        content = content.replaceAll("<!--.*-->", "");
        content = content.replaceAll("<iframe.*</iframe>", "");

        content = content.replace("(adsbygoogle = window.adsbygoogle || []).push({});", "");
        content = content.replace("Onde ficar", "");
        content = content.replace("Onde Ficar" ,"");

        xpp.setInput(new StringReader(content));
        int eventType = xpp.getEventType();

        String content_result = new String();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.TEXT) {
                 content_result = content_result + xpp.getText();
            }
            eventType = xpp.next();
        }


        //parse á martelada!! dos contents
        content_result = content_result.replace("Booking.com", "");
        content_result = content_result.replace("    (function(d, sc, u) {", "");
        content_result = content_result.replace("      var s = d.createElement(sc), p = d.getElementsByTagName(sc)[0];", "");
        content_result = content_result.replace("      s.type = 'text/javascript';","");
        content_result = content_result.replace("      s.async = true;","");
        content_result = content_result.replace("      s.src = u + '?v=' + (+new Date());","");
        content_result = content_result.replace("      p.parentNode.insertBefore(s,p);","");
        content_result = content_result.replace("      })(document, 'script', '//aff.bstatic.com/static/affiliate_base/js/flexiproduct.js');","");

        return content_result;
    }


    public String parseExcerpt(String excerpt) throws XmlPullParserException, IOException {

        excerpt = excerpt.replace("&hellip;", "... ");
        excerpt = excerpt.replace("&nbsp;" , " ");
        XmlPullParser xpp;
        XmlPullParserFactory factory;

        factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        xpp = factory.newPullParser();

        xpp.setInput(new StringReader(excerpt));
        int eventType = xpp.getEventType();
        String excerpt_result = new String();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.TEXT) {;
                excerpt_result = excerpt_result + xpp.getText();
                //System.out.println(excerpt_result);
                //temos q fazer concat na string para ler todas as tags
            }
            eventType = xpp.next();
        }

        return excerpt_result;
    }

    String parseDate (String date){

        date = date.replace("T", " ");
        return date;

    }


    public String getCoordinatesURL (String content){
        String result="";
        Pattern pattern = Pattern.compile("<strong>Coordenadas GPS:<\\/strong> <a href=\"\\S*\"");
        Matcher matcher = pattern.matcher(content);

        if(matcher.find()){
            String strongTag = matcher.group(0);
            Pattern pattern1 = Pattern.compile("href=\"\\S*\"");
            Matcher matcher1 = pattern1.matcher(strongTag);
                if(matcher1.find()){
                    String hrefTag = matcher1.group(0);
                    result = hrefTag.replace("href=" + '"', "");
                    result = result.replace(""+'"',"");

                    return result;
                }
        }

        return "";
    }

   public String getIMGURL(String content){
        String imgURL="";
        Pattern pattern = Pattern.compile("<img.*/>");
        Matcher matcher = pattern.matcher(content);

        if(matcher.find()){
            String imgTag = matcher.group(0);
            Pattern pattern1 = Pattern.compile("src=\"\\S*\"");
            Matcher matcher1 = pattern1.matcher(imgTag);
                if(matcher1.find()){

                    String srcTag = matcher1.group(0);
                    imgURL = srcTag.replace("src=" + '"', "");
                    imgURL = imgURL.replace(""+'"',"");

                    return imgURL;

                }

        }

        return "empty";
    }



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



}








/** Links relativos ao trabalho que podem vir a dar jetio!

 https://stackoverflow.com/questions/11255353/java-best-way-to-grab-all-strings-between-two-strings-regex



 while (eventType != XmlPullParser.END_DOCUMENT) {
 if(eventType == XmlPullParser.TEXT) {
 System.out.println("");
 } else if(eventType == XmlPullParser.START_TAG) {
 System.out.println("");
 } else if(eventType == XmlPullParser.END_TAG) {
 System.out.println("");
 } else if(eventType == XmlPullParser.TEXT) {
 System.out.println("Text "+xpp.getText());

 content_result = content_result + xpp.getText();
 //temos q fazer concat na string para ler todas as tags
 }
 eventType = xpp.next();
 }


 **/

/**


 Intent intent = new Intent(Intent.ACTION_VIEW);
 intent.setData(Uri.parse("http://www.stackoverflow.com"));
 startActivity(intent);

 **/