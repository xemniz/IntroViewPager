package com.flowexample.introsample.intro;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.view.View;

public final class FragmentItem implements Parcelable {

    @IdRes
    private int mViewResId;
    private float mShiftCoefficient;
    private View mView;

    public static FragmentItem create(@IdRes int viewResId, float shiftCoefficient) {
        return new FragmentItem(viewResId, shiftCoefficient);
    }

    private FragmentItem(@IdRes int viewResId, float shiftCoefficient) {
        this.mViewResId = viewResId;
        this.mShiftCoefficient = shiftCoefficient;
    }

    int getViewResId() {
        return mViewResId;
    }

    float getShiftCoefficient() {
        return mShiftCoefficient;
    }

    View getView() {
        return mView;
    }

    void setView(View view) {
        mView = view;
    }

    protected FragmentItem(Parcel in) {
        mViewResId = in.readInt();
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
        dest.writeFloat(mShiftCoefficient);
    }

}
