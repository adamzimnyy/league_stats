package adamzimnyy.com.leaguestats.model.realm;

import adamzimnyy.com.leaguestats.util.IntentHelper;
import io.realm.RealmObject;

import java.util.*;

/**
 * Created by adamz on 26.03.2017.
 */

public class Score extends RealmObject {
    int D;
    int D_PLUS;
    int C_MINUS;
    int C;
    int C_PLUS;
    int B_MINUS;
    int B;
    int B_PLUS;
    int A_MINUS;
    int A;
    int A_PLUS;
    int S_MINUS;
    int S;
    int S_PLUS;

    public List<Integer> getAsList() {
        return Arrays.asList(D, D_PLUS, C_MINUS, C, C_PLUS, B_MINUS, B, B_PLUS, A_MINUS, A, A_PLUS, S_MINUS, S, S_PLUS);
    }
}
