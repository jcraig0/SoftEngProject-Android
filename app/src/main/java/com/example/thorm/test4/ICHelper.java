package com.example.thorm.test4;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

    public static boolean loginRequest(final String USERNAME, final String PASSWORD){
        String url = "/authenticate";
        try{
            StringRequest postRequest = new StringRequest(Request.Method.POST, baseURL.concat(url),
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
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
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("username", USERNAME);
                    params.put("password", PASSWORD);
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

    public static void getJobsFor(final Employee EMPLOYEE){
        String url = String.format("/employee/%s/jobs", EMPLOYEE.getID());
        try{
            StringRequest getRequest = new StringRequest(Request.Method.GET, baseURL.concat(url),
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject j = new JSONObject(String.format("{\"test\": %s}", response));
                                JSONArray jArrary = j.getJSONArray("test");
                                for (int i = 0; i < jArrary.length(); i++){
                                    JSONObject jObj = jArrary.getJSONObject(i);
                                    EMPLOYEE.addJob(jObj);
                                }
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
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

    public static void postHours(final Employee EMPLOYEE, final int JOBNUM, final int SHIFTNUM){
        String url = "/hours";
        StringRequest postHours = new StringRequest(Request.Method.POST, baseURL.concat(url),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError
            {
                Employee.Job j = EMPLOYEE.getJobAt(JOBNUM);
                EmployeeShift s = j.shifts.get(SHIFTNUM);

                Map<String, String>  params = new HashMap<String, String>();
                params.put("employee_id", EMPLOYEE.getEmployeeID());
                params.put("job_id", j.getID());
                params.put("date", s.getStringDate());
                params.put("shift_number", String.valueOf(SHIFTNUM));

                if (j.getType() == Employee.JobType.STARTEND){
                    params.put("start", s.getStringDate().concat(" ").concat(s.getStartTimeString()));
                    params.put("end", s.getStringDate().concat(" ").concat(s.getEndTimeString()));
                }else if (j.getType() == Employee.JobType.AMOUNT) {
                    params.put("quantity", s.getAmount());
                }
                Log.d("Parameters:", params.toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "applications/x-www-form-urlencoded");
                headers.put("Authorization", String.format("Bearer %s", ICHelper.token.getValue()));
                return headers;
            }
        };
        queue.add(postHours);
    }

}
