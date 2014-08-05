/*
Copyright (c) 2014 Vishalsinh Jhala (myCodeHurts), Techie Tux.

        Permission is hereby granted, free of charge, to any person obtaining a copy
        of this software and associated documentation files (the "Software"), to deal
        in the Software without restriction, including without limitation the rights
        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
        copies of the Software, and to permit persons to whom the Software is
        furnished to do so, subject to the following conditions:

        The above copyright notice and this permission notice shall be included in
        all copies or substantial portions of the Software.

        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
        THE SOFTWARE.

*/

package com.mycodehurts.rapidmath.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.analytics.GoogleAnalytics;

public class ProfilePage extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    public static final String PREFS_NAME = "RapidMathPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences store = getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor = store.edit();
        String strProfileName = store.getString("strProfileName","NotFound");

        if(strProfileName.compareToIgnoreCase("NotFound")==0)
        {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_profile_page);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        ((RMApplication) getApplication()).getTracker(RMApplication.TrackerName.APP_TRACKER);

    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);




    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);

    }


    @Override
    public void onNavigationDrawerItemSelected(int i) {
        // update the main content by replacing fragments
        Log.i("JS", "onNavigationDrawerItemSelected:"+i);

        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(i)
        {
            case 0: // Profile page
            {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ProfilePageFragment.newInstance(i))
                        .commit();
                mTitle = "Profile";
            }
            break;
            case 2:// Practice Test
            {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, SelectTestType.newInstance(i))
                        .commit();
                mTitle = "Practice";

            }
            break;
            case 3://About Page
            {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, AboutPage.newInstance(i))
                        .commit();
                mTitle = "About";

            }
            break;

        }
    }

    @Override
    public void onNavigationDrawerChildItemSelected(int i, int i2) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        if(i==1) //Learn
        {
            switch(i2)
            {

                case 0: // Addition
                {
                    fragmentManager.beginTransaction()
                        .replace(R.id.container, LearnFragment.newInstance("additionsinglecol",5))
                        .commit();
                    mTitle = "Addition Single Column";
                    break;
                }
                case 1: // Addition Vocal
                {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, LearnFragment.newInstance("additionvocal",1))
                            .commit();
                    mTitle = "Addition Vocal";

                    break;
                }
                case 2: // Substraction COmpliment
                {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, LearnFragment.newInstance("subcompliment",6))
                            .commit();
                    mTitle = "Subtraction Compliment";
                    break;
                }

                case 3:
                {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, LearnFragment.newInstance("submultiply",4))
                            .commit();
                    mTitle = "Subtraction Multiply";
                    break;
                }
                case 4:
                {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, LearnFragment.newInstance("multibutterfly",3))
                            .commit();
                    mTitle = "Multiply Butterfly";
                    break;
                }
                case 5:
                {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, LearnFragment.newInstance("multiline",3))
                            .commit();
                    mTitle = "Multiply Line";
                    break;
                }
                case 6:
                {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, LearnFragment.newInstance("divhillclimb",3))
                            .commit();
                    mTitle = "Division Hill Climb";
                    break;
                }

                case 7:
                {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, LearnFragment.newInstance("sqgeneral",4))
                            .commit();
                    mTitle = "Square General";
                    break;
                }
                case 8:
                {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, LearnFragment.newInstance("sqendw5",3))
                            .commit();
                    mTitle = "Square ending with 5";
                    break;
                }
                case 9:
                {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, LearnFragment.newInstance("cubegeneral",2))
                            .commit();
                    mTitle = "Cube General";
                    break;
                }
                case 10:
                {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, LearnFragment.newInstance("cubeequaldigit",2))
                            .commit();
                    mTitle = "Cube Equal digits";
                    break;
                }
                case 11:
                {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, LearnFragment.newInstance("percent",5))
                            .commit();
                    mTitle = "Percent";
                    break;
                }
                case 12:
                {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, LearnFragment.newInstance("squareroot",3))
                            .commit();
                    mTitle = "Square Root";
                    break;
                }

            }
        }
    }

    public void onSectionAttached(int number) {
//        switch (number) {
//            case 1:
//                mTitle = getString(R.string.My_Profile);
//                break;
//            case 2:
//                mTitle = getString(R.string.Addition);
//                break;
//            case 3:
//                mTitle = getString(R.string.Substraction);
//                break;
//        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //Ask the user if they want to quit
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Quit")
                    .setMessage("Really Quit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Stop the activity
                            ProfilePage.this.finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.profile_page, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
