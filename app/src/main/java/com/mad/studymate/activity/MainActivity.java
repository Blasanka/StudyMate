package com.mad.studymate.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.mad.studymate.R;
import com.mad.studymate.fragment.AttemptedQuizesFragment;
import com.mad.studymate.fragment.HomeFragment;
import com.mad.studymate.fragment.NotesFragment;
import com.mad.studymate.fragment.QuizFragment;
import com.mad.studymate.fragment.StudySessionFragment;
import com.mad.studymate.fragment.TasksFragment;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
        NotesFragment.OnFragmentInteractionListener, QuizFragment.OnFragmentInteractionListener,
        TasksFragment.OnFragmentInteractionListener, AttemptedQuizesFragment.OnFragmentInteractionListener,
        StudySessionFragment.OnFragmentInteractionListener {

    private android.support.v7.app.ActionBar actionbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //String appName = getString(R.string.app_name);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
            actionbar.setTitle(R.string.nav_home);
            navigationView.setCheckedItem(R.id.action_home);
        }

        //nav drawer
        handleNavigationSelect();
    }

    public void handleNavigationSelect() {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        switch (menuItem.getItemId()) {
                            case R.id.action_home:
                                fragmentTransaction.replace(R.id.content_frame, new HomeFragment()).commit();
                                actionbar.setTitle(R.string.nav_home);
                                break;
                            case R.id.action_quizes:
                                fragmentTransaction.replace(R.id.content_frame, new QuizFragment()).commit();
                                actionbar.setTitle(R.string.nav_quizes);
                                break;
                            case R.id.action_attempted:
                                fragmentTransaction.replace(R.id.content_frame, new AttemptedQuizesFragment()).commit();
                                actionbar.setTitle(R.string.nav_attempted);
                                break;
                            case R.id.action_study_sessions:
                                fragmentTransaction.replace(R.id.content_frame, new StudySessionFragment()).commit();
                                actionbar.setTitle(R.string.nav_study);
                                break;
                            case R.id.action_notes:
                                fragmentTransaction.replace(R.id.content_frame, new NotesFragment()).commit();
                                actionbar.setTitle(R.string.nav_notes);
                                break;
                            case R.id.action_tasks:
                                fragmentTransaction.replace(R.id.content_frame, new TasksFragment()).commit();
                                actionbar.setTitle(R.string.nav_tasks);
                                break;
                        }
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        mDrawerLayout.closeDrawer(GravityCompat.START);

                        return true;
                    }
                });

        //TODO: close the drawer when opened if nav menu button is clicked(toggle)
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //search icon functioning
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        // Associate searchable configuration with the SearchView
        // SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        // searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
