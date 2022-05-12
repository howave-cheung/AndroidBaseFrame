package com.bobo.baseframe.widget.component.recycler_view;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * @ClassName ISuperRecyclerView
 * @Description 一句话概括作用
 */
public interface ISuperRecyclerView {

    int getLayoutId();

    SuperRecyclerView setAdapter(BaseQuickAdapter adapter);

    SuperRecyclerView setOnRefreshListener(SuperRecyclerView.OnRefreshListener refreshListener);

    SuperRecyclerView setEnableRefresh(boolean enabled);

    SuperRecyclerView setOnLoadListener(SuperRecyclerView.OnLoadMoreListener loadMoreListener);

    SuperRecyclerView setLayoutNestedScrollingEnabled(boolean enabled);

    SuperRecyclerView setLayoutManager(RecyclerView.LayoutManager layoutManager);

    SuperRecyclerView showLoading();

    SuperRecyclerView showError();

    SuperRecyclerView showEmpty();

    SuperRecyclerView refreshFailure();

    SuperRecyclerView refreshSuccess(int pages);

    SuperRecyclerView refreshSuccess(@NonNull List data);

    SuperRecyclerView refreshSuccess(@NonNull List data, int pages);

    SuperRecyclerView refreshSuccess(@NonNull List data, int pageNum, int pages);

    SuperRecyclerView loadMoreFailure();

    SuperRecyclerView loadMoreSuccess(int pageNum, int pages);

    SuperRecyclerView loadMoreSuccess(List data, int pageNum, int pages);

    SuperRecyclerView autoRefresh();

    BaseQuickAdapter getAdapter();

    RecyclerView getRecyclerView();

    LinearLayoutManager getLayoutManager();

    /**
     * 获取第一个显示的position
     */
    int findFirstVisibleItemPosition();

    /**
     * 获取最后一个显示的position
     */
    int findLastVisibleItemPosition();

    /**
     * 获取真实的position，加上头部
     */
    int getRealPosition(int position);

    /**
     * 刷新item
     */
    SuperRecyclerView notifyRealItemChanged(int position);

    /**
     * 判断是否滚动到顶部
     */
    boolean isSlideToTop();

    int getPages();
}
