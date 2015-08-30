package com.veaer.gank.widget;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.veaer.gank.App;

/**
 * Created by Veaer on 15/8/16.
 */
public class BaseFragment extends Fragment {
    protected LayoutInflater inflater;
    protected Activity activity;
    protected View fragmentView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public < T extends View> T $(int id) {
        return (T)fragmentView.findViewById(id);
    }

    public void hideView(int id) {
        fragmentView.findViewById(id).setVisibility(View.GONE);
    }

    public void showView(int id) {
        fragmentView.findViewById(id).setVisibility(View.VISIBLE);
    }

    protected Context getContext() {
        return App.sContext;
    }

}
