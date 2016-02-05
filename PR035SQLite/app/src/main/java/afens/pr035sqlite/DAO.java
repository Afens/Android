package afens.pr035sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 01/02/2016.
 */
public class DAO {

    // Patron Singleton
    private static DAO mInstance;
    public static DAO getInstance(Context contexto) {
        if(mInstance==null)
            mInstance=new DAO(contexto);

        return mInstance;
    }

    // Variables a nivel de clase.
    private final Helper mHelper; // Ayudante para la creación y gestión de la BD.

    // Constructor. Recibe el contexto.
    private DAO(Context contexto) {
        // Se obtiene el mHelper.
        mHelper = new Helper(contexto);
    }

    // Abre la base de datos para escritura.
    public SQLiteDatabase openWritableDatabase() {
        return mHelper.getWritableDatabase();
    }

    // Cierra la base de datos.
    public void closeDatabase() {
        mHelper.close();
    }


    public long createAlumno(Alumno alumno) {
        // Se abre la base de datos.
        SQLiteDatabase bd = mHelper.getWritableDatabase();
        // Se crea la lista de pares campo-valor para realizar la inserción.
        ContentValues valores = new ContentValues();
        valores.put(Contract.Alumno.NOMBRE, alumno.getNombre());
        valores.put(Contract.Alumno.CURSO, alumno.getCurso());
        valores.put(Contract.Alumno.TELEFONO, alumno.getTelefono());
        valores.put(Contract.Alumno.EDAD, alumno.getEdad());
        valores.put(Contract.Alumno.DIRECCION, alumno.getDireccion());
        valores.put(Contract.Alumno.REPETIDOR, alumno.isRepetidor());
        valores.put(Contract.Alumno.FOTO, alumno.getFoto());
        // Se realiza el insert
        long resultado = bd.insert(Contract.Alumno.TABLA, null, valores);
        // Se cierra la base de datos.
        mHelper.close();
        // Se retorna el _id del alumno insertado o -1 si error.
        return resultado;
    }


    public boolean deleteAlumno(long id) {
        // Se abre la base de datos.
        SQLiteDatabase bd = mHelper.getWritableDatabase();
        // Se realiza el delete.
        long resultado = bd.delete(Contract.Alumno.TABLA, Contract.Alumno._ID + " = "
                + id, null);
        // Se cierra la base de datos.
        mHelper.close();
        // Se retorna si se ha eliminado algún alumno.
        return resultado > 0;

    }

    public boolean updateAlumno(Alumno alumno) {
        // Se abre la base de datos.
        SQLiteDatabase bd = mHelper.getWritableDatabase();
        // Se crea la lista de pares clave-valor con cada campo-valor.
        ContentValues valores = new ContentValues();
        valores.put(Contract.Alumno.NOMBRE, alumno.getNombre());
        valores.put(Contract.Alumno.CURSO, alumno.getCurso());
        valores.put(Contract.Alumno.TELEFONO, alumno.getTelefono());
        valores.put(Contract.Alumno.EDAD, alumno.getEdad());
        valores.put(Contract.Alumno.DIRECCION, alumno.getDireccion());
        valores.put(Contract.Alumno.REPETIDOR, alumno.isRepetidor());
        valores.put(Contract.Alumno.FOTO, alumno.getFoto());
        // Se realiza el update.
        long resultado = bd.update(Contract.Alumno.TABLA, valores, Contract.Alumno._ID
                + " = " + alumno.getId(), null);
        // Se cierra la base de datos.
        mHelper.close();
        // Se retorna si ha actualizado algún alumno.
        return resultado > 0;

    }

    public Cursor queryAllAlumnos(SQLiteDatabase bd) {
        // Se realiza la consulta y se retorna el cursor.
        return  bd.query(Contract.Alumno.TABLA, Contract.Alumno.TODOS, null,
                null, null, null, Contract.Alumno.NOMBRE);
    }

    public List<Alumno> getAllAlumnos() {
        // Se abre la base de datos.
        SQLiteDatabase bd = mHelper.getWritableDatabase();
        List<Alumno> lista = new ArrayList<>();
        // Se consultan todos los alumnos en la BD y obtiene un cursor.
        Cursor cursor = this.queryAllAlumnos(bd);
        // Se convierte cada registro del cursor en un elemento de la lista.
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Alumno alumno = cursorToAlumno(cursor);
            lista.add(alumno);
            cursor.moveToNext();
        }
        // Se cierra el cursor (IMPORTANTE).
        cursor.close();
        // Se cierra la base de datos.
        mHelper.close();
        // Se retorna la lista.
        return lista;
    }

    public Alumno cursorToAlumno(Cursor cursorAlumno) {
        // Crea un objeto Alumno y guarda los valores provenientes
        // del registro actual del cursor.
        Alumno alumno = new Alumno();
        alumno.setId(cursorAlumno.getInt(
                cursorAlumno.getColumnIndexOrThrow(Contract.Alumno._ID)));
        alumno.setNombre(cursorAlumno.getString(
                cursorAlumno.getColumnIndexOrThrow(Contract.Alumno.NOMBRE)));
        alumno.setCurso(cursorAlumno.getString(
                cursorAlumno.getColumnIndexOrThrow(Contract.Alumno.CURSO)));
        alumno.setTelefono(cursorAlumno.getString(
                cursorAlumno.getColumnIndexOrThrow(Contract.Alumno.TELEFONO)));
        alumno.setDireccion(cursorAlumno.getString(
                cursorAlumno.getColumnIndexOrThrow(Contract.Alumno.DIRECCION)));
        alumno.setRepetidor(cursorAlumno.getInt(
                cursorAlumno.getColumnIndexOrThrow(Contract.Alumno.REPETIDOR))==1);
        alumno.setEdad(cursorAlumno.getInt(
                cursorAlumno.getColumnIndexOrThrow(Contract.Alumno.EDAD)));
        alumno.setFoto(cursorAlumno.getString(
                cursorAlumno.getColumnIndexOrThrow(Contract.Alumno.FOTO)));
        // Se retorna el objeto Alumno.
        return alumno;
    }
}
