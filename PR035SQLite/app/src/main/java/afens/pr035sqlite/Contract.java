package afens.pr035sqlite;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 01/02/2016.
 */
public class Contract {

    // Constantes generales de la BD.
    public static final String BD_NOMBRE = "instituto";
    public static final int BD_VERSION = 1;

    // Tabla Alumno.
    public static abstract class Alumno implements BaseColumns {
        public static final String TABLA = "alumnos";
        public static final String NOMBRE = "nombre";
        public static final String CURSO = "curso";
        public static final String TELEFONO = "telefono";
        public static final String EDAD = "edad";
        public static final String DIRECCION = "direccion";
        public static final String REPETIDOR = "repetidor";
        public static final String FOTO = "foto";
        public static final String[] TODOS = new String[] { _ID, NOMBRE, CURSO,
                TELEFONO,EDAD, DIRECCION,REPETIDOR,FOTO };
    }

    // Constructor privado para que NO pueda instanciarse.
    private Contract() {
    }
}
