package com.veaer.gank.request;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.veaer.gank.widget.LoadingErrorFragment;

import org.json.JSONObject;

/**
 * Created by Veaer on 15/8/15.
 */
public class VolleyRequestManager {
    private static int TIME_OUT = 5 * 1000;
    private static long CACHE_OUT = + 86400 * 60 * 60;

    private static VolleyRequestManager volley;
    public static VolleyRequestManager getInstance(){
        if(volley == null)
            volley = new VolleyRequestManager();
        return volley;
    }

    public void get(final String url, final LoadingErrorFragment loadingErrorFragment, Response.Listener<JSONObject> listener) {
        JsonObjectRequest request = new JsonObjectRequest(url,
                listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingErrorFragment.showMe();
            }
        });
        request.setShouldCache(true);
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, 1, 1.0f));
        VolleyUtil.getRequestQueue().add(request);
    }

}


