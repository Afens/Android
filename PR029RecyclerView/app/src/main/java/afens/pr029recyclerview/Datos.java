package afens.pr029recyclerview;

/**
 * Created by Usuario on 14/01/2016.
 */
public class Datos extends ListItem {
    String uno;

    public Datos(String uno) {
        this.uno = uno;
    }

    @Override
    public int getType() {
        return ListItem.TYPE_DATA;
    }

}
