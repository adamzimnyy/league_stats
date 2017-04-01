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
            schema.create("Filter")
                    .addField("fieldName", String.class)
                    .addField("fieldType", String.class)
                    .addField("value", String.class);
            schema.get("Graph").removeField("filter")
                    .addRealmListField("filters",schema.get("Filter"));
            oldVersion++;
        }

        if(oldVersion == 1){
            oldVersion++;
        }
    }
}
