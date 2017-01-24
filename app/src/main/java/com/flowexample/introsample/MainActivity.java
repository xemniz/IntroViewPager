package com.flowexample.introsample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.flowexample.introsample.intro.FragmentItem;
import com.flowexample.introsample.intro.IntroFragment;
import com.flowexample.introsample.intro.ViewPagerBuilder;

import static java.lang.String.format;

/**
 * Created by USER on 20.01.2017.
 */
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                .addPage(getIntroFragment(), getResources().getColor(R.color.color1))
                .addPage(getIntroFragment(), getResources().getColor(R.color.color2))
                .addPage(getIntroFragment(), getResources().getColor(R.color.color3))
                .build();

        }

    private IntroFragment getIntroFragment() {
        return IntroFragment.create(R.layout.fragment_main, new FragmentItem[]
                {FragmentItem.create(R.id.sl, -0.1f), FragmentItem.create(R.id.Img, 0.1f)});
    }


}

