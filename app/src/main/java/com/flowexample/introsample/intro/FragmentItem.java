package com.flowexample.introsample.intro;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public final class FragmentItem implements Parcelable {

    @IdRes
    private int mViewResId;
    private int valueResId;
    private float mShiftCoefficient;
    private View mView;

    public static FragmentItem create(@IdRes int viewResId, float shiftCoefficient) {
        return new FragmentItem(viewResId, shiftCoefficient);
    }

    public static FragmentItem create(@IdRes int viewResId, int valueResId, float shiftCoefficient) {
        return new FragmentItem(viewResId, valueResId, shiftCoefficient);
    }

    public FragmentItem(int viewResId, int valueResId, float shiftCoefficient) {
        this.mViewResId = viewResId;
        this.mShiftCoefficient = shiftCoefficient;
        this.valueResId = valueResId;
    }

    private FragmentItem(@IdRes int viewResId, float shiftCoefficient) {
        this.mViewResId = viewResId;
        this.mShiftCoefficient = shiftCoefficient;
        this.valueResId = 0;
    }

    int getViewResId() {
        return mViewResId;
    }

    float getShiftCoefficient() {
        return mShiftCoefficient;
    }

    View getView() {
        if (valueResId!=0){
            if (mView instanceof ImageView)
                ((ImageView) mView).setImageResource(valueResId);
            if (mView instanceof TextView)
                ((TextView) mView).setText(valueResId);
        }
        return mView;
    }

    void setView(View view) {
        mView = view;
    }

    protected FragmentItem(Parcel in) {
        mViewResId = in.readInt();
        valueResId = in.readInt();
        mShiftCoefficient = in.readFloat();
    }

    public static final Creator<FragmentItem> CREATOR = new Creator<FragmentItem>() {
        @Override
        public FragmentItem createFromParcel(Parcel in) {
            return new FragmentItem(in);
        }

        @Override
        public FragmentItem[] newArray(int size) {
            return new FragmentItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mViewResId);
        dest.writeInt(valueResId);
        dest.writeFloat(mShiftCoefficient);
    }

}
