package com.ronju.covid_19tracker.DoInBackground;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ronju.covid_19tracker.Model.Bd_dis_item;
import com.ronju.covid_19tracker.MySingleton;
import com.ronju.covid_19tracker.Model.WorldDataItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;



public class doInBackground {
    private final String COUNTRY_API_URL = "https://corona.lmao.ninja/v2/countries";
    private final String BD_API_DISTRICT = "http://covid19tracker.gov.bd/api/district";
    //private final String DRIVE_DISTRICT_API ="https://drive.google.com/uc?export=download&id=1I5J28gAGs2XmFOL211duVO-b1PAZ5Ogc";
    private Context context;

    public doInBackground(Context context) {
        this.context = context;
    }


    //country data service

    public interface VolleyResponseListener {
        void onResponse(ArrayList<WorldDataItem> wData);

        void onErrorResponse();
    }

    public void CountryDataService(VolleyResponseListener volleyResponseListener) {
        ArrayList<WorldDataItem> wData = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, COUNTRY_API_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String flagUrl = response.getJSONObject(i).getJSONObject("countryInfo").getString("flag");
                        long updatedTime = response.getJSONObject(i).getLong("updated");
                        String countryName = response.getJSONObject(i).getString("country");
                        long totalCases = response.getJSONObject(i).getLong("cases");
                        long todayCases = response.getJSONObject(i).getLong("todayCases");
                        long totalDeaths = response.getJSONObject(i).getLong("deaths");
                        long todayDeaths = response.getJSONObject(i).getLong("todayDeaths");
                        long totalRecovered = response.getJSONObject(i).getLong("recovered");
                        long todayRecovered = response.getJSONObject(i).getLong("todayRecovered");
                        long activeCase = response.getJSONObject(i).getLong("active");
                        long totalTastes = response.getJSONObject(i).getLong("tests");
                        long totalPopulation = response.getJSONObject(i).getLong("population");
                        long ID = response.getJSONObject(i).getJSONObject("countryInfo").getLong("_id");

                        //adding data inside array
                        wData.add(new WorldDataItem(flagUrl, countryName, updatedTime, totalCases, todayCases, totalDeaths, todayDeaths, totalRecovered, todayRecovered, activeCase, totalTastes, totalPopulation,ID));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                volleyResponseListener.onResponse(wData);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onErrorResponse();
            }
        });
        MySingleton.getInstance(context).getRequestQueue().add(jsonArrayRequest);
    }


    // bd district service

    public interface VolleyDisResponseListener
    {
        void onResponse(ArrayList<Bd_dis_item> dataBd);
        void onErrorResponse();
    }

    public void districtDataService(VolleyDisResponseListener volleyDisResponseListener) {
        ArrayList<Bd_dis_item> dataBd = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BD_API_DISTRICT, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("features");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject("properties");

                    String name = jsonObject.getString("name");
                      String bnName = jsonObject.getString("bnName");
                      int confirmed = jsonObject.getInt("confirmed");
                      dataBd.add(new Bd_dis_item(name, bnName, confirmed));
                    }
                    volleyDisResponseListener.onResponse(dataBd);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               volleyDisResponseListener.onErrorResponse();
            }
        });
        MySingleton.getInstance(context).getRequestQueue().add(jsonObjectRequest);

    }
}