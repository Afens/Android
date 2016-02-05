package afens.pr035sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Usuario on 01/02/2016.
 */
public class Helper extends SQLiteOpenHelper {
    // Sentencia SQL para crear la tabla de Alumnos.
    private static final String SQL_CREATE_ALUMNOS =
            "CREATE TABLE " + Contract.Alumno.TABLA + " (" +
                    Contract.Alumno._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Contract.Alumno.NOMBRE + " TEXT, " +
                    Contract.Alumno.CURSO + " TEXT, " +
                    Contract.Alumno.TELEFONO + " TEXT, " +
                    Contract.Alumno.EDAD + " INTEGER," +
                    Contract.Alumno.DIRECCION + " TEXT, " +
                    Contract.Alumno.REPETIDOR + " INTEGER," +
                    Contract.Alumno.FOTO + " TEXT " +
                    " )";
    // ... (sentencias SQL para el resto de tablas).

    // Constructor.
    public Helper(Context contexto, String nombreBD,
                                 SQLiteDatabase.CursorFactory factory, int versionBD) {
        // Se llama al constuctor del padre.
        super(contexto, nombreBD, factory, versionBD);
    }
    public Helper(Context contexto){
        this(contexto,Contract.BD_NOMBRE,null,Contract.BD_VERSION);
    }

    // Se llama automáticamente al intentar abrir una base de datos que
    // no exista aún.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Se ejecutan las sentencias SQL de creación de las tablas.
        db.execSQL(SQL_CREATE_ALUMNOS);
        // ... (sentencias SQL del resto de tablas)
    }

    // Se llama automáticamente al intentar abrir una base de datos con una versión
    // distinta a la que tiene actualmente.
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        // Por simplicidad, se eliminan las tablas existentes y se vuelven a crear,
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Alumno.TABLA);
        db.execSQL(SQL_CREATE_ALUMNOS);
        // ... (sentencias SQL del resto de tablas)
    }
}
