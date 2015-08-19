package com.veaer.gank.request;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.veaer.gank.widget.LoadingErrorFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

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
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    Cache.Entry requestEntry = HttpHeaderParser.parseCacheHeaders(response);
                    requestEntry.ttl = System.currentTimeMillis() + 86400 * 60 * 60 * 12;
                    requestEntry.softTtl = System.currentTimeMillis() + 86400 * 60 * 60 * 12;
                    return Response.success(new JSONObject(jsonString), requestEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };
        request.setShouldCache(true);
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, 1, 1.0f));
        VolleyUtil.getRequestQueue().add(request);
    }

    public void getNoJson(final String url, Response.Listener<JSONObject> listener) {
        JsonObjectRequest request = new JsonObjectRequest(url,listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("requestError", error.toString());
            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                    JSONObject result = new JSONObject();
                    result.put("code", 200);
                    result.put("data", jsonString);
                    return Response.success(result,
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };

        request.setShouldCache(true);
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, 1, 1.0f));
        VolleyUtil.getRequestQueue().add(request);
    }

}


