package com.bobo.baseframe.widget.component.recycler_view;

import android.content.Context;

import androidx.annotation.Nullable;

import com.bobo.baseframe.R;
import com.bobo.baseframe.widget.utils.ResUtils;

import ysn.com.recyclerviewdivider.ConvertUtils;
import ysn.com.recyclerviewdivider.Divider;
import ysn.com.recyclerviewdivider.DividerBuilder;
import ysn.com.recyclerviewdivider.RecyclerViewDivider;

public class GridDivider extends RecyclerViewDivider {
    private int color;
    private int spacing;
    private int column;
    private boolean bottom;

    public GridDivider(Context context, int spacing, int column) {
        super(context);
        this.color = ResUtils.getColor(R.color.bg);
        this.spacing = ConvertUtils.px2dp(context,ResUtils.getDimension(spacing));
        this.column = column;
    }

    public GridDivider(Context context, int spacing, int column, boolean bottom) {
        super(context);
        this.color = ResUtils.getColor(R.color.white);
        this.spacing = ConvertUtils.px2dp(context,ResUtils.getDimension(spacing));
        this.column = column;
        this.bottom = bottom;
    }

    @Nullable
    @Override
    public Divider getDivider(int itemPosition) {
        DividerBuilder dividerBuilder = new DividerBuilder();
        int tag = itemPosition % column;

        if (tag == 0) {
            dividerBuilder.setLeftLine(true, color, spacing, 0, 0)
                    .setRightLine(true, color, spacing, 0, 0);
        } else {
            dividerBuilder.setRightLine(true, color, spacing, 0, 0);
        }

        if (!bottom){
            if (itemPosition < column) {
                dividerBuilder.setTopLine(true, color, spacing, 0, 0);
            }
        }

        return dividerBuilder
                .setBottomLine(true, color, spacing , 0, 0)
                .create();


    }


}
