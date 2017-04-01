package adamzimnyy.com.leaguestats.activity;

import adamzimnyy.com.leaguestats.R;
import adamzimnyy.com.leaguestats.api.RetrofitBuilder;
import adamzimnyy.com.leaguestats.api.endpoint.StaticDataService;
import adamzimnyy.com.leaguestats.api.endpoint.SummonerService;
import adamzimnyy.com.leaguestats.model.realm.Champion;
import adamzimnyy.com.leaguestats.task.GetMostRecentMatchTask;
import adamzimnyy.com.leaguestats.util.*;
import adamzimnyy.com.leaguestats.model.riot.ChampionList;
import adamzimnyy.com.leaguestats.model.riot.Image;
import adamzimnyy.com.leaguestats.model.riot.Summoner;
import adamzimnyy.com.leaguestats.view.AddMatchDialog;
import adamzimnyy.com.leaguestats.view.ChampionItem;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.glomadrian.materialanimatedswitch.MaterialAnimatedSwitch;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.IItemAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import io.realm.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    FastItemAdapter fastAdapter;
    @BindView(R.id.search_bar)
    EditText searchBar;
    List<ChampionItem> championList = new ArrayList<>();
    @BindView(R.id.pin_switch)
    MaterialAnimatedSwitch pinSwitch;

    @BindView(R.id.tip_card)
    CardView tipCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPref.contains(Preference.SUMMONERS_NAME)) {
            Log.d("sharedPref", sharedPref.getString(Preference.SUMMONERS_NAME, "dupa"));
            findSummonerId();
        }
        tipCard.setVisibility(sharedPref.getBoolean(Preference.SHOW_PIN_TIP, false) ? View.VISIBLE : View.GONE);
        GridLayoutManager glm = new GridLayoutManager(MainActivity.this, 4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(glm);
        fastAdapter = new FastItemAdapter();
        fastAdapter.getItemAdapter().withComparator(new AlphabetComparatorAscending());
        fastAdapter.withFilterPredicate(new IItemAdapter.Predicate<ChampionItem>() {
            @Override
            public boolean filter(ChampionItem item, CharSequence constraint) {
                String[] parts = String.valueOf(constraint).split("\\|");
                String filter;
                if (parts.length > 1) {
                    filter = parts[1];
                } else {
                    filter = "";
                }
                Log.d("filter", Arrays.toString(parts));
                if (parts[0].equals("true") && filter.isEmpty()) {
                    return !item.isPinned();
                } else if (parts[0].equals("true")) {
                    return !item.getName().toLowerCase().startsWith(filter.toLowerCase())
                          /*  !filter.toLowerCase().contains(item.getRegion().toLowerCase()))*/ || !item.isPinned();
                } else {
                    return !item.getName().toLowerCase().startsWith(filter.toLowerCase()) /*&&
                            !filter.toLowerCase().contains(item.getRegion().toLowerCase())*/;
                }
            }
        });

        fastAdapter.withOnClickListener(new FastAdapter.OnClickListener() {
            @Override
            public boolean onClick(View v, IAdapter adapter, IItem item, int position) {
                ChampionItem ci = (ChampionItem) item;
                IntentHelper.startActivityIntent(MainActivity.this, ChampionActivity.class, ci.getKey());
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
                return false;
            }
        });
        fastAdapter.withOnLongClickListener(new FastAdapter.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v, IAdapter adapter, IItem item, int position) {
                ChampionItem ci = (ChampionItem) item;
                Realm realm = Realm.getDefaultInstance();
                Champion c = realm.where(Champion.class).contains("key", ci.getKey()).findFirst();
                realm.beginTransaction();
                c.setPinned(!c.isPinned());
                realm.commitTransaction();
                realm.close();
                ci.setPinned(!ci.isPinned());
                fastAdapter.notifyAdapterItemChanged(position);
                return false;
            }
        });

        pinSwitch.setOnCheckedChangeListener(new MaterialAnimatedSwitch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean b) {
                fastAdapter.filter(b + "|" + searchBar.getText().toString());
            }
        });
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fastAdapter.filter(pinSwitch.isChecked() + "|" + s.toString());
                fastAdapter.notifyAdapterDataSetChanged();
            }
        });
        ImageLoaderHelper.initialize(this);
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
             //   .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                championList = getChampionList();
                fastAdapter.add(championList);
                fastAdapter.notifyAdapterDataSetChanged();
            }
        }, 500);
        checkForLeagueUpdates();

