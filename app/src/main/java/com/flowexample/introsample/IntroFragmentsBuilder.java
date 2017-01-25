package com.flowexample.introsample;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.TypedValue;

import com.flowexample.introsample.intro.FragmentItem;
import com.flowexample.introsample.intro.IntroFragment;

import java.util.ArrayList;
import java.util.List;


public class IntroFragmentsBuilder {
    private Context context;
    private int mDefaultFragmentLayout;
    private List<Fragment> fragments;
    private List<Integer> colors;
    private int defaultColor;

    public IntroFragmentsBuilder(Context context) {
        this.context = context;
        fragments = new ArrayList<>();
        colors = new ArrayList<>();
        defaultColor = getThemePrimaryColor(context);
    }

    public static IntroFragmentsBuilder create(Context context) {
        return new IntroFragmentsBuilder(context);
    }

    public IntroFragmentsBuilder setDefaultFragmentLayout(int defaultFragmentLayout) {
        mDefaultFragmentLayout = defaultFragmentLayout;
        return this;
    }

    public IntroFragmentsBuilder addFragment(FragmentItem... args) {
        fragments.add(IntroFragment.create(mDefaultFragmentLayout, args));
        colors.add(defaultColor);
        return this;
    }

    private static int getThemePrimaryColor(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

    public List[] build() {
        return new List[]{fragments, colors};
    }
}
