/*
 * Copyright 2014 Taylor Caldwell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package adamzimnyy.com.leaguestats.model.riot.recent;

import io.realm.RealmList;
import io.realm.RealmObject;

import java.io.Serializable;
import java.util.List;


public class Game extends RealmObject implements Serializable {

    private static final long serialVersionUID = 1228681223919935293L;

    private int championId;
    private long createDate;
    private RealmList<Player> fellowPlayers;
    private long gameId;
    private String gameMode;
    private String gameType;
    private boolean invalid;
    private int ipEarned;
    private int level;
    private int mapId;
    private int spell1;
    private int spell2;
    private RawStats stats;
    private String subType;
    private int teamId;

    public int getChampionId() {
        return championId;
    }

    public long getCreateDate() {
        return createDate;
    }

    public List<Player> getFellowPlayers() {
        return fellowPlayers;
    }

    public long getGameId() {
        return gameId;
    }

    public String getGameMode() {
        return gameMode;
    }

    public String getGameType() {
        return gameType;
    }

    public int getIpEarned() {
        return ipEarned;
    }

    public int getLevel() {
        return level;
    }

    public int getMapId() {
        return mapId;
    }

    public int getSpell1() {
        return spell1;
    }

    public int getSpell2() {
        return spell2;
    }

    public RawStats getStats() {
        return stats;
    }

    public String getSubType() {
        return subType;
    }

    public int getTeamId() {
        return teamId;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public class Field {

        public static final String IP_EARNED = "ipEarned";
        public static final String LEVEL = "level";
        public static final String MAP = "mapId";
        public static final String SPELL_1 = "spell1";
        public static final String SPELL_2 = "spell2";
        public static final String SUB_TYPE = "subType";
        public static final String TEAM = "teamId";
    }
}