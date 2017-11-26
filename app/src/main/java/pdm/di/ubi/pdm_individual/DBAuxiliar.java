package pdm.di.ubi.pdm_individual;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;
import java.util.ArrayList;

public class DBAuxiliar extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Maravilha" ;
    //tabela dos posts
    protected static final String TABLE_POSTS = "Posts" ;
    protected static final String IDPOST   = "Id";
    protected static final String CONTENT = "Content" ;
    protected static final String TITLE = "Title" ;
    protected static final String COORDINATES = "Coordinates" ;
    protected static final String IMG = "Img";
    protected static final String DATE = "Date";
    protected static final String EXCERPT = "Excerpt";
    protected static final String CATEGORIE = "Categorie";

    //protected static final Blob COLUMN5 = null;


    //tabela auxiliar
    protected static final String TABLE_AUX = " TabelaAuxiliar ";
    protected static final String ID_TABELA = "Id";
    protected static final String JSON_SIZE = "Jsonsize";


    protected static final String POSTS_CREATE_TABLE = "" +
            "CREATE TABLE " + TABLE_POSTS + " (" +
            IDPOST + " INT PRIMARY KEY, " +
            CONTENT + " TEXT, " +
            TITLE + " VARCHAR(255), " +
            COORDINATES + " TEXT, " +
            CATEGORIE + " INT, " +
            IMG + " BLOB, " +
            EXCERPT + " TEXT, " +
            DATE + " TEXT );" ;


    DBAuxiliar(Context context){
          super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(POSTS_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    //inserir na db este array de posts
    boolean insertArrayPosts (ArrayList<Posts> oPosts){
        System.out.println("WE ARRIVED insertArrayPosts");
        SQLiteDatabase oSQLiteDB = this.getWritableDatabase();

        for(int i =0; i<oPosts.size();i++){
            ContentValues oCValues = new ContentValues();
                if(this.checkExistsPost(oPosts.get(i).getId())==true)
                    continue;
            oCValues.put(CONTENT, oPosts.get(i).getContent());
            oCValues.put(TITLE, oPosts.get(i).getTitle());
            oCValues.put(IDPOST, oPosts.get(i).getId());
            oCValues.put(DATE, oPosts.get(i).getDate());
            oCValues.put(EXCERPT, oPosts.get(i).getExcerpt());
            oCValues.put(CATEGORIE, oPosts.get(i).getCategorie());
            oCValues.put(COORDINATES, oPosts.get(i).getCoordinates());
            oCValues.put(IMG, "");

            long flag=0;
           flag = oSQLiteDB.insert(TABLE_POSTS, null, oCValues);
                if(flag==-1) {
                   // SQLiteOpenHelper:close();
                   // oSQLiteDB.close();
                    return false;
                }
        }

       // oSQLiteDB.close();
        //SQLiteOpenHelper:close();
        return true;
    }

    //true = existe post
    //false = n existe
   public boolean checkExistsPost (int id){

        SQLiteDatabase oSQLiteDB = this.getReadableDatabase();
        Cursor oCursor = null;
        oCursor = oSQLiteDB.rawQuery("SELECT *"  + " FROM " + TABLE_POSTS + " WHERE " + id + "=" + IDPOST, null);
        oCursor.moveToFirst();

        int a = oCursor.getCount();

        if(a>0) {
            //oSQLiteDB.close();
            //SQLiteOpenHelper:close();
            return true;
        }
       //oSQLiteDB.close();
       //SQLiteOpenHelper:close();
        return false;
    }

    //funcao que retorna os titulos de uma categoria
    public ArrayList<String> getTitlesFromCategorieandTitle( int id){
        ArrayList result = new ArrayList<String>();

        SQLiteDatabase oSQLiteDB;
        oSQLiteDB = this.getReadableDatabase();

        Cursor oCursor = null;
        oCursor = oSQLiteDB.rawQuery("SELECT " + TITLE + " FROM " + TABLE_POSTS + " WHERE " + CATEGORIE + "=" + id, null );
        oCursor.moveToFirst();


        while(!oCursor.isAfterLast()){
            result.add(oCursor.getString(0).toString());
            oCursor.moveToNext();
        }

       // oSQLiteDB.close();
       // SQLiteOpenHelper:close();
        return result;
    }

    //funcao que poderá ser usada para a parte das img (not used yet)
    public int getIdPostFromTitle(String title){
        int id =0;

        SQLiteDatabase oSQLiteDB = this.getReadableDatabase();

        Cursor oCursor = null;
        oCursor = oSQLiteDB.rawQuery("SELECT " + IDPOST + " FROM " + TABLE_POSTS + " WHERE " + "'" + title + "'" + "=" + TITLE, null);
        oCursor.moveToFirst();

        id = Integer.parseInt(oCursor.getString(0).toString());

       // oSQLiteDB.close();
       // SQLiteOpenHelper:close();

        return id;
    }


    //retorna os 10 posts mais recentes para mostrar na pagina inicial
    public ArrayList<String> getLastestsTitlePosts (){
        ArrayList<String> aPostTitle = new ArrayList<String>();
        SQLiteDatabase oSQLiteDB = this.getReadableDatabase();
        Cursor oCursor = null;

        oCursor = oSQLiteDB.rawQuery("SELECT " +TITLE + " FROM " + TABLE_POSTS + " LIMIT 10",null);
        oCursor.moveToFirst();

        while(!oCursor.isAfterLast()){
            String postTitle="";

            postTitle = oCursor.getString(0).toString();
            aPostTitle.add(postTitle);

            oCursor.moveToNext();
        }
        //SQLiteOpenHelper:close();
        //oSQLiteDB.close();
        return aPostTitle;
    }


}