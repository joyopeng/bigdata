package com.bric.kagdatabkt.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bric.kagdatabkt.R;

public class TabLayout extends LinearLayout {

    private ImageView icon;
    private TextView text;

    private LayoutInflater mInflater;

    public TabLayout(Context c) {
        super(c);
    }

    public TabLayout(Context c, AttributeSet attrs) {
        super(c, attrs);
        mInflater = LayoutInflater.from(c);
        setOrientation(VERTICAL);
        View view = mInflater.inflate(R.layout.tab_layout, this, true);
        icon = view.findViewById(R.id.tab_icon);
        text = view.findViewById(R.id.tab_text);
    }

    public void setIcon(int resid) {
        icon.setBackgroundResource(resid);
    }

    public void setText(int value) {
        text.setText(value);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked() == MotionEvent.ACTION_UP)
            performClick();
        return true;
    }

    public void setTextColor(int colorres) {
        text.setTextColor(colorres);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

}
