package afens.pr2t.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import afens.pr2t.App;
import afens.pr2t.modelos.Alumno;
import afens.pr2t.modelos.Visita;


/**
 * Created by Afens on 27/02/2016.
 */
public class DAO {
    //Singletone
    private static DAO mInstance;
    private final SharedPreferences preferences;

    public static synchronized DAO getInstance(Context contexto) {
        if (mInstance == null)
            mInstance = new DAO(contexto);

        return mInstance;
    }

    // Variables a nivel de clase.
    private final Helper mHelper; // Ayudante para la creación y gestión de la BD.

    // Constructor. Recibe el contexto.
    private DAO(Context contexto) {
        // Se obtiene el mHelper.
        mHelper = new Helper(contexto);
        preferences = PreferenceManager.getDefaultSharedPreferences(contexto);
    }

    // Abre la base de datos para escritura.
    public SQLiteDatabase openWritableDatabase() {
        return mHelper.getWritableDatabase();
    }

    // Cierra la base de datos.
    public void closeDatabase() {
        mHelper.close();
    }


    //Alumno
    public long createAlumno(Alumno alumno) {
        long idAlumnoInsertado;

        //Se abre la base de datos
        SQLiteDatabase db = openWritableDatabase();
        //Se crea la lista de pares clave-valor para realizar la inserción.
        ContentValues valores = new ContentValues();
        valores.put(BDD.Alumno.NOMBRE, alumno.getNombre());
        valores.put(BDD.Alumno.TELEFONO, alumno.getTelefono());
        valores.put(BDD.Alumno.EMAIL, alumno.getEmail());
        valores.put(BDD.Alumno.EMPRESA, alumno.getEmpresa());
        valores.put(BDD.Alumno.TUTOR, alumno.getTutor());
        valores.put(BDD.Alumno.HORARIO, alumno.getHorario());
        valores.put(BDD.Alumno.DIRECCION, alumno.getDireccion());
        valores.put(BDD.Alumno.FOTO, alumno.getFoto());
        //Se realiza la Insert y se cierra la base de datos.
        idAlumnoInsertado = db.insert(BDD.Alumno.TABLA, null, valores);
        closeDatabase();
        return idAlumnoInsertado;
    }

    public boolean deleteAlumno(long id) {
        SQLiteDatabase db = openWritableDatabase();
        db.delete(BDD.Visita.TABLA, BDD.Visita.ID_ALUMNO + " = " + id, null);
        long resultado = db.delete(BDD.Alumno.TABLA, BDD.Alumno._ID + " = " + id, null);
        closeDatabase();
        return resultado > 0;
    }

    public boolean updateAlumno(Alumno alumno) {
        // Se abre la base de datos.
        SQLiteDatabase bd = openWritableDatabase();
        // Se crea la lista de pares clave-valor con cada campo-valor.
        ContentValues valores = new ContentValues();
        valores.put(BDD.Alumno.NOMBRE, alumno.getNombre());
        valores.put(BDD.Alumno.TELEFONO, alumno.getTelefono());
        valores.put(BDD.Alumno.EMAIL, alumno.getEmail());
        valores.put(BDD.Alumno.EMPRESA, alumno.getEmpresa());
        valores.put(BDD.Alumno.TUTOR, alumno.getTutor());
        valores.put(BDD.Alumno.HORARIO, alumno.getHorario());
        valores.put(BDD.Alumno.DIRECCION, alumno.getDireccion());
        valores.put(BDD.Alumno.FOTO, alumno.getFoto());
        // Se realiza el update.
        long resultado = bd.update(BDD.Alumno.TABLA, valores, BDD.Alumno._ID
                + " = " + alumno.getId(), null);
        // Se cierra la base de datos.
        closeDatabase();
        // Se retorna si ha actualizado algún alumno.
        return resultado > 0;
    }


    public Cursor queryAllAlumnos(SQLiteDatabase bd) {
        //Devuelve todos los alumnos ordenados por el nombre.
        return bd.query(BDD.Alumno.TABLA, BDD.Alumno.TODOS, null, null, null, null, BDD.Alumno.NOMBRE);
    }

