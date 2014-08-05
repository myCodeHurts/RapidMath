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
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewAdapter extends BaseExpandableListAdapter {

    public ArrayList<String> groupItem, tempChild;
    public ArrayList<Object> Childtem = new ArrayList<Object>();
    public LayoutInflater minflater;
    public Activity activity;
    private final Context context;

    public NewAdapter(Context context,ArrayList<String> grList, ArrayList<Object> childItem) {
        this.context = context;
        groupItem = grList;
        this.Childtem = childItem;
    }

    public void setInflater(LayoutInflater mInflater, Activity act) {
        this.minflater = mInflater;
        activity = act;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        tempChild = (ArrayList<String>) Childtem.get(groupPosition);
        TextView text = null;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_node,
                    null);
        }
        text = (TextView) convertView.findViewById(R.id.child_textview);
        text.setTypeface(null, Typeface.BOLD);

        text.setText(tempChild.get(childPosition));
//		convertView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(activity, tempChild.get(childPosition),
//						Toast.LENGTH_SHORT).show();
//			}
//		});


        ImageView imgView = (ImageView)convertView.findViewById(R.id.imgChildItem);
        int id = 0;
        switch (childPosition)
        {
            case 0:
                id = context.getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + "additionsinglecol", null, null);
                break;
            case 1:
                id = context.getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + "additionvocal", null, null);
                break;
            case 2:
                id = context.getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + "subcompliment", null, null);
                break;
            case 3:
                id = context.getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + "submultiply", null, null);
                break;
            case 4:
                id = context.getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + "multibutterfly", null, null);
                break;
            case 5:
                id = context.getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + "multiline", null, null);
                break;
            case 6:
                id = context.getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + "divhillclimb", null, null);
                break;
            case 7:
                id = context.getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + "sqgeneral", null, null);
                break;
            case 8:
                id = context.getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + "sqendw5", null, null);
                break;
            case 9:
                id = context.getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + "cubegeneral", null, null);
                break;
            case 10:
                id = context.getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + "cubeequaldigit", null, null);
                break;
            case 11:
                id = context.getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + "percent", null, null);
                break;
            case 12:
                id = context.getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + "squareroot", null, null);
                break;

        }
        imgView.setImageResource(id);

        convertView.setTag(tempChild.get(childPosition));
        return convertView;
    }



    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) Childtem.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return groupItem.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_node,
                    null);
        }
        TextView text = (TextView) convertView.findViewById(R.id.group_textview);
        text.setTypeface(null, Typeface.BOLD);

        text.setText(groupItem.get(groupPosition));
        convertView.setTag(groupItem.get(groupPosition));

        ImageView imgView = (ImageView)convertView.findViewById(R.id.imgGroupItem);
        int id = 0;
        switch (groupPosition)
        {
            case 0:
                id = context.getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + "profile", null, null);
                break;
            case 1:
                id = context.getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + "learn", null, null);
                break;
            case 2:
                id = context.getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + "practice", null, null);
                break;
            case 3:
                id = context.getResources().getIdentifier("com.mycodehurts.rapidmath.app:drawable/" + "about", null, null);
                break;

        }
        imgView.setImageResource(id);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}