package afens.pr020popupmenu;

/**
 * Created by Usuario on 29/10/2015.
 */
public class Contacto {
    String nombre;
    String curso;
    String tlf;


    public Contacto(String nombre, String curso, String tlf) {
        this.nombre = nombre;
        this.curso = curso;
        this.tlf = tlf;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }
}
