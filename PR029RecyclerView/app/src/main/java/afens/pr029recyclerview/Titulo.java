package afens.pr029recyclerview;

/**
 * Created by Usuario on 14/01/2016.
 */
public class Titulo extends ListItem {
    String titulo;

    public Titulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public int getType() {
        return ListItem.TYPE_TITLE;
    }
}
