package com.blagro.framework;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.blagro.utilities.Contants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by lalit on 7/25/2017.
 */
public class ServiceCaller {
    Context context;

    public ServiceCaller(Context context) {
        this.context = context;
    }

    //    //call login data
    public void callLoginService(final String username, final String password, final IAsyncWorkCompletedCallback workCompletedCallback) {
        final String url = "http://blapi2.veteransoftwares.com/api/login?emp_code=" + username + "&password=" + password;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                workCompletedCallback.onDone(response, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                workCompletedCallback.onDone(error.getMessage(), false);
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
    }


    public void callCityListService(final IAsyncWorkCompletedCallback workCompletedCallback) {
        final String url = "http://blapi2.veteransoftwares.com/api/city";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                workCompletedCallback.onDone(response, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                workCompletedCallback.onDone(error.getMessage(), false);
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
    }


    public void callCateorygListService(final IAsyncWorkCompletedCallback workCompletedCallback) {
        final String url = "http://blapi2.veteransoftwares.com/api/category";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                workCompletedCallback.onDone(response, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                workCompletedCallback.onDone(error.getMessage(), false);
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
    }


    public void callProductListService(final String category, final IAsyncWorkCompletedCallback workCompletedCallback) {
        final String url = "http://blapi2.veteransoftwares.com/api/product?category=" + category;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                workCompletedCallback.onDone(response, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                workCompletedCallback.onDone(error.getMessage(), false);
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
    }

    public void callDistributerListService(final String city, final IAsyncWorkCompletedCallback workCompletedCallback) {
        final String url = "http://blapi2.veteransoftwares.com/api/distributor?city=" + city;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                workCompletedCallback.onDone(response, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                workCompletedCallback.onDone(error.getMessage(), false);
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
    }

    public void callRetailerListService(final String city, final IAsyncWorkCompletedCallback workCompletedCallback) {
        final String url = "http://blapi2.veteransoftwares.com/api/retailer?city=" + city;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                workCompletedCallback.onDone(response, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                workCompletedCallback.onDone(error.getMessage(), false);
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
    }

    public void callGetProfileData(final String username, final IAsyncWorkCompletedCallback workCompletedCallback) {
        final String url = "http://blapi2.veteransoftwares.com/api/emp?emp_code=" + username;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                workCompletedCallback.onDone(response, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                workCompletedCallback.onDone(error.getMessage(), false);
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
    }


    public void callAllLocationData(final String username, final double lat, final double lon, final IAsyncWorkCompletedCallback workCompletedCallback) {
        final String url = "http://blapi2.veteransoftwares.com/api/location?username=" + username + "&lang=" + lon + "&lat=" + lat;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                workCompletedCallback.onDone(response, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                workCompletedCallback.onDone(error.getMessage(), false);
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
    }

    public void callViewRetailerOrderData(final int id, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url = "http://blapi2.veteransoftwares.com/api/retailer1?retailer_id=" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                workCompletedCallback.onDone(response, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                workCompletedCallback.onDone(error.getMessage(), false);
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
    }


    public void callViewDistributorOrderData(final int id, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url = "http://blapi2.veteransoftwares.com/api/distributer1?distributor_id=" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                workCompletedCallback.onDone(response, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                workCompletedCallback.onDone(error.getMessage(), false);
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
    }

    public void callAllOrderData(final int orderno, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url = "http://blapi2.veteransoftwares.com/api/aorder?order_no=" + orderno;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                workCompletedCallback.onDone(response, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                workCompletedCallback.onDone(error.getMessage(), false);
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
    }

    public void callCheckoutData(final String city, final String category, final int distributor, final int retailer, final String list, final IAsyncWorkCompletedCallback workCompletedCallback) {
        final String url = "http://blapi2.veteransoftwares.com/api/order?distributor=" + distributor + "&retailer=" + retailer + "&list=" + list;
        JSONObject obj = new JSONObject();
        try {
            obj.put("city", city);
            obj.put("category", category);
            obj.put("distributor", distributor);
            obj.put("retailer", retailer);
            obj.put("list", list);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
            @Override
            public void onDone(String doneWhatCode, String result, String error) {
                if (result != null) {
                    workCompletedCallback.onDone(result, true);
                } else {
                    workCompletedCallback.onDone("callNewsUrlService done", false);
                }
            }
        });
    }

}

