package com.flowexample.introsample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.flowexample.introsample.intro.FragmentItem;
import com.flowexample.introsample.intro.IntroFragment;
import com.flowexample.introsample.intro.ViewPagerBuilder;

import java.util.List;

import static java.lang.String.format;

/**
 * Created by USER on 20.01.2017.
 */
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List[] pages = IntroFragmentsBuilder.create(this)
                .setDefaultFragmentLayout(R.layout.fragment_main)
                .addFragment(FragmentItem.create(R.id.sl, R.string.app_name, -0.1f),
                        FragmentItem.create(R.id.Img, R.drawable.sample, 0.1f))
                .addFragment(FragmentItem.create(R.id.sl, R.string.app_name, -0.1f),
                        FragmentItem.create(R.id.Img, R.drawable.sample, 0.1f))
                .addFragment(FragmentItem.create(R.id.sl, R.string.app_name, -0.1f),
                        FragmentItem.create(R.id.Img, R.drawable.sample, 0.1f))
                .addFragment(FragmentItem.create(R.id.sl, R.string.app_name, -0.1f),
                        FragmentItem.create(R.id.Img, R.drawable.sample, 0.1f))
                .addFragment(FragmentItem.create(R.id.sl, R.string.app_name, -0.1f),
                        FragmentItem.create(R.id.Img, R.drawable.sample, 0.1f))
                .build();


        ViewPagerBuilder.create(this)
                .setNextButtonResId(R.id.next_button)
                .setSkipButtonResId(R.id.skip_button)
                .setOnSkipListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "SKIP TUTORIAL", Toast.LENGTH_SHORT).show();
                    }
                })
                .setLayoutResId(R.id.pager)
                .setIndicatorResId(R.id.pageIndicatorView)
                .addPages(pages)
                .build();

        }

    private IntroFragment getIntroFragment() {
        return IntroFragment.create(R.layout.fragment_main,
                FragmentItem.create(R.id.sl, -0.1f),
                FragmentItem.create(R.id.Img, 0.1f));
    }


}

