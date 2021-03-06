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

package adamzimnyy.com.leaguestats.model.riot.match;

import io.realm.RealmObject;

import java.io.Serializable;


public class MatchPlayer extends RealmObject implements Serializable {

	private static final long serialVersionUID = -4459702825178547603L;

	private String matchHistoryUri;
	private int profileIcon;
	private long summonerId;
	private String summonerName;

	public String getMatchHistoryUri() {
		return matchHistoryUri;
	}

	public int getProfileIcon() {
		return profileIcon;
	}

	public long getSummonerId() {
		return summonerId;
	}

	public String getSummonerName() {
		return summonerName;
	}

	@Override
	public String toString() {
		return getSummonerName();
	}
}