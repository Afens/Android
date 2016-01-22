package afens.pr029recyclerview;

import android.os.Parcelable;

/**
 * Created by Usuario on 15/01/2016.
 */
public abstract class ListItem {
    static final int TYPE_TITLE = 0;
    static final int TYPE_DATA = 1;

    public abstract int getType();
}
