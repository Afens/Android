package afens.pr035sqlite;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usuario on 22/01/2016.
 */
public class Alumno extends ListItem {

    private  int id;
    private String nombre;
    private String curso;
    private String telefono;
    private int edad;
    private String direccion;
    private boolean repetidor;
    private String foto;

    public Alumno(String nombre, String curso, String telefono, int edad, String direccion, boolean repetidor, String foto) {
        this.nombre = nombre;
        this.curso = curso;
        this.telefono = telefono;
        this.edad = edad;
        this.direccion = direccion;
        this.repetidor = repetidor;
        this.foto = foto;
    }
    public Alumno(int id, String nombre, String curso, String telefono, int edad, String direccion, boolean repetidor, String foto) {
        this(nombre, curso, telefono, edad, direccion, repetidor, foto);
        this.id = id;

    }
    public Alumno(){

    }



    //GETTERS && SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isRepetidor() {
        return repetidor;
    }

    public void setRepetidor(boolean repetidor) {
        this.repetidor = repetidor;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nombre);
        dest.writeString(this.curso);
        dest.writeString(this.telefono);
        dest.writeInt(this.edad);
        dest.writeString(this.direccion);
        dest.writeByte(repetidor ? (byte) 1 : (byte) 0);
        dest.writeString(this.foto);
    }

    protected Alumno(Parcel in) {
        this.id = in.readInt();
        this.nombre = in.readString();
        this.curso = in.readString();
        this.telefono = in.readString();
        this.edad = in.readInt();
        this.direccion = in.readString();
        this.repetidor = in.readByte() != 0;
        this.foto = in.readString();
    }

    public static final Parcelable.Creator<Alumno> CREATOR = new Parcelable.Creator<Alumno>() {
        public Alumno createFromParcel(Parcel source) {
            return new Alumno(source);
        }

        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };

    @Override
    public int getType() {
        return ListItem.TYPE_CHILD;
    }


}