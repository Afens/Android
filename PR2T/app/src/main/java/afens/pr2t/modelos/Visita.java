package afens.pr2t.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Afens on 27/02/2016.
 */
public class Visita implements Parcelable{
    private int id;
    private int idAlumno;
    private Date inicio;
    private Date fin;
    private String comentario;

    public Visita() {
    }

    public Visita(int idAlumno, Date inicio, Date fin, String comentario) {

        this.idAlumno = idAlumno;
        this.inicio = inicio;
        this.fin = fin;
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

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
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
        dest.writeLong(inicio != null ? inicio.getTime() : -1);
        dest.writeLong(fin != null ? fin.getTime() : -1);
        dest.writeString(this.comentario);
    }

    protected Visita(Parcel in) {
        this.id = in.readInt();
        this.idAlumno = in.readInt();
        long tmpInicio = in.readLong();
        this.inicio = tmpInicio == -1 ? null : new Date(tmpInicio);
        long tmpFin = in.readLong();
        this.fin = tmpFin == -1 ? null : new Date(tmpFin);
        this.comentario = in.readString();
    }

    public static final Creator<Visita> CREATOR = new Creator<Visita>() {
        public Visita createFromParcel(Parcel source) {
            return new Visita(source);
        }

        public Visita[] newArray(int size) {
            return new Visita[size];
        }
    };
}
