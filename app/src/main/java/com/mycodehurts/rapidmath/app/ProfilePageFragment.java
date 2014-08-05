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

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.plus.PlusShare;


public class ProfilePageFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ProfilePageFragment newInstance(int sectionNumber) {
        ProfilePageFragment fragment = new ProfilePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfilePageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.fragment_profile_page, container, false);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
        RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
         Button playStore = (Button) rootView.findViewById(R.id.btnGooglePlay);

        {
            SharedPreferences store = getActivity().getSharedPreferences("RapidMathPref",0);
            SharedPreferences.Editor editor = store.edit();
            String strProfileName = store.getString("strStarRating","NotFound");

            if(strProfileName.compareToIgnoreCase("NotFound")==0)
            {
                ratingBar.setVisibility(View.VISIBLE);
                playStore.setVisibility(View.GONE);

            }
            else
            {
                ratingBar.setVisibility(View.GONE);
                playStore.setVisibility(View.VISIBLE);

                playStore.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Uri uri = Uri.parse("market://details?id=com.mycodehurts.rapidmath.app");
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            //Toast.makeText(this, "Could not launch Play store", Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
            String strProfileNam = store.getString("strProfileName","NotFound");

            if(strProfileNam.compareToIgnoreCase("NotFound")!=0)
            {
                TextView txtView = (TextView) rootView.findViewById(R.id.txtWelcomeName);
                txtView.setText("Welcome "+strProfileNam);

            }
        }

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                ((RMApplication) getActivity().getApplication()).getTracker(RMApplication.TrackerName.APP_TRACKER).send(
                        new HitBuilders.EventBuilder()
                                .setCategory("Rating")
                                .setAction("StarRating")
                                .setValue((long) rating)
                                .build());


                Log.i("JS:", "Rating" + rating);

                ratingBar.setVisibility(View.GONE);
                Button playStore = (Button) getActivity().findViewById(R.id.btnGooglePlay);

                playStore.setVisibility(View.VISIBLE);

                SharedPreferences store = getActivity().getSharedPreferences("RapidMathPref",0);
                SharedPreferences.Editor editor = store.edit();
                String strProfileName = store.getString("strStarRating","NotFound");

                if(strProfileName.compareToIgnoreCase("NotFound")==0)
                {

                    editor.putString("strStarRating"," "+ rating);
                    editor.commit();


                }


            }
        });
        Button gplus = (Button) rootView.findViewById(R.id.btnFB);

        gplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new PlusShare.Builder(getActivity())
                        .setType("text/plain")
                        .setText("RapidMath is a collection of techniques that help improve basic math skills. Download from here - \n" +
                                "https://play.google.com/store/apps/details?id=com.mycodehurts.rapidmath.app\n")
                        .setContentUrl(Uri.parse("https://developers.google.com/+/"))
                        .getIntent();

                startActivityForResult(shareIntent, 0);
            }
        });


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((ProfilePage) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}


