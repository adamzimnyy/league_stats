package adamzimnyy.com.leaguestats.util;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by adamz on 01.04.2017.
 */

public class Migration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        // DynamicRealm exposes an editable schema
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 0) {
            schema.get("Filter")
                    .addField("gameField", boolean.class);
            oldVersion++;

            //done
        }

        if (oldVersion == 1) {
            schema.get("Graph")
                    .addField("fieldName", String.class);
            oldVersion++;
        }
        if (oldVersion == 2) {
            schema.get("Graph")
                    .addField("gameField", boolean.class)
                    .addField("statsField", boolean.class);
            schema.get("Filter")
                    .addField("statsField", boolean.class);
            oldVersion++;
        }
    }
}
