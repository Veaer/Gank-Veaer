package com.veaer.gank.widget;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;

import com.veaer.gank.App;

/**
 * Created by Veaer on 15/8/16.
 */
public class BaseFragment extends Fragment {
    protected LayoutInflater inflater;
    protected Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    protected Context getContext() {
        return App.sContext;
    }

}
