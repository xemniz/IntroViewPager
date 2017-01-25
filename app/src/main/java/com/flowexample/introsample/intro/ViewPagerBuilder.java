package com.flowexample.introsample.intro;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import com.flowexample.introsample.R;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerBuilder {
    private List<Fragment> fragments;
    private List<Integer> colors;
    private int defaultColor;
    private final FragmentActivity activity;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private int resId;
    private int pageIndicatorResId;
    private int nextButtonResId;
    private int skipButtonResId;
    private int DURATION = 150;
    private View.OnClickListener skipButtonClick;

    private ViewPagerBuilder(FragmentActivity activity) {
        this.activity = activity;
        fragments = new ArrayList<>();
        colors = new ArrayList<>();
        defaultColor = ViewPagerBuilder.getThemePrimaryColor(activity);
    }

    public static ViewPagerBuilder create(FragmentActivity activity) {
        return new ViewPagerBuilder(activity);
    }

    //region setting attributes
    public ViewPagerBuilder setDefaultColor(int color) {
        defaultColor = color;
        return this;
    }

    public ViewPagerBuilder setLayoutResId(int resId) {
        this.resId = resId;
        return this;
    }

    public ViewPagerBuilder addPage(Fragment fragment, int bgColor) {
        fragments.add(fragment);
        colors.add(bgColor);
        return this;
    }

    public ViewPagerBuilder addPage(Fragment fragment) {
        fragments.add(fragment);
        colors.add(defaultColor);
        return this;
    }

    public ViewPagerBuilder addPages(List... fragmentsColors) {
        for (int i = 0; i < fragmentsColors[0].size(); i++) {
            fragments.add((Fragment) fragmentsColors[0].get(i));
            colors.add((Integer) fragmentsColors[1].get(i));
        }
        return this;
    }

    public ViewPagerBuilder setIndicatorResId(int pageIndicatorResId) {
        this.pageIndicatorResId = pageIndicatorResId;
        return this;
    }

    public ViewPagerBuilder setNextButtonResId(int nextButtonResId) {
        this.nextButtonResId = nextButtonResId;
        return this;
    }

    public ViewPagerBuilder setSkipButtonResId(int skipButtonResId) {
        this.skipButtonResId = skipButtonResId;
        return this;
    }

    public ViewPagerBuilder setOnSkipListener(View.OnClickListener skipButtonClick) {
        this.skipButtonClick = skipButtonClick;
        return this;
    }
    //endregion

    public ViewPager build() {
        final ViewPager viewPager = (ViewPager) activity.findViewById(resId);
        final Button nextButton = (Button) activity.findViewById(nextButtonResId);
        final Button skipButton = (Button) activity.findViewById(skipButtonResId);
        final int[] colors = ViewPagerBuilder.toIntArray(ViewPagerBuilder.this.colors);
        final SectionsPagerAdapter adapter = new SectionsPagerAdapter(activity.getSupportFragmentManager(), fragments);

        ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]));
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
                    skipButton.setVisibility(View.VISIBLE);
                    if (skipButton.getAlpha() == 0) {
                        skipButton.setVisibility(View.VISIBLE);
                        skipButton.animate().alpha(1f).setDuration(DURATION).setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).start();
                        updateNextButton(viewPager, nextButton);
                    }

                } else {
                    skipButton.animate().alpha(0f).setDuration(DURATION).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (skipButton.getVisibility() == View.VISIBLE) {
                                skipButton.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).start();
                    updateNextButton(viewPager, nextButton);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };

        ViewPager.PageTransformer pageTransformer = new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                Object obj = page.getTag(R.id.st_page_fragment);
                if (obj instanceof FragmentItemsTransformer) {
                    ((FragmentItemsTransformer) obj).transformPage(page.getWidth(), position);
                }
            }
        };

        PageIndicatorView pageIndicatorView = (PageIndicatorView) activity.findViewById(pageIndicatorResId);

        viewPager.addOnPageChangeListener(changeListener);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(false, pageTransformer);
        updateNextButton(viewPager, nextButton);
        skipButton.setOnClickListener(skipButtonClick);
        pageIndicatorView.setViewPager(viewPager);
        return viewPager;
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragments;

        SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ("page " + position).toUpperCase();
        }
    }

    //region util methods
    private static int getThemePrimaryColor(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

    private void updateNextButton(final ViewPager viewPager, Button nextButton) {
        if (viewPager.getAdapter().getCount() - 1 > viewPager.getCurrentItem()) {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                }
            });
            nextButton.setText("next");
        }
        else {
            nextButton.setOnClickListener(skipButtonClick);
            nextButton.setText("got it");
        }
    }

    private static int[] toIntArray(List<Integer> list) {
        int[] ret = new int[list.size()];
        int i = 0;
        for (Integer e : list)
            ret[i++] = e;
        return ret;
    }
    //endregion
}
