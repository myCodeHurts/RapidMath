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
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AboutPage extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AboutPage newInstance(int sectionNumber) {
        AboutPage fragment = new AboutPage();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AboutPage() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_about, container, false);

        {
            TextView link = (TextView) rootView.findViewById(R.id.visitkickstarter);
            String linkText = "<a href='https://www.kickstarter.com/projects/1373995643/rapidmath-educational-poster-for-rapid-mental-math'>Kickstarter Campaign</a> ";
            link.setText(Html.fromHtml(linkText));
            link.setMovementMethod(LinkMovementMethod.getInstance());
        }

        {
            TextView link = (TextView) rootView.findViewById(R.id.buyrapidmath);
            String linkText = "<a href='http://techessentials.myshopify.com/'>Buy Rapidmath Material</a> ";
            link.setText(Html.fromHtml(linkText));
            link.setMovementMethod(LinkMovementMethod.getInstance());
        }
        {
            TextView link = (TextView) rootView.findViewById(R.id.jhalaLink);
            String linkText = "<a href='https://plus.google.com/+VishalsinhJhala/posts'>Vishalsinh Jhala</a> ";
            link.setText(Html.fromHtml(linkText));
            link.setMovementMethod(LinkMovementMethod.getInstance());
        }
        {
            TextView link = (TextView) rootView.findViewById(R.id.githubLink);
            String linkText = "<a href='https://github.com/myCodeHurts/RapidMath'>Download Source</a> ";
            link.setText(Html.fromHtml(linkText));
            link.setMovementMethod(LinkMovementMethod.getInstance());
        }



        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));

//        Button btnStartTest = (Button)rootView.findViewById(R.id.btnStartTest);
//        btnStartTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                RadioGroup chkSex = (RadioGroup)rootView.findViewById(R.id.testDifficultLevel);
//
//                if( chkSex.getCheckedRadioButtonId()==-1)
//                {
//
//                }
//                int iLevel = -1;
//                if( chkSex.getCheckedRadioButtonId() == R.id.testEasy )
//                    iLevel = 0;
//                else if (chkSex.getCheckedRadioButtonId() == R.id.testMedium)
//                    iLevel = 1;
//                else if(chkSex.getCheckedRadioButtonId() == R.id.testDifficult)
//                    iLevel = 2;
//
//                Intent intent = new Intent(rootView.getContext(), TestActivity.class);
//                intent.putExtra("iLevel",iLevel);
//                startActivity(intent);
//            }
//        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((ProfilePage) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}


