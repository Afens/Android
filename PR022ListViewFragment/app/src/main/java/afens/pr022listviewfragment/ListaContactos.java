package afens.pr022listviewfragment;

import java.util.ArrayList;

/**
 * Created by Afens on 01/12/2015.
 */
public class ListaContactos {
    private static ArrayList<Contacto> contactos;
    private static boolean generados=false;
    public static ArrayList<Contacto> crearContactos() {
        if (!generados) {
            generados=true;
            contactos = new ArrayList<>();
            Contacto c;
            for (int i = 0; i < 5; i++) {
                c = new Contacto();
                c.setNombre("C" + i);
                c.setCorreo(i + "@" + i);
                c.setEdad(i);
                c.setLocalidad("Local" + i);
                c.setTelf("+34" + i);
                c.setFoto("http://lorempixel.com/500/500/people/" + (i + 1) + "/");
                contactos.add(c);
            }
        }
        return contactos;
    }

    public static int indexOf(Contacto c) {
        return contactos.indexOf(c);
    }
    public static Contacto get(int i) {
        return contactos.get(i);
    }
    public static void add(Contacto c){contactos.add(c);}
    public static int size(){return contactos.size();}
}
