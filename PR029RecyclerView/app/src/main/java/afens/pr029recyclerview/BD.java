package afens.pr029recyclerview;

import java.util.ArrayList;

/**
 * Created by Usuario on 14/01/2016.
 */
public class BD {
    private static final ArrayList<ListItem> datos;
    static {
        datos = new ArrayList<>();
        datos.add(new Titulo("Grupo Titulo"));
        for (int i = 0; i < 20; i++) {
            datos.add(new Datos("Alumno "+i));
        }
    }
    public static ArrayList<ListItem> getDatos() {
        return datos;
    }
}
