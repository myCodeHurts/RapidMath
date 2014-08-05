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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;

public class HomeActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "RapidMathPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {
            //getSupportFragmentManager().beginTransaction()
                    //.add(R.id.container, new PlaceholderFragment())
                    //.commit();
        }

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

    public void onBtnClicked(View v){
        if(v.getId() == R.id.btnGetMeStarted) {

            //handle the click here
            EditText txtProfileName = (EditText)findViewById(R.id.txtProfileName);
            RadioGroup chkSex = (RadioGroup)findViewById(R.id.sexGroup);
            Spinner spnrAge = (Spinner)findViewById(R.id.spinnerAge);

            if(txtProfileName.getText().toString().length()==0)
            {
                Toast.makeText(this.getApplicationContext(),"Missing Profile Name",3).show();
                txtProfileName.requestFocus();
                return;
            }
            if( chkSex.getCheckedRadioButtonId()==-1)
            {
                Toast.makeText(this.getApplicationContext(),"Male / Female - Choose one!",3).show();
                return;

            }
            SharedPreferences store = getSharedPreferences(PREFS_NAME,0);
            SharedPreferences.Editor editor = store.edit();
            editor.putString("strProfileName",txtProfileName.getText().toString());
            if(chkSex.getCheckedRadioButtonId()==R.id.sexMale)
                editor.putString("strSex","Male");
            else
                editor.putString("strSex","Female");
            editor.putInt("iAgeGroup",spnrAge.getSelectedItemPosition());
            editor.commit();


            ((RMApplication) getApplication()).getTracker(RMApplication.TrackerName.APP_TRACKER).send(
                    new HitBuilders.EventBuilder()
                            .setCategory("Registration")
                            .setAction(chkSex.getCheckedRadioButtonId()==R.id.sexMale?"Male":"Female")
                            .setValue(spnrAge.getSelectedItemPosition())
                            .build());

            //Load next activity.
            Intent intent = new Intent(this, ProfilePage.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }
    }

}
