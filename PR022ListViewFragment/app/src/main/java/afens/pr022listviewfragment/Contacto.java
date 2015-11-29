package afens.pr022listviewfragment;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Afens on 29/11/2015.
 */
public class Contacto implements Parcelable{
    private String nombre;
    private int edad;
    private String foto;
    private String localidad;
    private String telf;
    private String correo;

    public Contacto() {

    }

    public Contacto(Parcel in) {
        this.nombre = in.readString();
        this.edad = in.readInt();
        this.foto = in.readString();
        this.localidad = in.readString();
        this.telf = in.readString();
        this.correo = in.readString();
    }

    public static final Creator<Contacto> CREATOR = new Creator<Contacto>() {
        @Override
        public Contacto createFromParcel(Parcel in) {
            return new Contacto(in);
        }

        @Override
        public Contacto[] newArray(int size) {
            return new Contacto[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeInt(edad);
        dest.writeString(foto);
        dest.writeString(localidad);
        dest.writeString(telf);
        dest.writeString(correo);
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getTelf() {
        return telf;
    }

    public void setTelf(String telf) {
        this.telf = telf;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
