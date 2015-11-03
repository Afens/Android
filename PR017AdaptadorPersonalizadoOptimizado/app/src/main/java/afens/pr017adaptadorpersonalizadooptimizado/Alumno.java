package afens.pr017adaptadorpersonalizadooptimizado;

/**
 * Created by Usuario on 29/10/2015.
 */
public class Alumno {
    String nombre;
    int edad;
    String tlf;

    public Alumno(String nombre, int edad, String tlf) {
        this.nombre = nombre;
        this.edad = edad;
        this.tlf = tlf;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }
}
