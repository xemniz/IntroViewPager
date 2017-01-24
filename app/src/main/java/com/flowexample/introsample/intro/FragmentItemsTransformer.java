package com.flowexample.introsample.intro;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flowexample.introsample.R;

public class FragmentItemsTransformer {
    @LayoutRes
    private int mLayoutResId;
    private FragmentItem[] fragmentItems;
    private final InternalFragment mInternalFragment;
    final public static int TAG = 1;

    FragmentItemsTransformer(InternalFragment internalFragment) {
        mInternalFragment = internalFragment;
    }

    View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayoutResId = mInternalFragment.getLayoutResId();
        fragmentItems = mInternalFragment.getFragmentItems();

        if (mLayoutResId == 0 || fragmentItems == null || fragmentItems.length == 0) {
            throw new IllegalArgumentException("Page layout id or transform items not specified");
        }

        View view = inflater.inflate(mLayoutResId, container, false);
        view.setTag(R.id.st_page_fragment, this);

        for (FragmentItem item : fragmentItems) {
            View transformView = view.findViewById(item.getViewResId());
            if (transformView == null) {
                throw new IllegalArgumentException("View by FragmentItem#getViewResId() not found.");
            }
            item.setView(transformView);
        }

        return view;
    }

    final public void transformPage(int pageWidth, float position) {
        for (int i = 0; i < fragmentItems.length; i++) {
            float translationX = position * pageWidth * fragmentItems[i].getShiftCoefficient();
            fragmentItems[i].getView().setTranslationX(translationX);
        }
    }

    void onDestroyView() {
        for (FragmentItem item : fragmentItems) {
            item.setView(null);
        }
    }

    interface InternalFragment {
        @LayoutRes
        int getLayoutResId();

        FragmentItem[] getFragmentItems();

        Bundle getArguments();
    }
}
