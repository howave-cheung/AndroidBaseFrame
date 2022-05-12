package com.bobo.baseframe.widget.component.recycler_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bobo.baseframe.R;
import com.bobo.baseframe.widget.listener.MyOnClickListener;
import com.bobo.baseframe.widget.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * @ClassName SuperRecyclerView
 * @Description 拥有刷新空布局的RecyclerView
 */
public class SuperRecyclerView extends FrameLayout implements ILayoutView, ISuperRecyclerView {

    int emptyResId = NO_ID, loadingResId = NO_ID, errorResId = NO_ID;

    public Context context;
    private int pageNum, pages;
    protected boolean enableRefresh, enableLoadMore;
    protected LinearLayoutManager layoutManager;
    protected BaseQuickAdapter adapter;
    protected OnRefreshListener refreshListener;

    protected View mRootView;
    protected SmartRefreshLayout refreshLayout;
    protected RecyclerView recyclerView;
    protected View loadingView, emptyView, errorView;
    protected View headView;

    private MyOnClickListener onClickListener = new MyOnClickListener() {
        @Override
        public void onClick(View v) {
            if (isDoubleClick()) {
                return;
            }
            if (refreshListener != null) {
                showLoading();
                loadingView.postDelayed(() -> refreshListener.onRefresh(), 500);
            }
        }
    };

