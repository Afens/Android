package afens.pr015parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usuario on 23/10/2015.
 */
public class Alumno implements Parcelable {
    private String dni, nombre = "No Disponible";
    private String edad = "No Disponible";
    private String sexo;

    public Alumno() {

    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dni);
        dest.writeString(this.nombre);
        dest.writeString(this.edad);
        dest.writeString(this.sexo);
    }

    protected Alumno(Parcel in) {
        this.dni = in.readString();
        this.nombre = in.readString();
        this.edad = in.readString();
        this.sexo = in.readString();
    }

    public static final Creator<Alumno> CREATOR = new Creator<Alumno>() {
        public Alumno createFromParcel(Parcel source) {
            return new Alumno(source);
        }

        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };
}
