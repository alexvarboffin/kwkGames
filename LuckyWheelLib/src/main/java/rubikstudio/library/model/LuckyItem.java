package rubikstudio.library.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by kiennguyen on 11/5/16.
 */

@Entity//(primaryKeys = {"host", "port"})
public class LuckyItem {

    public String topText;
    public String secondaryText = "";
    public int secondaryTextOrientation;
    public int icon;
    public int color;
    public boolean active = true;

    @PrimaryKey(autoGenerate = true)
    public long _id;

    public LuckyItem(String topText, String secondaryText) {
        this.topText = topText;
        this.secondaryText = secondaryText;
    }

    public LuckyItem() {}


    @Override
    public String toString() {
        return "{" +
                "topText='" + topText + '\'' +
                ", secondaryText='" + secondaryText + '\'' +
                ", secondaryTextOrientation=" + secondaryTextOrientation +
                ", icon=" + icon +
                ", color=" + color +
                ", active=" + active +
                ", _id=" + _id +
                '}';
    }
}
