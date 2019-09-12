package com.cheekibreeki.whatsfridge;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomLayoutManager extends RecyclerView.LayoutManager {

    private int screenWidth;
    private final int MAX_ITEMS = 25;

    public CustomLayoutManager(int screenWidth){
        this.screenWidth = screenWidth;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);

        int x=0;
        for(int i=0; i<getItemCount(); i++){
            if(i%2==0 && i!=0)  x=0;

            View view = recycler.getViewForPosition(i);

            measureChild(view, 500, 150);

            int viewWidth = view.getMeasuredWidth();
            int viewHeight = view.getMeasuredHeight();

            int left = x;
            int right = x+viewWidth;

            int top = i/2 * viewHeight;
            int bottom = top+viewHeight;
            x+=viewWidth;

            addView(view);

            layoutDecorated(view, left, top, right, bottom);
        }

        List<RecyclerView.ViewHolder> scrapList = recycler.getScrapList();
        for(RecyclerView.ViewHolder viewHolder: scrapList)
            recycler.recycleView(viewHolder.itemView);

    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
