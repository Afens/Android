package afens.mislibrosfavoritos;

import java.util.ArrayList;

/**
 * Created by Usuario on 11/12/2015.
 */
public class Coleccion {
    private static ArrayList<Libro> lista;
    static {
        lista=new ArrayList<Libro>();
        Libro l=new Libro();
        l.setTitulo("El Umbral de la Eternidad");
        l.setAutor("Follet, Ken");
        l.setYear_publicacion(2014);
        l.setUrl_portada("http://www.celesa.com/images/9788401342196.jpg");
        l.setSinopsis("Después de La caída de los gigantes y El invierno del mundo llega el final de la gran historia de las cinco " +
                "familias cuyas vidas se han entrelazado a través del siglo XX. En el año 1961 Rebecca Hoffman, profesora en Alemania " +
                "del Este y nieta de lady Maud, descubrirá que la policía secreta está vigilándola mientras su herma no menor, Walli, " +
                "sueña con huir a Occidente para convertirse en músico de rock. George Jakes, joven abogado que trabaja con los hermanos " +
                "Kennedy, es un activista del movimiento por los derechos civiles de los negros en Estados Unidos que participará en " +
                "las protestas de los estados del Sur y en la marcha sobre Washington liderada por Martin Luther King. En Rusia " +
                "las inclinaciones políticas enfrentan a los hermanos Tania y Dimka Dvorkin. Este se convierte en una de las " +
                "jóvenes promesas del Kremlin mientras su hermana entrará a formar parte de un grupo activista que promueve " +
                "la insurrección. Desde el sur de Estados Unidos hasta la remota Siberia, desde la isla de Cuba hasta el " +
                "vibrante Londres de los años sesenta, El umbral de la eternidad es la historia de aquellas personas que lucharon " +
                "por la libertad individual en medio del conflicto titánico entre los dos países más poderosos jamás conocidos.");
        lista.add(l);
    }
    public static void add(Libro l){
        lista.add(l);
    }
    public static ArrayList<Libro> getLista() {
        return lista;
    }
}