    public List<Alumno> getAllAlumnos() {
        SQLiteDatabase db = openWritableDatabase();
        List<Alumno> lista = new ArrayList<>();
        //Se obtiene el cursor con todos los alumnos.
        Cursor cursor = queryAllAlumnos(db);
        cursor.moveToFirst();
        //Se traspasa todos los elementos del cursor hacia una lista.
        while (!cursor.isAfterLast()) {
            lista.add(cursorToAlumno(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return lista;
    }

    //Crea un objeto alumno a partir del registro ACTUAL del cursor pasado por parámetro.
    public Alumno cursorToAlumno(Cursor cursorAlumno) {
        Alumno alumno = new Alumno();
        alumno.setId(cursorAlumno.getInt(cursorAlumno.getColumnIndexOrThrow(BDD.Alumno._ID)));
        alumno.setNombre(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(BDD.Alumno.NOMBRE)));
        alumno.setTelefono(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(BDD.Alumno.TELEFONO)));
        alumno.setEmail(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(BDD.Alumno.EMAIL)));
        alumno.setEmpresa(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(BDD.Alumno.EMPRESA)));
        alumno.setTutor(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(BDD.Alumno.TUTOR)));
        alumno.setHorario(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(BDD.Alumno.HORARIO)));
        alumno.setDireccion(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(BDD.Alumno.DIRECCION)));
        alumno.setFoto(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(BDD.Alumno.FOTO)));
        //Se retorna el alumno ya configurado.
        return alumno;
    }

    public Alumno getAlumno(long id) {
        SQLiteDatabase bd = openWritableDatabase();
        Cursor cursor = bd.query(BDD.Alumno.TABLA, BDD.Alumno.TODOS, BDD.Alumno._ID + "=" + id, null, null, null, null);
        cursor.moveToFirst();
        Alumno a = cursorToAlumno(cursor);
        cursor.close();
        closeDatabase();
        return a;
    }


    //Visitas
    public long createVisita(Visita visita) {
        long idVisitaInsertado = -1;

        //Se abre la base de datos
        SQLiteDatabase db = openWritableDatabase();

        String condicion = String.format("%s >= %d AND %s <= %d"
                , BDD.Visita.FIN, visita.getInicio().getTime()
                , BDD.Visita.INICIO, visita.getFin().getTime());
        //Se comprueba que no hay ninguna visita en el intervalo de la que queremos añadir
        Cursor cursor = db.query(BDD.Visita.TABLA, BDD.Visita.TODOS, condicion, null, null, null, null, null);

        if (cursor.getCount() == 0) {
            //Se crea la lista de pares clave-valor para realizar la inserción.
            ContentValues valores = new ContentValues();
            valores.put(BDD.Visita.ID_ALUMNO, visita.getIdAlumno());
            valores.put(BDD.Visita.INICIO, visita.getInicio().getTime());
            valores.put(BDD.Visita.FIN, visita.getFin().getTime());
            valores.put(BDD.Visita.COMENTARIO, visita.getComentario());

            //Se realiza la Insert y se cierra la base de datos.
            idVisitaInsertado = db.insert(BDD.Visita.TABLA, null, valores);
        }

        closeDatabase();
        return idVisitaInsertado;
    }

    public boolean deleteVisita(long id) {
        SQLiteDatabase db = openWritableDatabase();
        long resultado = db.delete(BDD.Visita.TABLA, BDD.Visita._ID + " = " + id, null);
        closeDatabase();
        return resultado > 0;
    }

    public boolean updateVisita(Visita visita) {
        // Se abre la base de datos.
        SQLiteDatabase bd = openWritableDatabase();
        // Se crea la lista de pares clave-valor con cada campo-valor.
        ContentValues valores = new ContentValues();
        valores.put(BDD.Visita.ID_ALUMNO, visita.getIdAlumno());
        valores.put(BDD.Visita.INICIO, visita.getInicio().getTime());
        valores.put(BDD.Visita.FIN, visita.getFin().getTime());
        valores.put(BDD.Visita.COMENTARIO, visita.getComentario());
        // Se realiza el update.
        long resultado = bd.update(BDD.Visita.TABLA, valores, BDD.Visita._ID
                + " = " + visita.getId(), null);
        // Se cierra la base de datos.
        closeDatabase();
        // Se retorna si ha actualizado algúna visita.
        return resultado > 0;
    }


    public Cursor queryAlumnoVisitas(SQLiteDatabase bd, int idAlumno) {
        return bd.query(BDD.Visita.TABLA, BDD.Visita.TODOS, BDD.Visita.ID_ALUMNO + "=" + idAlumno, null, null, null, BDD.Visita.INICIO);
    }

    public Cursor queryAllProxVisitas(SQLiteDatabase bd) {
        String condicion = new Date().getTime() + "<" + BDD.Visita.FIN;
        String orderBy = BDD.Visita.INICIO;

        //Si la preferencia de ordenar descendentemente, se especificará en el orderBy su orden descendente.
        if (preferences.getString(App.PREF_ORDEN_VISITA, App.ORDEN_INICIO).equals(App.ORDEN_FIN))
            orderBy = BDD.Visita.FIN;

        //Devuelve las visitas posteriores al momento de ejecución de esta sentencia.
        return bd.query(BDD.Visita.TABLA, BDD.Visita.TODOS, condicion, null, null, null, orderBy);
    }

    public List<Visita> getAllProxVisitas() {
        SQLiteDatabase db = openWritableDatabase();
        List<Visita> lista = new ArrayList<>();
        //Se obtiene el cursor con todos las visitas proximas.
        Cursor cursor = queryAllProxVisitas(db);
        cursor.moveToFirst();
        //Se traspasa todos los elementos del cursor a una lista.
        while (!cursor.isAfterLast()) {
            lista.add(cursorToVisita(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return lista;
    }

    //Devuelve una lista con las visitas, del alumno propiertario de idAlumno.
    public List<Visita> getAlumnoVisitas(int idAlumno) {
        SQLiteDatabase db = openWritableDatabase();
        List<Visita> lista = new ArrayList<>();
        //Se obtiene el cursor con todos los alumnos.
        Cursor cursor = queryAlumnoVisitas(db, idAlumno);
        cursor.moveToFirst();
        //Se traspasa todos los elementos del cursor hacia una lista.
        while (!cursor.isAfterLast()) {
            lista.add(cursorToVisita(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return lista;

    }

    //Crea un objeto visita a partir del registro ACTUAL del cursor pasado por parámetro.
    public Visita cursorToVisita(Cursor cursorVisita) {
        Visita visita = new Visita();
        visita.setId(cursorVisita.getInt(cursorVisita.getColumnIndexOrThrow(BDD.Visita._ID)));
        visita.setIdAlumno(cursorVisita.getInt(cursorVisita.getColumnIndexOrThrow(BDD.Visita.ID_ALUMNO)));
        visita.setInicio(new Date(cursorVisita.getLong(cursorVisita.getColumnIndexOrThrow(BDD.Visita.INICIO))));
        visita.setFin(new Date(cursorVisita.getLong(cursorVisita.getColumnIndexOrThrow(BDD.Visita.FIN))));
        visita.setComentario(cursorVisita.getString(cursorVisita.getColumnIndexOrThrow(BDD.Visita.COMENTARIO)));

        //Se retorna la visita ya configurado.
        return visita;
    }
}
