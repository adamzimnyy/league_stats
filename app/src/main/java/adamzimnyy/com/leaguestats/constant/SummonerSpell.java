package adamzimnyy.com.leaguestats.constant;

import adamzimnyy.com.leaguestats.api.constant.Server;
import android.widget.HeaderViewListAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adamz on 01.04.2017.
 */

public enum SummonerSpell {
    BARRIER("SummonerBarrier", "Barrier", 21),
    SMITE("SummonerSmite", "Smite", 11),
    FLASH("SummonerFlash", "Flash", 4),
    HEAL("SummonerHeal", "Heal", 21),
    TELEPORT("SummonerTeleport", "Teleport", 12),
    CLEANSE("SummonerBoost", "Cleanse", 13),
    MARK("SummonerSnowball", "Mark", 32),
    CLARITY("SummonerMana", "Clarity", 13),
    PORO_TOSS("SummonerPoroThrow", "Poro Toss", 31),
    PORO_RECALL("SummonerPoroRecall", "To the King!", 30),
    EXHAUST("SummonerExhaust", "Exhaust", 3),
    IGNITE("SummonerDot", "Ignite", 14),
    GHOST("SummonerHaste", "Ghost", 6),
    NEXUS_1("SummonerSiegeChampSelect1", "Nexus Siege: Siege Weapon Slot", 33),
    NEXUS_2("SummonerSiegeChampSelect2", "Nexus Siege: Siege Weapon Slot", 34);
    String key;
    String name;
    int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    SummonerSpell(String key, String name, int id) {
        this.name = name;
        this.key = key;
        this.id = id;
    }

    private static final Map<Integer, SummonerSpell> idMap;
    private static final Map<String, SummonerSpell> keyMap;


    static {
        idMap = new HashMap<>();
        keyMap = new HashMap<>();
        for (SummonerSpell v : SummonerSpell.values()) {
            idMap.put(v.id, v);
            keyMap.put(v.key, v);
        }
    }

    public static SummonerSpell findById(int id) {
        return idMap.get(id);
    }

    public static SummonerSpell findByKey(String key) {
        return keyMap.get(key);
    }
}
