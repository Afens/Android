package afens.trabajo2t.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Afens on 27/02/2016.
 */
public class Helper extends SQLiteOpenHelper {
    // Sentencia SQL para crear la tabla de Alumnos.
    private static final String SQL_CREATE_ALUMNO = "CREATE TABLE " + BDD.Alumno.TABLA + " (" +
            BDD.Alumno._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BDD.Alumno.NOMBRE + " TEXT, " +
            BDD.Alumno.TELEFONO + " TEXT, " +
            BDD.Alumno.EMAIL + " TEXT," +
            BDD.Alumno.EMPRESA + " TEXT," +
            BDD.Alumno.TUTOR + " TEXT," +
            BDD.Alumno.HORARIO + " TEXT," +
            BDD.Alumno.DIRECCION + " TEXT," +
            BDD.Alumno.FOTO + " TEXT" +
            " );";
    private static final String SQL_CREATE_VISITA = "CREATE TABLE " + BDD.Visita.TABLA + " (" +
            BDD.Visita._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BDD.Visita.ID_ALUMNO + " INTEGER," +
            BDD.Visita.DIA + " DATE, " +
            BDD.Visita.COMENTARIO + " TEXT" +
            " );";


    // Constructor.
    public Helper(Context contexto, String nombreBD,
                  SQLiteDatabase.CursorFactory factory, int versionBD) {
        // Se llama al constuctor del padre.
        super(contexto, nombreBD, factory, versionBD);
    }
    public Helper(Context contexto){
        this(contexto,BDD.BDD_NAME,null,BDD.BDD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ALUMNO);
        db.execSQL(SQL_CREATE_VISITA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Por simplicidad, se eliminan las tablas existentes y se vuelven a crear,
        // perderia todos los datos
        db.execSQL("DROP TABLE IF EXISTS " + BDD.Alumno.TABLA);
        db.execSQL(SQL_CREATE_ALUMNO);
        db.execSQL("DROP TABLE IF EXISTS " + BDD.Visita.TABLA);
        db.execSQL(SQL_CREATE_VISITA);
    }
}
