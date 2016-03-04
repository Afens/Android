package afens.trabajo2t.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Afens on 27/02/2016.
 */
public class Visita implements Parcelable{
    private int id;
    private int idAlumno;
    private Date dia;
    private String comentario;

    public Visita() {
    }

    public Visita(int id, int idAlumno, Date dia, String comentario) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.dia = dia;
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.idAlumno);
        dest.writeLong(dia != null ? dia.getTime() : -1);
        dest.writeString(this.comentario);
    }

    protected Visita(Parcel in) {
        this.id = in.readInt();
        this.idAlumno = in.readInt();
        long tmpDia = in.readLong();
        this.dia = tmpDia == -1 ? null : new Date(tmpDia);
        this.comentario = in.readString();
    }

    public static final Parcelable.Creator<Visita> CREATOR = new Parcelable.Creator<Visita>() {
        public Visita createFromParcel(Parcel source) {
            return new Visita(source);
        }

        public Visita[] newArray(int size) {
            return new Visita[size];
        }
    };
}
