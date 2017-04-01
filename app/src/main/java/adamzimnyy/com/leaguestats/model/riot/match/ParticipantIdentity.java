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

public class ParticipantIdentity extends RealmObject implements Serializable {

	private static final long serialVersionUID = 7750317217073991764L;

	private int participantId;
	private MatchPlayer player;

	public int getParticipantId() {
		return participantId;
	}

	public MatchPlayer getPlayer() {
		return player;
	}

	@Override
	public String toString() {
		return getPlayer().toString();
	}
}