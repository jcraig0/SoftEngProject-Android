package com.example.thorm.test4;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ICHelper {

    public static ICHelper ICH;

    private static String baseURL = "https://umcos420gp.com/server/public";

    public static class Token{
        private String value = null;

        public void setValue(String s){
            this.value = new String(s);
        }

        public String getValue(){
            return this.value;
        }
    }
    public static Token token = new Token();

    private static Context context;
    public static RequestQueue queue;

    public ICHelper(Context context){
        this.context = context;
        queue = Volley.newRequestQueue(this.context);
    }

    public static boolean loginRequest(final String username, final String password){
        String url = "/authenticate";
        try{
            StringRequest postRequest = new StringRequest(Request.Method.POST, baseURL.concat(url),
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);

                            try {
                                JSONObject j = new JSONObject(response);

                                Log.d("MyToken", j.get("token").toString());

                                ICHelper.token.setValue(j.get("token").toString());

                                ICHelper.getEmployeeList();

                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("username", username);
                    params.put("password", password);

                    return params;
                }


            };
            queue.add(postRequest);
        }finally{
            if (ICHelper.token != null){
                return true;
            }else{
                return false;
            }
        }
    }

    public static class EmployeeJSONArray{
        public JSONArray Value = null;

        public void setValue(JSONArray j){
            Value = j;
        }

        public JSONArray getValue(){
            if (this.Value != null) {
                return Value;
            }else{
                return null;
            }
        }
    }
    public static EmployeeJSONArray employeeJArray = new EmployeeJSONArray();

    public static void getEmployeeList(){
        String url = "/employees";
        try{
            StringRequest getRequest = new StringRequest(Request.Method.GET, baseURL.concat(url),
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);

                            try {
                                JSONObject j = new JSONObject(String.format("{\"test\": %s}", response));
                                ICHelper.employeeJArray.setValue(j.getJSONArray("test"));

                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", error.toString());
                        }
                    }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError{
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "applications/x-www-form-urlencoded");
                        params.put("Authorization", String.format("Bearer %s", ICHelper.token.getValue()));
                        return params;
                    }
                };

            queue.add(getRequest);
        }finally{

        }
    }


}
