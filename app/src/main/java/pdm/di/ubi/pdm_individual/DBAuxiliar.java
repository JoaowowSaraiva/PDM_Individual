package pdm.di.ubi.pdm_individual;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;

public class DBAuxiliar extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = " Maravilha " ;
    //tabela dos posts
    protected static final String TABLE_POSTS = " Posts " ;
    protected static final String SLUG_PK = " " ; //n tem utf-8, so etras e -
    protected static final String IDPOST   = " ";
    protected static final String CONTENT = " " ;
    protected static final String TITLE = "  " ;
    protected static final String COORDINATES = "  " ;
    protected static final String IMG = " ";
    protected static final String DATE = " ";

    //protected static final Blob COLUMN5 = null;


    //tabela auxiliar
    protected static final String TABLE_AUX = " TabelaAuxiliar ";
    protected static final String ID_TABELA = " ";
    protected static final String JSON_SIZE = " ";



    protected static final String POSTS_CREATE_TABLE = "" +
            "CREATE TABLE " + TABLE_POSTS + " (" +
            SLUG_PK + " VARCHAR(255) PRIMARY KEY, " +
            IDPOST + " INT, " +
            CONTENT + " TEXT, " +
            TITLE + " VARCHAR(255), " +
            COORDINATES + " " +
            IMG + " BLOB, " +
            DATE + " );" ;

    protected static final String TABLE_AUX_CREATE = "";



    DBAuxiliar(Context context){
          super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
