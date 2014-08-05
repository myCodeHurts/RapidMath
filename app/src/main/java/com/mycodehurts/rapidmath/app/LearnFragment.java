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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LearnFragment extends Fragment  {





    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
//    private static final String ARG_SECTION_NUMBER = "section_number";
    //private Handler handler;
    int iCounter;
    ImageView iv;
    //String m_strDisplayName;
    String m_strID;
    int m_iVideoFrames;
    TextView txtImageNbr;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LearnFragment newInstance( String strID, int iVideoFrames) {
        LearnFragment fragment = new LearnFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//        fragment.setArguments(args);
        //fragment.m_strDisplayName = strDisplayName;
        fragment.m_strID = strID;
        fragment.m_iVideoFrames = iVideoFrames;

        return fragment;
    }

    public LearnFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();

        iCounter = 0;
        //handler = new Handler();
        //handler.postDelayed(runnable, 2000);

    }

    @Override
    public void onStop() {
        super.onStop();
        iCounter = -1;


    }

    @Override
    public void onPause() {
        super.onPause();
        iCounter = -1;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_learn, container, false);


        TextView textView = (TextView) rootView.findViewById(R.id.txtTechnique);
        iv = (ImageView)rootView.findViewById(R.id.imageVideoView);

        txtImageNbr = (TextView) rootView.findViewById(R.id.txtImageNumber);

        iCounter = 0;
        txtImageNbr.setText(1 +" of "+ m_iVideoFrames + ". (Swipe to Change)");

        String str = m_strID+(iCounter+1);
        int id = getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + str, null, null);

        iv.setImageResource(id);

        iv.setOnTouchListener(new OnSwipeTouchListener(this.getActivity()) {
            public void onSwipeTop() {
                //Toast.makeText(getActivity(), "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                //Toast.makeText(getActivity(), "right", Toast.LENGTH_SHORT).show();
                iCounter++;
                if(iCounter>= m_iVideoFrames)
                    iCounter =0;
                String str = m_strID+(iCounter+1);
                int id = getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + str, null, null);
                iv.setImageResource(id);
                txtImageNbr.setText(iCounter+1 +" of "+ m_iVideoFrames + ". (Swipe to Change)");


            }
            public void onSwipeRight() {
                iCounter--;

                if(iCounter<0)
                    iCounter =m_iVideoFrames -1;
                String str = m_strID+(iCounter+1);
                int id = getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + str, null, null);
                iv.setImageResource(id);
                txtImageNbr.setText(iCounter+1 +" of "+ m_iVideoFrames + ". (Swipe to Change)");
            }
            public void onSwipeBottom() {
                //Toast.makeText(getActivity(), "bottom", Toast.LENGTH_SHORT).show();
            }

            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });



        StringBuffer sb = new StringBuffer();

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getResources().getAssets().open(m_strID+".html"), "UTF-8"));

            // do reading, usually loop until end of file reading

            String mLine = reader.readLine();
            while (mLine != null) {
                sb.append(mLine );
                sb.append("\n");
                mLine = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            //log the exception
        }


        textView.setText(Html.fromHtml( sb.toString()));

        Log.i("JS","CreateView" );


        return rootView;
    }
    /*private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if(iCounter>=0)
            {
                String str = m_strID+(iCounter+1);
                int id = getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + str, null, null);

                iv.setImageResource(id);
                iCounter++;
                if(iCounter>m_iVideoFrames-1)
                    iCounter=0;
                handler.postDelayed(this, 2000);
            }
        }
    };*/


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ((ProfilePage) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }



}


