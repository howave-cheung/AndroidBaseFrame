package com.bobo.baseframe.widget.component.recycler_view;

import android.content.Context;
import androidx.annotation.Nullable;

import com.bobo.baseframe.R;
import com.bobo.baseframe.widget.utils.ResUtils;

import ysn.com.recyclerviewdivider.ConvertUtils;
import ysn.com.recyclerviewdivider.Divider;
import ysn.com.recyclerviewdivider.DividerBuilder;
import ysn.com.recyclerviewdivider.RecyclerViewDivider;

public class LinearLayoutDivider extends RecyclerViewDivider {
    private int color;
    private int spacing;
    private String direction;

    public LinearLayoutDivider(Context context, int spacing) {
        super(context);
        this.color = ResUtils.getColor(R.color.bg);
        this.spacing = ConvertUtils.px2dp(context,ResUtils.getDimension(spacing));

    }

    public LinearLayoutDivider(Context context, int color, int spacing) {
        super(context);
        this.color = ResUtils.getColor(color);
        this.spacing = ConvertUtils.px2dp(context,ResUtils.getDimension(spacing));

    }

    public LinearLayoutDivider(Context context, String direction, int spacing) {
        super(context);
        this.color = ResUtils.getColor(R.color.bg);
        this.spacing = ConvertUtils.px2dp(context,ResUtils.getDimension(spacing));
        this.direction = direction;
    }

    public LinearLayoutDivider(Context context, String direction, int color, int spacing) {
        super(context);
        this.color = ResUtils.getColor(color);
        this.spacing = ConvertUtils.px2dp(context,ResUtils.getDimension(spacing));
        this.direction = direction;
    }

    @Nullable
    @Override
    public Divider getDivider(int itemPosition) {
        DividerBuilder dividerBuilder = new DividerBuilder();
        if ("line".equals(direction)){
            if (itemPosition == 0){

            }else {
                dividerBuilder.setTopLine(true, color, spacing, 0, 0);
            }

            return dividerBuilder.create();
        }else if ("horizontal".equals(direction)){
            if (itemPosition == 0){
                dividerBuilder.setLeftLine(true, color, spacing, 0, 0);
            }
            return dividerBuilder
                    .setRightLine(true, color, spacing, 0, 0)
                    .create();
        } else if ("bottom".equals(direction)){
            return dividerBuilder
                    .setBottomLine(true, color, spacing, 0, 0)
                    .create();
        }else {
            if (itemPosition == 0){
                dividerBuilder.setTopLine(true, color, spacing, 0, 0);
            }
            return dividerBuilder
                    .setBottomLine(true, color, spacing, 0, 0)
                    .create();
        }

    }
}
