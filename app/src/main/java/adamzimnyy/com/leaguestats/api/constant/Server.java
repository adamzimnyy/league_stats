/*
 * Copyright 2015 Taylor Caldwell
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

package adamzimnyy.com.leaguestats.api.constant;

import java.util.HashMap;
import java.util.Map;

public enum Server {
	BR("BR1", "br"),
	EUNE("EUN1", "eune"),
	EUW("EUW1", "euw"),
	JP("JP1", "jp"),
	KR("KR", "kr"),
	LAN("LA1", "lan"),
	LAS("LA2", "las"),
	NA("NA1", "na"),
	OCE("OC1", "oce"),
	PBE("PBE1", "pbe"),
	RU("RU", "ru"),
	TR("TR1", "tr");

	private String location;
	private String region;

	Server(String location, String region) {
		this.location = location;
		this.region = region;
	}

    private static final Map<String,Server> regionMap;
    private static final Map<String,Server> locationMap;

    static {
        regionMap = new HashMap<String,Server>();
        locationMap = new HashMap<String,Server>();
        for (Server v : Server.values()) {
            regionMap.put(v.region, v);
            locationMap.put(v.location,v);
        }
    }
    public static Server findByRegion(String region) {
        return regionMap.get(region);
    }

    public static Server findByLocation(String location) {
        return locationMap.get(location);
    }

	public String getLocation() {
		return location;
	}

	public String getRegion() {
		return region;
	}

	@Override
	public String toString() {
		return getRegion();
	}
}