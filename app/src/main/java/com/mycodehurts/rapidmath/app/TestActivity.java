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
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TestActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "RapidMathPref";
    public int iLevel;
    public int iQuestion = 0;
    public int iTimeLeft = 0;
    public int bEnableTimer =0;

    String strTestType = new String();
    ArrayList<Question> lstQuestions = new ArrayList<Question>(10);
    Timer timer = new Timer();

    class Question
    {
        public String strQuestion;
        public String strChoices[] = new String[4];
        public int iAnswer;
        public int iQuestionType;
        public int iUserAnswer;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_test);

        if (savedInstanceState == null) {
            //getSupportFragmentManager().beginTransaction()
                    //.add(R.id.container, new PlaceholderFragment())
                    //.commit();
        }
        Bundle extras = getIntent().getExtras();
        if(extras != null) {

            iLevel = extras.getInt("iLevel");

            if(iLevel==0)
            {
                strTestType = "Easy";
                ((TextView)findViewById(R.id.txtTestType)).setTextColor(Color.rgb(0,128,0));


            }
            else if(iLevel ==1)
            {
                strTestType = "Medium";
                ((TextView)findViewById(R.id.txtTestType)).setTextColor(Color.rgb(255,140,0));


            }
            else
            {
                strTestType = "Hard";
                ((TextView)findViewById(R.id.txtTestType)).setTextColor(Color.rgb(255,0,0));

            }
            ((TextView)findViewById(R.id.txtTestType)).setText(strTestType);

            bEnableTimer = extras.getInt("enableTimer");


        }
        Button btn = (Button)findViewById(R.id.btnNextQuestion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnClicked(view);
            }
        });
        PrepareQuestions();
        NextQuestion();

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

    public void NextQuestion()
    {
        Question q = lstQuestions.get(iQuestion);

        ((TextView)findViewById(R.id.textQuestion)).setText(q.strQuestion);
        ((RadioButton)findViewById(R.id.answer1)).setText(q.strChoices[0]);
        ((RadioButton)findViewById(R.id.answer2)).setText(q.strChoices[1]);
        ((RadioButton)findViewById(R.id.answer3)).setText(q.strChoices[2]);
        ((RadioButton)findViewById(R.id.answer4)).setText(q.strChoices[3]);

        timer.cancel();
        timer.purge();
        if(iLevel==0 ||iLevel==1)
        {
            iTimeLeft = 60;
        }
        if(iLevel==2)
            iTimeLeft = 30;

        if(bEnableTimer==1) {
            timer = new Timer();
            timer.schedule(new TestTimer(), 0, 1000);
        }
        else
        {
            ((TextView)findViewById(R.id.txtTimeLeft)).setText("-:-");
        }

    }

    class TestTimer extends TimerTask {

        @Override
        public void run() {
            TestActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    iTimeLeft--;

                    ((TextView)findViewById(R.id.txtTimeLeft)).setText(""+iTimeLeft);
                    if(iTimeLeft<=10)
                    ((TextView)findViewById(R.id.txtTimeLeft)).setTextColor(Color.RED);
                    else
                        ((TextView)findViewById(R.id.txtTimeLeft)).setTextColor(Color.rgb(0,128,0));


                    if(iTimeLeft<=0)
                        onBtnClicked(findViewById(R.id.btnNextQuestion));

                }
            });
        }
    };

    public void PrepareQuestions()
    {
        iQuestion = 0;
        for (int i=0;i<10;i++)
        {
            switch (i)
            {
                case 0: //Addition
                case 8:
                {
                    Question q = new Question();
                    q.iQuestionType = 1;

                    if(iLevel == 0)
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(90)+10;
                        int i2 = r.nextInt(90)+10;
                        q.strQuestion = i1 + " + " + i2 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + (i1 + i2);

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + (i1 + i2+20);
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + (i1 + i2+10);
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + (i1 + i2-10);
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + (i1 + i2+1);

                    }
                    else if(iLevel == 1)
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(1000)+10;
                        int i2 = r.nextInt(1000)+10;
                        q.strQuestion = i1 + " + " + i2 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + (i1 + i2);

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + (i1 + i2+20);
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + (i1 + i2+10);
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + (i1 + i2-10);
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + (i1 + i2+1);


                    }
                    else
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(10000)+10;
                        int i2 = r.nextInt(10000)+10;
                        int i3 = r.nextInt(10000)+10;

                        q.strQuestion = i1 + " + " + i2 + " + "+ i3 +" = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + (i1 + i2+i3);

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + (i1 + i2+i3+20);
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + (i1 + i2+i3+10);
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + (i1 + i2+i3-10);
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + (i1 + i2+i3+1);

                    }
                    lstQuestions.add(q);
                }
                break;
                case 1: //substraction
                case 9:
                {
                    Question q = new Question();
                    q.iQuestionType = 1;

                    if(iLevel == 0)
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(90)+10;
                        int i2 = r.nextInt(90)+10;
                        q.strQuestion = i1 + " - " + i2 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + (i1 - i2);

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + (i1 - i2+20);
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + (i1 - i2+10);
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + (i1 - i2-10);
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + (i1 - i2+1);

                    }
                    else if(iLevel == 1)
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(1000)+10;
                        int i2 = r.nextInt(1000)+10;
                        q.strQuestion = i1 + " - " + i2 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + (i1 - i2);

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + (i1 - i2+20);
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + (i1 - i2+10);
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + (i1 - i2-10);
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + (i1 - i2+1);

                    }
                    else
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(10000)+10;
                        int i2 = r.nextInt(10000)+10;
                        int i3 = r.nextInt(10000)+10;

                        q.strQuestion = i1 + " - " + i2 + " - "+ i3 +" = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + (i1 - i2 - i3);

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + (i1 - i2 -i3 +20);
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + (i1 - i2-i3+10);
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + (i1 - i2-i3-10);
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + (i1 - i2-i3+1);

                    }
                    lstQuestions.add(q);
                }
                break;
                case 2: //Multiply
                {
                    Question q = new Question();
                    q.iQuestionType = 2;

                    if(iLevel == 0)
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(90)+10;
                        int i2 = r.nextInt(90)+10;
                        q.strQuestion = i1 + " * " + i2 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + (i1 * i2);

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + (i1 * i2 +20);
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + (i1 * i2+10);
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + (i1 * i2-10);
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + (i1 * i2+1);

                    }
                    else if(iLevel == 1)
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(1000)+10;
                        int i2 = r.nextInt(1000)+10;
                        q.strQuestion = i1 + " * " + i2 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + ((long)i1 * i2);

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + ((long)i1 * i2 +20);
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + ((long)i1 * i2+10);
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + ((long)i1 * i2-10);
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + ((long)i1 * i2+1);

                    }
                    else
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(10000)+10;
                        int i2 = r.nextInt(10000)+10;
                        int i3 = r.nextInt(10000)+10;

                        q.strQuestion = i1 + " * " + i2 + " * "+ i3 +" = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + ((long)i1 * i2 * i3);

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + ((long)i1 * i2 *i3 +20);
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + ((long)i1 * i2*i3 +10);
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + ((long)i1 * i2*i3 -10);
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + ((long)i1 * i2*i3 +1);
                    }
                    lstQuestions.add(q);
                }
                break;
                case 3: //Division
                {
                    Question q = new Question();
                    q.iQuestionType = 3;

                    if(iLevel == 0)
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(90)+10;
                        int i2 = r.nextInt(90)+10;
                        q.strQuestion = i1 + " / " + i2 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + String.format("%.3f", (1.0*i1 / i2));

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + String.format("%.3f",(1.0*i1 / i2 +20));
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + String.format("%.3f",(1.0*i1 / i2 +10));
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + String.format("%.3f",(1.0*i1 / i2 -10));
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + String.format("%.3f",(1.0*i1 / i2 +1));

                    }
                    else if(iLevel == 1)
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(1000)+10;
                        int i2 = r.nextInt(1000)+10;
                        q.strQuestion = i1 + " / " + i2 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + String.format("%.3f",(1.0*i1 / i2));

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + String.format("%.3f",(1.0*i1 / i2 +20));
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + String.format("%.3f",(1.0*i1 / i2 +10));
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + String.format("%.3f",(1.0*i1 / i2 -10));
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + String.format("%.3f",(1.0*i1 / i2 +1));

                    }
                    else
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(10000)+10;
                        int i2 = r.nextInt(10000)+10;
                        q.strQuestion = i1 + " / " + i2 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + String.format("%.3f",(1.0*i1 / i2));

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + String.format("%.3f",(1.0*i1 / i2 +20));
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + String.format("%.3f",(1.0*i1 / i2 +10));
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + String.format("%.3f",(1.0*i1 / i2 -10));
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + String.format("%.3f",(1.0*i1 / i2 +1));

                    }
                    lstQuestions.add(q);
                }
                break;
                case 4: //Squares
                {
                    Question q = new Question();
                    q.iQuestionType = 4;

                    if(iLevel == 0)
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(10)+1;
                        q.strQuestion = i1 + " * " + i1 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + ((long)i1 * i1);

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + ((long)i1 * i1 +20);
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + ((long)i1 * i1 +10);
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + ((long)i1 * i1 -10);
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + ((long)i1 * i1 +1);

                    }
                    else if(iLevel == 1)
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(50)+10;
                        q.strQuestion = i1 + " * " + i1 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + ((long)i1 * i1);

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + ((long)i1 * i1 +20);
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + ((long)i1 * i1 +10);
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + ((long)i1 * i1 -10);
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + ((long)i1 * i1 +1);

                    }
                    else
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(100)+10;

                        q.strQuestion = i1 + " * " + i1 +" = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + ((long)i1 * i1);

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + ((long)i1 * i1 +20);
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + ((long)i1 * i1 +10);
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + ((long)i1 * i1 -10);
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + ((long)i1 * i1 +1);

                    }
                    lstQuestions.add(q);
                }
                break;
                case 5: //Cubes
                {
                    Question q = new Question();
                    q.iQuestionType = 4;

                    if(iLevel == 0)
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(10)+1;
                        q.strQuestion = i1 + " * " + i1 + " * " + i1 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + ((long)i1 * i1 * i1);

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + ((long)i1 * i1*i1 +20);
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + ((long)i1 * i1*i1 +10);
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + ((long)i1 * i1*i1 -10);
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + ((long)i1 * i1*i1 +1);

                    }
                    else if(iLevel == 1)
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(50)+10;
                        q.strQuestion = i1 + " * " + i1 + " * " + i1 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + ((long)i1 * i1 *i1 );

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + ((long)i1 * i1*i1 +20);
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + ((long)i1 * i1*i1 +10);
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + ((long)i1 * i1*i1 -10);
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + ((long)i1 * i1*i1 +1);

                    }
                    else
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(100)+10;

                        q.strQuestion = i1 + " * " + i1 + " * " + i1 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + ((long)i1 * i1 * i1);

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + ((long)i1 * i1*i1 +20);
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + ((long)i1 * i1*i1 +10);
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + ((long)i1 * i1*i1 -10);
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + ((long)i1 * i1*i1 +1);

                    }
                    lstQuestions.add(q);
                }
                break;
                case 6: //Percentage
                {
                    Question q = new Question();
                    q.iQuestionType = 6;

                    if(iLevel == 0)
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(90)+10;
                        int i2 = r.nextInt(100);
                        q.strQuestion = i2 + " % of " + i1 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + String.format("%.3f",(1.0*i1*i2/100));

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + String.format("%.3f",(1.0*i1*i2/100 +20));
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + String.format("%.3f",(1.0*i1*i2/100 +10));
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + String.format("%.3f",(1.0*i1*i2/100 -10));
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + String.format("%.3f",(1.0*i1*i2/100 +1));

                    }
                    else if(iLevel == 1)
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(1000)+10;
                        int i2 = r.nextInt(100);
                        q.strQuestion = i2 + " % of " + i1 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + String.format("%.3f",(1.0*i1 * i2/100));

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + String.format("%.3f",(1.0*i1*i2/100 +20));
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + String.format("%.3f",(1.0*i1*i2/100 +10));
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + String.format("%.3f",(1.0*i1*i2/100 -10));
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + String.format("%.3f",(1.0*i1*i2/100 +1));

                    }
                    else
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(10000)+10;
                        int i2 = r.nextInt(100);

                        q.strQuestion = i2 + " % of " + i1 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + String.format("%.3f",(1.0*i1 % i2 /100));

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + String.format("%.3f",(1.0*i1*i2/100 +20));
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + String.format("%.3f",(1.0*i1*i2/100 +10));
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + String.format("%.3f",(1.0*i1*i2/100 -10));
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + String.format("%.3f",(1.0*i1*i2/100 +1));

                    }
                    lstQuestions.add(q);
                }
                break;
                case 7: //Square Roots
                {
                    Question q = new Question();
                    q.iQuestionType = 7;

                    if(iLevel == 0)
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(90)+10;
                        q.strQuestion = " Square Root of " + i1 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + String.format("%.3f",Math.sqrt(i1));

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + String.format("%.3f",(Math.sqrt(i1) +20));
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + String.format("%.3f",(Math.sqrt(i1)  +10));
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + String.format("%.3f",(Math.sqrt(i1)  -10));
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + String.format("%.3f",(Math.sqrt(i1)  +1));

                    }
                    else if(iLevel == 1)
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(1000)+10;
                        q.strQuestion = " Square Root of " + i1 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + String.format("%.3f",Math.sqrt(i1));

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + String.format("%.3f",(Math.sqrt(i1) +20));
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + String.format("%.3f",(Math.sqrt(i1)  +10));
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + String.format("%.3f",(Math.sqrt(i1)  -10));
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + String.format("%.3f",(Math.sqrt(i1)  +1));

                    }
                    else
                    {
                        Random r =new Random();
                        int i1 = r.nextInt(10000)+10;

                        q.strQuestion = " Square Root of " + i1 + " = ?";

                        q.iAnswer = r.nextInt(4);
                        q.strChoices[q.iAnswer] = " " + String.format("%.3f",Math.sqrt(i1));

                        if(q.iAnswer!=0)
                            q.strChoices[0] = " " + String.format("%.3f",(Math.sqrt(i1) +20));
                        if(q.iAnswer!=1)
                            q.strChoices[1] = " " + String.format("%.3f",(Math.sqrt(i1)  +10));
                        if(q.iAnswer!=2)
                            q.strChoices[2] = " " + String.format("%.3f",(Math.sqrt(i1)  -10));
                        if(q.iAnswer!=3)
                            q.strChoices[3] = " " + String.format("%.3f",(Math.sqrt(i1)  +1));

                    }
                    lstQuestions.add(q);
                }
                break;
            }
        }


    }
    public void onBtnClicked(View v){
        if(v.getId() == R.id.btnNextQuestion) {

            //Store User's answer first
            RadioGroup chkSex = (RadioGroup)findViewById(R.id.groupAnswers);
            Question q = lstQuestions.get(iQuestion);

            q.iUserAnswer = chkSex.indexOfChild(findViewById(chkSex.getCheckedRadioButtonId()));
            Log.i("JS: ","Answer: "+q.iAnswer+" Selection: "+q.iUserAnswer);

            //Increase counter and check if results can be displayed.
            iQuestion ++;
            if(iQuestion>=10)
            {

                timer.cancel();
                timer.purge();

                int iPass = 0;
                String strFailedQ = new String();
                for(int i=0;i<10;i++)
                {
                    //Log.i("JS: "," "+lstQuestions.get(i).iUserAnswer + " "+lstQuestions.get(i).iAnswer);
                    if(lstQuestions.get(i).iUserAnswer == lstQuestions.get(i).iAnswer) {
                        iPass++;
                    }
                    else
                    {
                        if(lstQuestions.get(i).iUserAnswer<0) {
                            strFailedQ += "\n\n Q" + (i + 1) + ". " + lstQuestions.get(i).strQuestion + "\n Your Answer: "
                                    + " "
                                    + "\n Correct Answer" + lstQuestions.get(i).strChoices[lstQuestions.get(i).iAnswer];
                        }
                        else
                        {
                            strFailedQ += "\n\n Q" + (i + 1) + ". " + lstQuestions.get(i).strQuestion + "\n Your Answer: "
                                    + lstQuestions.get(i).strChoices[lstQuestions.get(i).iUserAnswer]
                                    + "\n Correct Answer" + lstQuestions.get(i).strChoices[lstQuestions.get(i).iAnswer];
                        }
                    }
                }
                int iPercent = iPass*100/10;
                //Load next activity.
                Intent intent = new Intent(this, ShowResult.class);
                intent.putExtra("iPercent",iPercent);
                intent.putExtra("strFailedQ",strFailedQ);

                //String strTestType = new String();
                /*if(iLevel==0)
                    strTestType = "Easy";
                else if(iLevel ==1)
                    strTestType = "Medium";
                else
                    strTestType = "Hard";
*/
                intent.putExtra("TestType",strTestType);
                startActivity(intent);
                return;
            }

            //Fetch next question.
            NextQuestion();

            //Update UI
            ((TextView)findViewById(R.id.textView2)).setText("Question " + (iQuestion + 1) + " of 10");
            chkSex.clearCheck();

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
