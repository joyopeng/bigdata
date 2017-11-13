package com.bric.kagdatabkt.view.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class FlipOverListView extends ListView {

    private FilpOverListener listener;
    private boolean isLocked;
    private View footerView;

    public FlipOverListView(Context context) {
        super(context);

    }

    public FlipOverListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(scrollListener);


    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        super.setOnScrollListener(l);
    }

    private OnScrollListener scrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            StringBuffer sb = new StringBuffer();
            sb.append(firstVisibleItem).append(":").append(visibleItemCount).append(":").append(totalItemCount);
            if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                if (null != listener) {
                    if (isLocked == false)
                        isLocked = listener.filpOverEvent();
                }
            }
        }
    };


    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public void setOnFilpOverListener(FilpOverListener listener) {
        this.listener = listener;
    }

    public interface FilpOverListener {
        public boolean filpOverEvent();
    }

    public void setLoadingView(View footerView) {
        if (null != this.footerView && null != footerView) {
            this.removeFooterView(this.footerView);
            this.addFooterView(footerView);
        } else if (null == this.footerView && null != footerView) {
            this.footerView = footerView;
            this.addFooterView(this.footerView);
        }

    }

    public void showEndPageView(View endPageView) {
        if (null != footerView) {
            this.removeFooterView(footerView);
            this.addFooterView(endPageView);
        } else {
            this.addFooterView(endPageView);
        }
    }

    @Override
    public ListAdapter getAdapter() {
        ListAdapter mListAdapter = (ListAdapter) super.getAdapter();
        if (mListAdapter instanceof BaseAdapter) {
            return super.getAdapter();

        }

        if (mListAdapter instanceof HeaderViewListAdapter) {
            return ((HeaderViewListAdapter) mListAdapter).getWrappedAdapter();
        }
        return super.getAdapter();
    }
}
