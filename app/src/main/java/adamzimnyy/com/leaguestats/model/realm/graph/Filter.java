package adamzimnyy.com.leaguestats.model.realm.graph;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by adamz on 01.04.2017.
 */

public class Filter extends RealmObject {
    String fieldName;
    String fieldType;
    String value;
    boolean gameField;

    public boolean isStatsField() {
        return statsField;
    }

    public void setStatsField(boolean statsField) {
        this.statsField = statsField;
    }

    boolean statsField;

    public boolean isGameField() {
        return gameField;
    }

    public void setGameField(boolean gameField) {
        this.gameField = gameField;
    }

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

    public class FilterType{
        public static final String BOOLEAN = "boolean";
        public static final String INTEGER = "int";
        public static final String STRING = "String";
    }
}
