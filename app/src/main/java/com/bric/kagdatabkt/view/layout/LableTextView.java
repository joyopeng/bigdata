package com.bric.kagdatabkt.view.layout;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import com.bric.kagdatabkt.R;


/**
 * Created by joyopeng on 17-11-22.
 */

public class LableTextView extends AppCompatTextView {

    private SpannableStringBuilder builder;
    private ForegroundColorSpan redSpan;
    private Context context;

    public LableTextView(Context c) {
        this(c, null);
    }

    public LableTextView(Context c, AttributeSet attrs) {
        this(c, attrs, android.R.attr.textViewStyle);
    }

    public LableTextView(Context c, AttributeSet attrs, int defStyle) {
        super(c, attrs, defStyle);
        redSpan = new ForegroundColorSpan(Color.RED);
        context = c;
    }

    public void setText(String text) {
        String content = text + "*";
        builder = new SpannableStringBuilder(content);
        builder.setSpan(redSpan, content.length() - 1, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(builder);
    }

    public void setCusText(int res) {
        String content = context.getResources().getString(res);
        this.setText(content);
    }

}
