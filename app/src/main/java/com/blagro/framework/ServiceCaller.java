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


    public void callCityListService(final String area, final IAsyncWorkCompletedCallback workCompletedCallback) {
        final String url = "http://blapi2.veteransoftwares.com/api/city?area=" + area;
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


    public void callAreaListService(final IAsyncWorkCompletedCallback workCompletedCallback) {
        final String url = "http://blapi2.veteransoftwares.com/api/area";
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
        final String url = "http://blapi2.veteransoftwares.com/api/product?sub_cat=" + category;
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

    public void callSubCategoryListService(final String category, final IAsyncWorkCompletedCallback workCompletedCallback) {
        final String url = "http://blapi2.veteransoftwares.com/api/sub_cat?category=" + category;
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


    public void callAllLocationData(final String username, final String lat, final String lon, final IAsyncWorkCompletedCallback workCompletedCallback) {
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

    public void callViewRetailerOrderData(final int id, final String status, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url = "http://blapi2.veteransoftwares.com/api/retailer1?retailer_id=" + id + "&status=" + status;
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


    public void callViewDistributorOrderData(final int id, final String status, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url = "http://blapi2.veteransoftwares.com/api/distributer1?distributor_id=" + id + "&status=" + status;
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

    public void callViewMyOrderData(final String id, final String status, final IAsyncWorkCompletedCallback workCompletedCallback) {
        String url = "http://blapi2.veteransoftwares.com/api/employe?emp_id=" + id + "&status=" + status;
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

    public void callCheckoutData(final String Emp_id, final int distributor_id, final int retailer_id, final IAsyncWorkCompletedCallback workCompletedCallback) {
        final String url = "http://blapi2.veteransoftwares.com/api/order?emp_id=" + Emp_id + "&distributor_id=" + distributor_id + "&retailer_id=" + retailer_id;
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

    public void callCheckoutItemsData(final String orerno, final String id, final int qty, final IAsyncWorkCompletedCallback workCompletedCallback) {
        final String url = "http://blapi2.veteransoftwares.com/api/order_item?order_no=" + orerno + "&pro_id=" + id + "&qrt=" + qty;
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
}