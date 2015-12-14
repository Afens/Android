package afens.mislibrosfavoritos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usuario on 11/12/2015.
 */
public class Libro {
    private String titulo;
    private String autor;
    private int year_publicacion;
    private String url_portada;
    private String sinopsis;

    public Libro() {
    }


    // -------------- Get y Set ----------------
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getYear_publicacion() {
        return year_publicacion;
    }

    public void setYear_publicacion(int year_publicacion) {
        this.year_publicacion = year_publicacion;
    }

    public String getUrl_portada() {
        return url_portada;
    }

    public void setUrl_portada(String url_portada) {
        this.url_portada = url_portada;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }



}
