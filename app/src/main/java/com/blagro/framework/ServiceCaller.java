package com.blagro.framework;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blagro.utilities.Contants;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by lalit on 7/25/2017.
 */
public class ServiceCaller {
    Context context;

    public ServiceCaller(Context context) {
        this.context = context;
    }

//    //call login data
    public void callLoginService(final String phone, final IAsyncWorkCompletedCallback workCompletedCallback) {
        final String url = Contants.SERVICE_BASE_URL + Contants.Login;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                workCompletedCallback.onDone(response, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                workCompletedCallback.onDone(error.getMessage(), false);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phone);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
    }


    public void callRetailerListService(final IAsyncWorkCompletedCallback workCompletedCallback) {
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
//
////        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
////            @Override
////            public void onResponse(String response) {
////                parseAndSaveLoginData(response, workCompletedCallback);
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                workCompletedCallback.onDone("no", false);
////            }
////        }) {
////            @Override
////            protected Map<String, String> getParams() throws AuthFailureError {
////                Map<String, String> params = new HashMap<String, String>();
////                params.put("PhoneNumber", phone);
////                return params;
////            }
////        };
////
////        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
////        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
//
//
////        RequestQueue requestQueue = Volley.newRequestQueue(context);
////        requestQueue.add(stringRequest);
////    }
//
//    //parse and save login data
//    public void parseAndSaveLoginData(final String result, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        new AsyncTask<Void, Void, Boolean>() {
//
//
//            @Override
//            protected Boolean doInBackground(Void... voids) {
//                Boolean flag = false;
//                ContentDataAsArray data = new Gson().fromJson(result, ContentDataAsArray.class);
//                if (data != null) {
//                    DbHelper dbHelper = new DbHelper(context);
//                    for (Result objData : data.getResults()) {
//                        if (objData != null) {
//                            dbHelper.upsertUserData(objData);
//                        }
//                    }
//                    flag = true;
//
//                }
//
//                return flag;
//            }
//
//            @Override
//            protected void onPostExecute(Boolean flag) {
//                super.onPostExecute(flag);
//                if (flag) {
//                    workCompletedCallback.onDone("LoginService done", true);
//                } else {
//                    workCompletedCallback.onDone("LoginService done", false);
//                }
//            }
//        }.execute();
//    }
//
//    //call otp data
//    public void callOtpVerifyService(final String phone, final String otp, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        final String url = Contants.SERVICE_BASE_URL + Contants.VerifyOTP;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                workCompletedCallback.onDone(response, true);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone(error.getMessage(), false);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("phone", phone);
//                params.put("otp", otp);
//                return params;
//            }
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
//    }
//
//
//    //call all category list data
//    public void callAllCategoryListService(String categoryName, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        final String url = Contants.SERVICE_BASE_URL + Contants.GetAllCategoryList;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                workCompletedCallback.onDone(response, true);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone(error.getMessage(), false);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("main_category", categoryName);
//                return params;
//            }
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
//    }
//
//
//    //call all product list data
//    public void callAllProductListService(String categoryName, final int id, final int page, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        final String url = Contants.SERVICE_BASE_URL + Contants.GetAllProductList;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                workCompletedCallback.onDone(response, true);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone(error.getMessage(), false);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("main_category", categoryName);
//                params.put("id", String.valueOf(id));
//                params.put("page", String.valueOf(page));
//                return params;
//            }
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
//    }
//
//
//    //call all product list data by id
//    public void callAllProductListServiceById(final int id, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        final String url = Contants.SERVICE_BASE_URL + Contants.GetAllProductListById;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                workCompletedCallback.onDone(response, true);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone(error.getMessage(), false);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("id", String.valueOf(id));
//                return params;
//            }
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
//    }
//
//
//    //call add new address
//    public void callAddNewAddressService(final String address, final String landmark, final String city, final String name, final String pincode, final String locality, final String mobile, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        final String url = Contants.SERVICE_BASE_URL + Contants.AddNewAddress;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                workCompletedCallback.onDone(response, true);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone(error.getMessage(), false);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("address", address);
//                params.put("landmark", landmark);
//                params.put("city", city);
//                params.put("patient_name", name);
//                params.put("pincode", pincode);
//                params.put("locality", locality);
//                params.put("mobile", mobile);
//                return params;
//            }
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
//    }
//
//
//    //get all address data...........
//    public void callGetAllAddressService(final String phone, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        final String url = Contants.SERVICE_BASE_URL + Contants.GetAllAddress;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                workCompletedCallback.onDone(response, true);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone(error.getMessage(), false);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("mobile", phone);
//                return params;
//            }
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
//    }
//
//
//    //get delete address data...........
//    public void callDeleteAddressService(final int id, final String phone, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        final String url = Contants.SERVICE_BASE_URL + Contants.DeleteAddress;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                workCompletedCallback.onDone(response, true);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone(error.getMessage(), false);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id", String.valueOf(id));
//                params.put("mobile", phone);
//                return params;
//            }
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
//    }
//
//
//    //get update address data...........
//    public void callUpdateAddressService(final int id, final String address, final String landmark, final String city, final String name, final String pincode, final String locality, final String mobile, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        final String url = Contants.SERVICE_BASE_URL + Contants.UpdateAddress;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                workCompletedCallback.onDone(response, true);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone(error.getMessage(), false);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id", String.valueOf(id));
//                params.put("address", address);
//                params.put("landmark", landmark);
//                params.put("city", city);
//                params.put("patient_name", name);
//                params.put("pincode", pincode);
//                params.put("locality", locality);
//                params.put("mobile", mobile);
//                return params;
//            }
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
//    }
//
//
//    public void callUploadImageData(final String cid, final String dr_name, final String prescription_pic, final IAsyncWorkCompletedCallback workCompletedCallback ) {
//        String url = Contants.SERVICE_BASE_URL + Contants.uploadPrescription;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                workCompletedCallback.onDone(response, true);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone(error.toString(), false);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("dr_name", dr_name);
//                params.put("prescription_pic", prescription_pic);
//                params.put("cid", cid);
//                return params;
//            }
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);//, tag_json_obj);
//    }
//
//
//    //for get user profile................
//    public void getUserProfileService(int LoginID, final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.GetUserProfile;
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("LoginID", LoginID);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    parseGetUserProfileService(result, workCompletedCallback);
//                } else {
//                    workCompletedCallback.onDone("getUserProfileService done", false);
//                }
//            }
//        });
//    }
//
//
//    //parse and save get user profile
//    public void parseGetUserProfileService(final String result, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        new AsyncTask<Void, Void, Boolean>() {
//
//
//            @Override
//            protected Boolean doInBackground(Void... voids) {
//                Boolean flag = false;
//                ContentData data = new Gson().fromJson(result, ContentData.class);
//                if (data != null) {
//                    if (data.getResponse() != null && data.getResponse().getSuccess()) {
//                        if (data.getData() != null) {
//                            DbHelper dbhelper = new DbHelper(context);
////                            dbhelper.upsertUserData(data.getData());
//                            flag = true;
//                        }
//                    }
//                }
//                return flag;
//            }
//
//            @Override
//            protected void onPostExecute(Boolean flag) {
//                super.onPostExecute(flag);
//                if (flag) {
//                    workCompletedCallback.onDone("getUserProfileService done", true);
//                } else {
//                    workCompletedCallback.onDone("getUserProfileService done", false);
//                }
//            }
//        }.execute();
//    }
//
//    //update user profile
//    public void UpdateUserProfileService(int LoginID, String Name, String Phone, String Email, final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.UpdateProfile;
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("LoginID", LoginID);
//            obj.put("Name", Name);
//            obj.put("PhoneNumber", Phone);
//            obj.put("EmailID", Email);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    parseUpdateUserProfileService(result, workCompletedCallback);
//                } else {
//                    workCompletedCallback.onDone("UpdateUserProfileService done", false);
//                }
//            }
//        });
//    }
//
//    //parse and save update user profile
//    public void parseUpdateUserProfileService(final String result, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        new AsyncTask<Void, Void, Boolean>() {
//
//
//            @Override
//            protected Boolean doInBackground(Void... voids) {
//                Boolean flag = false;
//                ContentData data = new Gson().fromJson(result, ContentData.class);
//                if (data != null) {
//                    if (data.getResponse() != null && data.getResponse().getSuccess()) {
//                        if (data.getData() != null) {
//                            DbHelper dbhelper = new DbHelper(context);
////                            dbhelper.upsertUserData(data.getData());
//                            flag = true;
//                        }
//                    }
//                }
//                return flag;
//            }
//
//            @Override
//            protected void onPostExecute(Boolean flag) {
//                super.onPostExecute(flag);
//                if (flag) {
//                    workCompletedCallback.onDone("UpdateUserProfileService done", true);
//                } else {
//                    workCompletedCallback.onDone("UpdateUserProfileService done", false);
//                }
//            }
//        }.execute();
//    }
//
//    //Set AddNewAddress  data
//    public void SetNewAddressService(final int LoginId, final String completeAddress, final String phoneNumber, final String zipcode, final String landmark, final String city, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        final String url = Contants.SERVICE_BASE_URL + Contants.AddNewAddress;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                workCompletedCallback.onDone(response, true);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone("no", false);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("LoginID", String.valueOf(LoginId));
//                params.put("CompleteAddress", completeAddress);
//                params.put("PhoneNumber", phoneNumber);
//                params.put("ZipCode", zipcode);
//                params.put("LandMark", landmark);
//                params.put("cityName", city);
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);
//    }
//
//    //Set update Address  data
//    public void updateAddressService(int addressId, int LoginId, String completeAddress, String phoneNumber, String zipcode, String landmark, final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.UpdateAddress;
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("AddressId", addressId);
//            obj.put("LoginID", LoginId);
//            obj.put("CompleteAddress", completeAddress);
//            //obj.put("CityId", cityId);
//            obj.put("LandMark", landmark);
//            obj.put("PhoneNumber", phoneNumber);
//            obj.put("ZipCode", zipcode);
////            obj.put("LocalityId", LocalityId);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    workCompletedCallback.onDone("updateAddressService done", true);
//                } else {
//                    workCompletedCallback.onDone("updateAddressService done", false);
//                }
//            }
//        });
//    }
//
//
//    //call Delete address data
//    public void callDeleteAddressDataService(int addressId, int loginId, final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.DeleteAddress;
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("AddressId", addressId);
//            obj.put("LoginID", loginId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    ContentData d = new Gson().fromJson(result, ContentData.class);
//                    if (d.getResult() != null) {
//                        if (d.getResult().getSuccess()) {
//                            workCompletedCallback.onDone("Address delete done", true);
//                        } else {
//                            workCompletedCallback.onDone("Address delete not done", false);
//                        }
//                    } else {
//                        workCompletedCallback.onDone("Address delete not done", false);
//                    }
//                }
//            }
//        });
//    }
//
//
////    //call All CategoryList data
////    public void callAllCategoryListService(final IAsyncWorkCompletedCallback workCompletedCallback) {
////
////        final String url = Contants.SERVICE_BASE_URL + Contants.GetAllCategory;
////        JSONObject obj = new JSONObject();
////        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
////        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
////            @Override
////            public void onDone(String doneWhatCode, String result, String error) {
////                if (result != null) {
//////                    workCompletedCallback.onDone(result, true);
////                    parseAndSaveAllCategoryListData(result, workCompletedCallback);
////                } else {
////                    workCompletedCallback.onDone("callAllCategoryListService done", false);
////                }
////            }
////        });
////    }
//
//    //parse and save category  data
//    public void parseAndSaveAllCategoryListData(final String result, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        new AsyncTask<Void, Void, Boolean>() {
//
//
//            @Override
//            protected Boolean doInBackground(Void... voids) {
//                Boolean flag = false;
//                ContentDataAsArray data = new Gson().fromJson(result, ContentDataAsArray.class);
//                if (data != null) {
//                    DbHelper dbHelper = new DbHelper(context);
//                    for (Result objData : data.getResults()) {
//                        if (objData != null) {
//                            dbHelper.upsertCategoryData(objData);
//                        }
//                    }
//                    flag = true;
//
//                }
//
//                return flag;
//            }
//
//            @Override
//            protected void onPostExecute(Boolean flag) {
//                super.onPostExecute(flag);
//                if (flag) {
//                    workCompletedCallback.onDone("city done", true);
//                } else {
//                    workCompletedCallback.onDone("city done", false);
//                }
//            }
//        }.execute();
//    }
//
//    //call All lab test data
//    public void callAllLabTestService(final IAsyncWorkCompletedCallback workCompletedCallback) {
//        final String url = Contants.SERVICE_BASE_URL + Contants.getAllLabTests;
//        JSONObject obj = new JSONObject();
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    workCompletedCallback.onDone(result, true);
////                    parseAndSaveAllCategoryListData(result, workCompletedCallback);
//                } else {
//                    workCompletedCallback.onDone("callAllCategoryListService done", false);
//                }
//            }
//        });
//    }
//
//    //call All article data
//    public void callAllHealthArticleService(final IAsyncWorkCompletedCallback workCompletedCallback) {
//        final String url = Contants.SERVICE_BASE_URL + Contants.getAllLHealthArticles;
//        JSONObject obj = new JSONObject();
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    workCompletedCallback.onDone(result, true);
////                    parseAndSaveAllCategoryListData(result, workCompletedCallback);
//                } else {
//                    workCompletedCallback.onDone("callAllCategoryListService done", false);
//                }
//            }
//        });
//    }
//
//    //get all presp data...........
//    public void callGetAllPrescriptionService(final int LoginId, final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.getAllPrescription;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                workCompletedCallback.onDone(response, true);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone("no", false);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("loginId", String.valueOf(LoginId));
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);
//    }
//
//    //get allsearch product data...........
//    public void callSearchProductService(final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.productSearch;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                parseAndSaveSearchData(response, workCompletedCallback);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone("no", false);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
////                params.put("query", "Ce");
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);
//    }
//
//    //parse and save city  data
//    public void parseAndSaveSearchData(final String result, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        new AsyncTask<Void, Void, Boolean>() {
//
//
//            @Override
//            protected Boolean doInBackground(Void... voids) {
//                Boolean flag = false;
//                ContentDataAsArray data = new Gson().fromJson(result, ContentDataAsArray.class);
//                if (data != null) {
//                    DbHelper dbHelper = new DbHelper(context);
//                    for (Result objData : data.getResults()) {
//                        if (objData != null) {
//                            dbHelper.upsertSearchProductData(objData);
//                        }
//                    }
//                    flag = true;
//
//                }
//
//                return flag;
//            }
//
//            @Override
//            protected void onPostExecute(Boolean flag) {
//                super.onPostExecute(flag);
//                if (flag) {
//                    workCompletedCallback.onDone("city done", true);
//                } else {
//                    workCompletedCallback.onDone("city done", false);
//                }
//            }
//        }.execute();
//    }
//
//
//    //call  viewpager  data
//    public void callViewPagerService(final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.getViewPager;
//        JSONObject obj = new JSONObject();
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    workCompletedCallback.onDone(result, true);
////                    parseAndSaveAllCategoryListData(result, workCompletedCallback);
//                } else {
//                    workCompletedCallback.onDone("callAllCategoryListService done", false);
//                }
//            }
//        });
//    }
//
//    //call  city  data
//    public void callCityService(final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.getCity;
//        JSONObject obj = new JSONObject();
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
////                    workCompletedCallback.onDone(result, true);
//                    parseAndSaveCityData(result, workCompletedCallback);
//                } else {
//                    workCompletedCallback.onDone("city data done", false);
//                }
//            }
//        });
//    }
//
//    //parse and save city  data
//    public void parseAndSaveCityData(final String result, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        new AsyncTask<Void, Void, Boolean>() {
//
//
//            @Override
//            protected Boolean doInBackground(Void... voids) {
//                Boolean flag = false;
//                ContentDataAsArray data = new Gson().fromJson(result, ContentDataAsArray.class);
//                if (data != null) {
//                    DbHelper dbHelper = new DbHelper(context);
//                    for (Result objData : data.getResults()) {
//                        if (objData != null) {
//                            dbHelper.upsertCityData(objData);
//                        }
//                    }
//                    flag = true;
//
//                }
//
//                return flag;
//            }
//
//            @Override
//            protected void onPostExecute(Boolean flag) {
//                super.onPostExecute(flag);
//                if (flag) {
//                    workCompletedCallback.onDone("city done", true);
//                } else {
//                    workCompletedCallback.onDone("city done", false);
//                }
//            }
//        }.execute();
//    }
//
//    public void callProductData(final int categoryId, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        final String url = Contants.SERVICE_BASE_URL + Contants.GetAllProductByCategory;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                workCompletedCallback.onDone(response, true);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone("no", false);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("CategoryId", String.valueOf(categoryId));
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);
//    }
//
//
//    //............................. my all order history.................................
//
//    //get My All Order  history data................
//    public void callMyAllOrderHistoryService(int LoginId, final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.GetMyAllOrderHistory;
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("LoginId", LoginId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    parseAndSaveMyAllOrderHistoryData(result, workCompletedCallback);
//                } else {
//                    workCompletedCallback.onDone("callMyAllOrderHistoryService done", false);
//                }
//            }
//        });
//    }
//
//    //parse and save All MyOrder   History data
//    public void parseAndSaveMyAllOrderHistoryData(final String result, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        new AsyncTask<Void, Void, Boolean>() {
//
//
//            @Override
//            protected Boolean doInBackground(Void... voids) {
//                Boolean flag = false;
//                ContentDataAsArray data = new Gson().fromJson(result, ContentDataAsArray.class);
//                if (data != null) {
//                    if (data.getResponse() != null && data.getResponse().getSuccess()) {
//                        DbHelper dbHelper = new DbHelper(context);
//                        for (Data objData : data.getData()) {
//                            if (objData != null) {
//                                dbHelper.upsertMyAllOrderHistoryData(objData);
//                            }
//                        }
//                        flag = true;
//
//                    }
//                }
//
//                return flag;
//            }
//
//            @Override
//            protected void onPostExecute(Boolean flag) {
//                super.onPostExecute(flag);
//                if (flag) {
//                    workCompletedCallback.onDone("callMyAllOrderHistoryService done", true);
//                } else {
//                    workCompletedCallback.onDone("callMyAllOrderHistoryService done", false);
//                }
//            }
//        }.execute();
//    }
//    //............................. get all order by User (Track Order).................................
//
//    //get all order by User  data................
//    public void callGetAllOrderByUserService(int LoginId, final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.GetAllOrderByUser;
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("LoginId", LoginId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    parseAndSaveGetAllOrderByUserServiceData(result, workCompletedCallback);
//                } else {
//                    workCompletedCallback.onDone("callGetAllOrderByUserService done", false);
//                }
//            }
//        });
//    }
//
//    //parse and save Get All Order By User Service data
//    public void parseAndSaveGetAllOrderByUserServiceData(final String result, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        new AsyncTask<Void, Void, Boolean>() {
//
//
//            @Override
//            protected Boolean doInBackground(Void... voids) {
//                Boolean flag = false;
//                ContentDataAsArray data = new Gson().fromJson(result, ContentDataAsArray.class);
//                if (data != null) {
//                    if (data.getResponse() != null && data.getResponse().getSuccess()) {
//                        DbHelper dbHelper = new DbHelper(context);
//                        for (Data objData : data.getData()) {
//                            if (objData != null) {
//                                dbHelper.upsertGetAllOrderByUserData(objData);
//                            }
//                        }
//                        flag = true;
//                    }
//                }
//
//                return flag;
//            }
//
//            @Override
//            protected void onPostExecute(Boolean flag) {
//                super.onPostExecute(flag);
//                if (flag) {
//                    workCompletedCallback.onDone("callGetAllOrderByUserService done", true);
//                } else {
//                    workCompletedCallback.onDone("callGetAllOrderByUserService done", false);
//                }
//            }
//        }.execute();
//    }
//
//    //create order service
//    public void createOrderService(final int addressId, final int LoginId, ArrayList<CreateOrderDetails> orderDetailsesList, double totalPrice, double dis, float shippingChareges, double grandTotal, final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.CreateOrder;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                workCompletedCallback.onDone(response, true);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                workCompletedCallback.onDone(error.toString(), false);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("AddressId", String.valueOf(addressId));
//                params.put("LoginID", String.valueOf(LoginId));
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);
//    }
//
//    //GetOrderByOrderNumber
//    public void GetOrderByOrderNumberService(String OrderNumber, int StoreId, final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.GetOrderByOrderNumber;
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("OrderNumber", OrderNumber);
//            obj.put("StoreId", StoreId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    workCompletedCallback.onDone(result, true);
////                    Log.d(Contants.LOG_TAG, "dataahjhhh*****" + result);
////                    parseAndSaveGetOrderByOrderNumber(result, workCompletedCallback);
//                } else {
//                    workCompletedCallback.onDone("GetOrderByOrderNumber done", false);
//                }
//            }
//        });
//    }
//
////    //parse and save GetOrderByOrderNumber
////    public void parseAndSaveGetOrderByOrderNumber(final String result, final IAsyncWorkCompletedCallback workCompletedCallback) {
////        new AsyncTask<Void, Void, Boolean>() {
////            @Override
////            protected Boolean doInBackground(Void... voids) {
////                Boolean flag = false;
////                ContentData contentData = new Gson().fromJson(result, ContentData.class);
////                if (contentData != null) {
////                    Data orderData = contentData.getData();
////                    if (orderData != null) {
////                        DbHelper dbHelper = new DbHelper(context);
////                        dbHelper.upsertGetAllOrderByUserData(orderData);
////                    }
////                    flag = true;
////                }
////                return flag;
////            }
////
////            @Override
////            protected void onPostExecute(Boolean flag) {
////                super.onPostExecute(flag);
////                if (flag) {
////                    workCompletedCallback.onDone("GetOrderByOrderNumberService done", true);
////                } else {
////                    workCompletedCallback.onDone("GetOrderByOrderNumberService done", false);
////                }
////            }
////        }.execute();
////    }
//
//    // track order status data..................................
////GetOrderByOrderNumber
//    public void callTrackOrderStatusService(String OrderNumber, int StoreId, int LoginID, final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.TrackOrderStatus;
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("OrderNumber", OrderNumber);
//            obj.put("StoreId", StoreId);
//            obj.put("LoginID", LoginID);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    // workCompletedCallback.onDone(result, true);
//                    parseAndSavecallTrackOrderStatusService(result, workCompletedCallback);
//                } else {
//                    workCompletedCallback.onDone("callTrackOrderStatusService done", false);
//                }
//            }
//        });
//    }
//
//    //parse and save GetOrderByOrderNumber
//    public void parseAndSavecallTrackOrderStatusService(final String result, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        new AsyncTask<Void, Void, Boolean>() {
//            @Override
//            protected Boolean doInBackground(Void... voids) {
//                Boolean flag = false;
//                ContentData contentData = new Gson().fromJson(result, ContentData.class);
//                if (contentData != null) {
//                    Data orderData = contentData.getData();
//                    if (orderData != null) {
//                        DbHelper dbHelper = new DbHelper(context);
//                        dbHelper.upsertGetAllOrderByUserData(orderData);
//                    }
//                    flag = true;
//                }
//                return flag;
//            }
//
//            @Override
//            protected void onPostExecute(Boolean flag) {
//                super.onPostExecute(flag);
//                if (flag) {
//                    workCompletedCallback.onDone("callTrackOrderStatusService done", true);
//                } else {
//                    workCompletedCallback.onDone("callTrackOrderStatusService done", false);
//                }
//            }
//        }.execute();
//    }
//
//    //upload image  service
//    public void UploadImageService(int LoginId, String base64Image, final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.UploadProfilePicture;
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("LoginID", LoginId);
//            obj.put("ImageUrl", base64Image);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    workCompletedCallback.onDone("UploadImageService done", true);
//                } else {
//                    workCompletedCallback.onDone("UploadImageService done", false);
//                }
//            }
//        });
//    }
//
//    //call social login data
//    public void callSocialLoginService(String email, String personName, String phone, String mAccessToken, String FirebaseToken, String fb_url, final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.SocialUserLogin;
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("PhoneNumber", phone);
//            obj.put("Name", personName);
//            obj.put("EmailID", email);
//            obj.put("FbToken", mAccessToken);
//            obj.put("DeviceId", FirebaseToken);
//            obj.put("ProfilePictureUrl", fb_url);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    parseAndSaveLoginData(result, workCompletedCallback);
//                } else {
//                    workCompletedCallback.onDone("loginService done", false);
//                }
//            }
//        });
//    }
//
//    //make usfavorite store
//    public void makeUnFavoriteStoreService(int loginId, final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.RemoveFavouriteStoreByUser;
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("LoginID", loginId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    workCompletedCallback.onDone(result, true);
//                } else {
//                    workCompletedCallback.onDone("PaymentGateway done", false);
//                }
//            }
//        });
//    }
//
//    //get payment url
//    public void getPaymentUrlService(String orderNo, int loginId, final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.CreatePaymentOrder;
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("OrderNumber", orderNo);
//            obj.put("LoginID", loginId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    workCompletedCallback.onDone(result, true);
//                } else {
//                    workCompletedCallback.onDone("PaymentGateway done", false);
//                }
//            }
//        });
//    }
//
//
//    //confirm payment details service...........
//    //get payment url
//    public void sendConfirmDetailsToServerService(int loginId, int OrderId, String OrderNo, int StoreId, String transaction_id, String payment_id, String PaymentOrderId, String PaymentMode, final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.PaymentConfirmation;
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("LoginId", loginId);
//            obj.put("OrderId", OrderId);
//            obj.put("OrderNumber", OrderNo);
//            obj.put("StoreId", StoreId);
//            obj.put("TransactionId", transaction_id);
//            obj.put("PaymentId", payment_id);
//            obj.put("PaymentOrderId", PaymentOrderId);
//            obj.put("PaymentMode", PaymentMode);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    workCompletedCallback.onDone(result, true);
//                } else {
//                    workCompletedCallback.onDone("PaymentConfirm done", false);
//                }
//            }
//        });
//    }
//
//
//    //send device token to server
//    public void callSendDeviceTokenRegistrationToServer(final IAsyncWorkCompletedCallback workCompletedCallback) {
//
//        final String url = Contants.SERVICE_BASE_URL + Contants.Notification;
//        String deviceToken = Utility.getDeviceIDFromSharedPreferences(context);
//        DbHelper dbHelper = new DbHelper(context);
//        Result userData = dbHelper.getUserData();
//        int loginId = 0;
//        if (userData != null) {
//            loginId = userData.getLoginId();
//        }
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("DeviceId", deviceToken);
//            if (loginId != 0) {
//                //obj.put("LoginId", loginId);
//            }
//            // obj.put("DeviceName", Utility.getDeviceName());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    ContentData data = new Gson().fromJson(result, ContentData.class);
//                    if (data != null) {
//                        workCompletedCallback.onDone("DeviceTokenAddService done", true);
//                    } else {
//                        workCompletedCallback.onDone("DeviceTokenAddService done", false);
//                    }
//                } else {
//                    workCompletedCallback.onDone("DeviceTokenAddService done", false);
//                }
//            }
//        });
//    }
//
//
//    public void getCODService(String orderNo, int loginId, String PaymentMode, final IAsyncWorkCompletedCallback workCompletedCallback) {
//        final String url = Contants.SERVICE_BASE_URL + Contants.PaymentConfirmforCOD;
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("OrderNumber", orderNo);
//            obj.put("LoginId", loginId);
//            obj.put("PaymentMode", PaymentMode);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.d(Contants.LOG_TAG, "Payload*****" + obj);
//        new ServiceHelper().callService(url, obj, new IServiceSuccessCallback() {
//            @Override
//            public void onDone(String doneWhatCode, String result, String error) {
//                if (result != null) {
//                    workCompletedCallback.onDone(result, true);
//                } else {
//                    workCompletedCallback.onDone("PaymentGateway done", false);
//                }
//            }
//        });
//    }
}
