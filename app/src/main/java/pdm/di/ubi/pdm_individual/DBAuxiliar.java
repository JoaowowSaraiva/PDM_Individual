package pdm.di.ubi.pdm_individual;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;

public class DBAuxiliar extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Maravilha" ;
    //tabela dos posts
    protected static final String TABLE_POSTS = "Posts" ;
    protected static final String SLUG_PK = "Slugpk" ; //n tem utf-8, so etras e -
    protected static final String IDPOST   = "Id";
    protected static final String CONTENT = "Content" ;
    protected static final String TITLE = "Title" ;
    protected static final String COORDINATES = "Coordinates" ;
    protected static final String IMG = "Img";
    protected static final String DATE = "Date";
    protected static final String EXCERPT = "Excerpt";

    //protected static final Blob COLUMN5 = null;


    //tabela auxiliar
    protected static final String TABLE_AUX = " TabelaAuxiliar ";
    protected static final String ID_TABELA = "Id";
    protected static final String JSON_SIZE = "Jsonsize";



    protected static final String POSTS_CREATE_TABLE = "" +
            "CREATE TABLE " + TABLE_POSTS + " (" +
            SLUG_PK + " VARCHAR(255) PRIMARY KEY, " +
            IDPOST + " INT, " +
            CONTENT + " TEXT, " +
            TITLE + " VARCHAR(255), " +
            COORDINATES + " TEXT, " +
            IMG + " BLOB, " +
            EXCERPT + " TEXT, " +
            DATE + " TEXT );" ;

    protected static final String AUX_CREATE_TABLE = "" +
            "CREATE TABLE " + TABLE_AUX + " (" +
            ID_TABELA + " INT PRIMARY KEY, " +
            JSON_SIZE + " INT );";



    DBAuxiliar(Context context){
          super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(POSTS_CREATE_TABLE);
        db.execSQL(AUX_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}




/**

 oDBAux = new DBAuxiliar(this);
 oSQLiteDB = oDBAux.getWritableDatabase();
 System.out.println("TAG SOUT" + " PASSOU NAS INICIALIZAÇÔES");

 ContentValues oCValues = new ContentValues();

 oCValues.put(oDBAux.SLUG_PK, "praia-de-cvl1");
 oCValues.put(oDBAux.IDPOST, new Integer(1));
 oCValues.put(oDBAux.CONTENT, "Este seria o texto que tem uma carrada de cenas com '+ ~ º ç .");
 oCValues.put(oDBAux.TITLE, "Titulo Lindo");

 oSQLiteDB.insert(oDBAux.TABLE_POSTS, null, oCValues);

 System.out.println("VAMOSSS!");

 Cursor oCursor;
 oDBAux = new DBAuxiliar(this);
 SQLiteDatabase db2 = oDBAux.getWritableDatabase();

 oCursor = db2.rawQuery("SELECT * " + "FROM " + "Posts", null);
 oCursor.moveToFirst();

 int a =  oCursor.getCount();

 String s = oCursor.getString(0).toString();
 System.out.println("Resultado: " + s + " " + a);

 **/