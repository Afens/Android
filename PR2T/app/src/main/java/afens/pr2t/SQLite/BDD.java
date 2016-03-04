package afens.pr2t.SQLite;

import android.provider.BaseColumns;

/**
 * Created by Afens on 27/02/2016.
 */
public class BDD {
    //Constantes generales de la BDD
    public static final String BDD_NAME = "FCT";
    public static final int BDD_VERSION = 1;

    //Constructor privado para que no pueda instanciarse.
    private BDD(){

    }
    //Tabla Alumno
    public static abstract class Alumno implements BaseColumns {
        public static final String TABLA = "alumno";
        public static final String NOMBRE = "nombre";
        public static final String TELEFONO = "telefono";
        public static final String EMAIL = "email";
        public static final String EMPRESA = "empresa";
        public static final String TUTOR = "tutor";
        public static final String HORARIO = "horario";
        public static final String DIRECCION = "direccion";
        public static final String FOTO = "foto";
        public static final String[] TODOS = new String[] { _ID, NOMBRE, TELEFONO,
                EMAIL, EMPRESA, TUTOR, HORARIO, DIRECCION, FOTO };
    }
    //Tabla Visita
    public static abstract class Visita implements BaseColumns {
        public static final String TABLA = "visita";
        public static final String ID_ALUMNO = "idAlumn";
        public static final String INICIO = "inicio";
        public static final String FIN = "fin";
        public static final String COMENTARIO = "comentario";
        public static final String[] TODOS = new String[] { _ID, ID_ALUMNO, INICIO, FIN, COMENTARIO };
    }
}