//set our adapters to the RecyclerView
//we wrap our FastAdapter inside the ItemAdapter -> This allows us to chain adapters for more complex useCases
        recyclerView.setAdapter(fastAdapter);
    }

    private void findSummonerId() {
        SummonerService service = (SummonerService) RetrofitBuilder.getService(SummonerService.class, RetrofitBuilder.RIOT_BASE);
        String region = PreferenceManager.getDefaultSharedPreferences(this).getString(Preference.SERVER, "");
        String name = PreferenceManager.getDefaultSharedPreferences(this).getString(Preference.SUMMONERS_NAME, "");
        if (region.isEmpty()) return;
        service.byName(region, name, Riot.API_KEY).enqueue(new Callback<Map<String, Summoner>>() {
            @Override
            public void onResponse(Call<Map<String, Summoner>> call, Response<Map<String, Summoner>> response) {
                if (response.isSuccessful()) {
                    Map<String, Summoner> map = response.body();
                    if (map.size() != 1) return;
                    Summoner s = null;
                    for (Map.Entry<String, Summoner> e : map.entrySet()) {
                        s = e.getValue();
                    }

                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt(Preference.SUMMONERS_ID, (int) s.getId());
                    editor.apply();
                    Log.d("sharedPref", "saved ID: " + (int) s.getId());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Summoner>> call, Throwable t) {

            }
        });
    }

    /**
     * @return true if needs update
     */
    private void checkForLeagueUpdates() {
        new ChampionListTask().execute();
    }

    private List<ChampionItem> getChampionList() {
        Realm realm = Realm.getDefaultInstance();
        List<Champion> champions = realm.where(Champion.class).findAll();
        List<ChampionItem> items = new ArrayList<>();
        for (Champion c : champions) {
            ChampionItem item = new ChampionItem();
            item.setPinned(c.isPinned());
            item.setImage(c.getImage().getFull());
            item.setKey(c.getKey());
            item.setName(c.getName());
            items.add(item);
        }
        return items;
    }


    @OnClick(R.id.button_add_new_match)
    public void onAddNewMatch() {
        new AddMatchDialog().setActivity(this).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_toolmar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            IntentHelper.startActivityIntent(this, SettingsActivity.class);
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AlphabetComparatorAscending implements Comparator<ChampionItem>, Serializable {
        @Override
        public int compare(ChampionItem lhs, ChampionItem rhs) {
            return lhs.getKey().compareTo(rhs.getKey());
        }
    }

    @OnClick(R.id.close_tip)
    public void closeTip() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("show_pin_tip", false);
        editor.apply();
        tipCard.setVisibility(View.GONE);
    }


    public class ChampionListTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            final boolean[] needsUpdate = {false};
            StaticDataService service = (StaticDataService) RetrofitBuilder.getService(StaticDataService.class, RetrofitBuilder.RIOT_STATIC_DATA);
            Call<List<String>> call = service.versions(Riot.API_KEY);
            Response<List<String>> response = null;
            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response.isSuccessful()) {
                String latest = response.body().get(0);
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                if (!sharedPref.getString("lol_version", "").equals(latest)) {
                    needsUpdate[0] = true;
                    Log.d("version", "update needed");
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("lol_version", latest);
                    editor.commit();
                }
            }

            if (needsUpdate[0]) {
                Call<ChampionList> call2 = service.getChampionList("image", Riot.API_KEY);
                Response<ChampionList> response2 = null;
                try {
                    response2 = call2.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (response.isSuccessful()) {
                    Map<String, Champion> map = response2.body().getData();
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    for (Map.Entry<String, Champion> entry : map.entrySet()) {
                        Champion ec = entry.getValue();

                        Champion champion = realm.where(Champion.class).contains("key", entry.getValue().getKey()).findFirst();
                        if (champion == null) {
                            champion = realm.createObject(Champion.class, ec.getKey());
                            champion.setName(ec.getName());
                            champion.setId(ec.getId());
                            Log.d("champion", "Set id " + ec.getId() + " on " + ec.getName());
                            champion.setImage(realm.createObject(Image.class));
                            champion.getImage().setFull(ec.getImage().getFull());
                        }
                    }
                    realm.commitTransaction();
                    realm.close();
                }
            }
            return needsUpdate[0];
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if (bool) {
                championList = getChampionList();
                fastAdapter.clear();
                fastAdapter.add(championList);
                fastAdapter.notifyAdapterDataSetChanged();
            }
        }
    }


}


/*
    private void downloadChampionList() {
        StaticDataService service = (StaticDataService) RetrofitBuilder.getService(StaticDataService.class, RetrofitBuilder.RIOT_STATIC_DATA);
        Call<ChampionList> call = service.getChampionList("image", Riot.API_KEY);
        call.enqueue(new Callback<ChampionList>() {
            @Override
            public void onResponse(Call<ChampionList> call, Response<ChampionList> response) {
                if (response.isSuccessful()) {
                    Map<String, Champion> map = response.body().getData();
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    for (Map.Entry<String, Champion> entry : map.entrySet()) {
                        Champion ec = entry.getValue();

                        Champion champion = realm.where(Champion.class).contains("key", entry.getValue().getKey()).findFirst();
                        if (champion == null) {
                            champion = realm.createObject(Champion.class, ec.getKey());
                            champion.setName(ec.getName());
                            champion.setImage(realm.createObject(Image.class));
                            champion.getImage().setFull(ec.getImage().getFull());
                        }
                    }
                    realm.commitTransaction();
                    realm.close();
                }
            }

            @Override
            public void onFailure(Call<ChampionList> call, Throwable t) {

            }
        });
    }
*/