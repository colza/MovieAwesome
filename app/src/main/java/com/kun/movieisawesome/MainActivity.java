package com.kun.movieisawesome;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kun.movieisawesome.model.ModelConfigImage;
import com.kun.movieisawesome.model.ModelGeneral;
import com.kun.movieisawesome.model.ModelMovie;
import com.kun.movieisawesome.model.ModelPeople;
import com.kun.movieisawesome.model.ModelTV;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ItemFragment.OnListFragmentInteractionListener, FragmentManager.OnBackStackChangedListener , MaterialSearchView.OnQueryTextListener {
    public static ModelConfigImage sModelConfigImage;
    private MaterialSearchView mSearchView;
    private Menu mMenu;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mToggle);
        mToggle.syncState();

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        NetworkRequest.fetchRemoteJsonAndSaveIntoPref(this, Constants.RESTFUL_GET_CONFIG, new String[]{Constants.RESP_JSON_KEY_IMAGES}, Constants.PREF_CONFIG_IMAGE);
        NetworkRequest.fetchGenreList(this, Constants.RESTFUL_GET_MOVIE_GENRE_LIST, Constants.PREF_MOVIE_GENRE_LIST);
        NetworkRequest.fetchGenreList(this, Constants.RESTFUL_GET_TV_GENRE_LIST, Constants.PREF_TV_GENRE_LIST);
    }

    @Override
    public void onBackStackChanged() {
        MyFragment myFragment = (MyFragment) getSupportFragmentManager().findFragmentById(R.id.main_content);
        setTitle(myFragment.getTitle());
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            showToolbarBackButton(true);
        }else{
            showToolbarBackButton(false);
        }

        switchSearchToggle(myFragment);
    }

    private void switchSearchToggle(MyFragment myFragment){
        if( myFragment instanceof DetailFragment){
            updateOptionMenu().findItem(R.id.search).setVisible(false);
        }else{
            updateOptionMenu().findItem(R.id.search).setVisible(true);
        }
    }

    private void showToolbarBackButton(Boolean bool){
        if( bool ){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }else{
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mToggle.syncState();
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        mMenu = menu;
        MenuItem item = menu.findItem(R.id.search);
        mSearchView.setMenuItem(item);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        String className = null;
        if (id == R.id.nav_movie) {
            className = ModelMovie.class.getName();
        } else if (id == R.id.nav_tv) {
            className = ModelTV.class.getName();
        } else if (id == R.id.nav_people) {
            className = ModelPeople.class.getName();
        }

        ItemFragment itemFragment = ItemFragment.newInstance(className, "");
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, itemFragment, Constants.TAG_FRAG_REQLIST).commit();
        setTitle(itemFragment.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Fragment myFragment = getSupportFragmentManager().findFragmentById(R.id.main_content);
        if( myFragment instanceof ItemFragment){
            ItemFragment currentItemFragment = (ItemFragment) myFragment;
            if( currentItemFragment.getTag().equals(Constants.TAG_FRAG_SEARCH)){
                // update search result
                currentItemFragment.setmQuery(query);
                currentItemFragment.startFirstRequest();
            }
            else{
                // create a new search fragment
                ItemFragment itemFragment = ItemFragment.newInstance(currentItemFragment.getmModelClassName(), query);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(Constants.TAG_FRAG_SEARCH);
                ft.add(R.id.main_content, itemFragment, Constants.TAG_FRAG_SEARCH).commit();
            }
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onListFragmentInteraction(ModelGeneral modelGeneral) {
        // click on item in List, launch detail fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailFragment detailFragment = DetailFragment.newInstance(modelGeneral);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(modelGeneral.getShowTitle());
        transaction.add(R.id.main_content, detailFragment).commit();
    }

    private Menu updateOptionMenu(){
        if( mMenu == null)
            onPrepareOptionsMenu(mMenu);

        return mMenu;
    }

    public static ModelConfigImage getsModelConfigImage(Context context) {
        if (sModelConfigImage == null) {
            String configImage = PreferenceManager.getDefaultSharedPreferences(context).getString(Constants.PREF_CONFIG_IMAGE, null);
            if (configImage != null) {
                Moshi moshi = new Moshi.Builder().build();
                JsonAdapter<ModelConfigImage> jsonAdapter = moshi.adapter(ModelConfigImage.class);
                try {
                    sModelConfigImage = jsonAdapter.fromJson(configImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sModelConfigImage;
    }
}
