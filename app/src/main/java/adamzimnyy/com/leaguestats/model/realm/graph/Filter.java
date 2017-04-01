package adamzimnyy.com.leaguestats.model.realm.graph;

import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by adamz on 01.04.2017.
 */

public class Filter extends RealmObject {
    String fieldName;
    String fieldType;
    String value;

    public Filter() {
    }

    public Filter(String fieldName, String fieldType, String value) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.value = value;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