    public SuperRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public SuperRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SuperRecyclerView);
        loadingResId = typedArray.getResourceId(R.styleable.SuperRecyclerView_srv_loading_res_Id, R.layout.layout_loading);
        emptyResId = typedArray.getResourceId(R.styleable.SuperRecyclerView_srv_empty_res_Id, R.layout.layout_empty);
        errorResId = typedArray.getResourceId(R.styleable.SuperRecyclerView_srv_error_res_Id, R.layout.layout_error);
        typedArray.recycle();

        initView(context);
        initData();
    }

    @Override
    public void initView(Context context) {
        mRootView = LayoutInflater.from(context).inflate(getLayoutId(), this);
        refreshLayout = findViewById(R.id.super_recycler_view_refresh);
        recyclerView = findViewById(R.id.super_recycler_view);

        loadingView = LayoutInflater.from(context).inflate(loadingResId, null);
        emptyView = LayoutInflater.from(context).inflate(emptyResId, null);
        errorView = LayoutInflater.from(context).inflate(errorResId, null);
    }

    @Override
    public void initData() {
        refreshLayout.setEnableNestedScroll(true);

        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        errorView.setOnClickListener(onClickListener);
        emptyView.setOnClickListener(onClickListener);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_recycler_super;
    }

    @Override
    public SuperRecyclerView setAdapter(BaseQuickAdapter adapter) {
        this.adapter = adapter;
        this.adapter.onDetachedFromRecyclerView(recyclerView);
        this.adapter.bindToRecyclerView(recyclerView);
        this.adapter.setEmptyView(loadingView);
        recyclerView.setAdapter(adapter);
        recyclerView.clearAnimation();
        return this;
    }

    @Override
    public SuperRecyclerView setOnRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
        if (refreshListener == null) {
            refreshLayout.setEnableRefresh(false);
            refreshLayout.setOnRefreshListener(null);
            enableRefresh = false;
        } else {
            refreshLayout.setEnableRefresh(true);
            refreshLayout.setOnRefreshListener(refreshLayout -> {
                refreshListener.onRefresh();
            });
            enableRefresh = true;
        }
        return this;
    }

    @Override
    public SuperRecyclerView setEnableRefresh(boolean enabled) {
        refreshLayout.setEnableRefresh(enabled);
        enableRefresh = enabled;
        return this;
    }

    @Override
    public SuperRecyclerView setOnLoadListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener == null) {
            refreshLayout.setEnableLoadMore(false);
            refreshLayout.setOnLoadMoreListener(null);
            enableLoadMore = false;
        } else {
            refreshLayout.setEnableLoadMore(true);
            refreshLayout.setOnLoadMoreListener(refreshLayout -> {
                if (pageNum > pages) {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                    ToastUtils.showNormalToast("无更多数据");
                } else {
                    loadMoreListener.onLoadMore(pageNum);
                }
            });
            enableLoadMore = true;
        }
        return this;
    }

    @Override
    public SuperRecyclerView setLayoutNestedScrollingEnabled(boolean enabled) {
        refreshLayout.setNestedScrollingEnabled(enabled);
        return this;
    }

    @Override
    public SuperRecyclerView setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            this.layoutManager = (LinearLayoutManager) layoutManager;
        }
        recyclerView.setLayoutManager(layoutManager);
        return this;
    }

    @Override
    public SuperRecyclerView showLoading() {
        adapter.getData().clear();
        adapter.notifyDataSetChanged();
        adapter.setEmptyView(loadingView);
        return this;
    }

    @Override
    public SuperRecyclerView showError() {
        adapter.setEmptyView(errorView);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
        return this;
    }

    @Override
    public SuperRecyclerView showEmpty() {
        adapter.setEmptyView(emptyView);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
        return this;
    }

    @Override
    public SuperRecyclerView refreshFailure() {
        refreshLayout.finishRefresh();
        if (adapter.getData().isEmpty()) {
            showError();
        }
        return this;
    }

    @Override
    public SuperRecyclerView refreshSuccess(int pages) {
        this.pageNum = 2;
//        pageNum++;
        this.pages = pages;
        refreshLoadingLayout();
        return this;
    }

    @Override
    public SuperRecyclerView refreshSuccess(List data) {
        return refreshSuccess(data, data.size());
    }

    @Override
    public SuperRecyclerView refreshSuccess(List data, int pages) {
        Log.e("SuperRecyclerView","自增操作"+(pageNum+1));
        return refreshSuccess(data, ++pageNum, pages);
    }

    @Override
    public SuperRecyclerView refreshSuccess(List data, int pageNum, int pages) {
        adapter.setNewData(data);
        this.pageNum = pageNum;
        this.pages = pages;
        refreshLoadingLayout();
        return this;
    }

    @Override
    public SuperRecyclerView loadMoreFailure() {
        refreshLayout.finishLoadMore();
        return this;
    }

    @Override
    public SuperRecyclerView loadMoreSuccess(int pageNum, int pages) {
        this.pageNum = pageNum;
        this.pages = pages;
        refreshLayout.finishLoadMore();
        return this;
    }

    @Override
    public SuperRecyclerView loadMoreSuccess(List data, int pageNum, int pages) {
        adapter.addData(data);
        this.pageNum = pageNum;
        this.pages = pages;
        refreshLayout.finishLoadMore();
        return this;
    }

    @Override
    public SuperRecyclerView autoRefresh() {
        refreshLayout.autoRefresh();
        return this;
    }

    @Override
    public BaseQuickAdapter getAdapter() {
        return adapter;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }

    @Override
    public int findFirstVisibleItemPosition() {
        return layoutManager.findFirstVisibleItemPosition();
    }

    @Override
    public int findLastVisibleItemPosition() {
        return layoutManager.findLastVisibleItemPosition();
    }

    @Override
    public int getRealPosition(int position) {
        BaseQuickAdapter adapter = getAdapter();
        if (adapter == null) {
            return position;
        }
        return adapter.getHeaderLayoutCount() + position;
    }

    @Override
    public SuperRecyclerView notifyRealItemChanged(int position) {
        BaseQuickAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.notifyItemChanged(getRealPosition(position));
        }
        return this;
    }

    @Override
    public boolean isSlideToTop() {
        return recyclerView.getChildCount() != 0 && recyclerView.getChildAt(0).getTop() == 0;
    }

    @Override
    public int getPages() {
        return pages;
    }

    protected void refreshLoadingLayout() {
        refreshLayout.finishRefresh();
        if (adapter.getData().isEmpty()) {
            showEmpty();
        } else {
            adapter.setEmptyView(emptyView);
            refreshLayout.setEnableRefresh(enableRefresh);
            refreshLayout.setEnableLoadMore(enableLoadMore);
            refreshLayout.setNoMoreData(false);
        }
        if (pageNum > pages) {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int pageNum);
    }


    public void setItemDecoration(int spacing) {
        recyclerView.addItemDecoration(new LinearLayoutDivider(context,spacing));
    }

    public void setItemDecoration(int color,int spacing) {
        recyclerView.addItemDecoration(new LinearLayoutDivider(context,color,spacing));
    }

    public void setHorizontalDecoration(int spacing) {
        recyclerView.addItemDecoration(new LinearLayoutDivider(context,"horizontal",spacing));
    }

    public void setHorizontalDecoration(int color,int spacing) {
        recyclerView.addItemDecoration(new LinearLayoutDivider(context,"horizontal",color,spacing));
    }

    public void setLineDecoration(int spacing) {
        recyclerView.addItemDecoration(new LinearLayoutDivider(context,"line",spacing));
    }
}
