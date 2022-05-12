package com.bobo.baseframe.widget.component;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.bobo.baseframe.R;
import com.bobo.baseframe.databinding.FragmentImageBinding;
import com.bobo.baseframe.widget.mvp.BaseFragment;
import com.bobo.baseframe.widget.utils.ImageUtil;

public class ImageFragment extends BaseFragment<FragmentImageBinding> {

    private String photoUrl;

    @Override
    protected FragmentImageBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentImageBinding.inflate(inflater,container,false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image;
    }

    @Override
    protected void initView() {
        if (getArguments() == null) {
            return;
        }
        photoUrl = getArguments().getString("url");

        ImageUtil.loadImageFitCenter(getActivity(), photoUrl,mViewBinding.imageShow);

    }

}
