package adamzimnyy.com.leaguestats.model.realm;

import adamzimnyy.com.leaguestats.model.riot.Image;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by adamz on 26.03.2017.
 */

public class Champion extends RealmObject {

    @PrimaryKey
    String key;
    String name;
    Image image;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    boolean pinned;

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    Score score = new Score();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
