package com.flowexample.introsample.intro;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IntroFragment extends Fragment {

    private static final String EXTRA_PAGE_LAYOUT_RES = "PAGE_LAYOUT_RES";
    private static final String EXTRA_TRANSFORM_ITEMS = "TRANSFORM_ITEMS";

    public static IntroFragment create(int layoutId, FragmentItem... items) {
        IntroFragment f = new IntroFragment();
        f.setArguments(getArguments(layoutId, items));
        return f;
    }

    static Bundle getArguments(@LayoutRes int pageLayoutRes, @NonNull FragmentItem... transformItems) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_PAGE_LAYOUT_RES, pageLayoutRes);
        args.putParcelableArray(EXTRA_TRANSFORM_ITEMS, transformItems);
        return args;
    }

    private FragmentItemsTransformer transformer;
    private FragmentItemsTransformer.InternalFragment mInternalFragment = new FragmentItemsTransformer.InternalFragment() {

        @Override
        public int getLayoutResId() {
            Bundle args = getArguments();
            if (args != null) {
                if (args.containsKey(EXTRA_PAGE_LAYOUT_RES)) {
                    return args.getInt(EXTRA_PAGE_LAYOUT_RES);
                }
            }
            throw new IllegalArgumentException("Page layout resource id is not specified.");
        }

        @Override
        public FragmentItem[] getFragmentItems() {
            FragmentItem transformItems[] = null;
            Bundle args = mInternalFragment.getArguments();
            if (args != null) {
                if (args.containsKey(EXTRA_TRANSFORM_ITEMS)) {
                    transformItems = ParcelableUtils.getParcelableArray(args, EXTRA_TRANSFORM_ITEMS,
                            FragmentItem.class, FragmentItem[].class);
                }
            }

            if (transformItems == null) {
                throw new IllegalArgumentException("Transform items array is not specified.");
            }

            return transformItems;
        }


        @Override
        public Bundle getArguments() {
            return IntroFragment.this.getArguments();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transformer = new FragmentItemsTransformer(mInternalFragment);
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return transformer.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        transformer.onDestroyView();
        super.onDestroyView();
    }
}
