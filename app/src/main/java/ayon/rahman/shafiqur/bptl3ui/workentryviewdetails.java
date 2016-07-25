package ayon.rahman.shafiqur.bptl3ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class workentryviewdetails extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public long endh, endm, starth, startm;
    public int endmonth, enddate, endyear, startmonth, startdate, startyear;
    String selectedworkspinner, PRE_JOB_REF_NO, selectedmot, refNoPassed, usernamepassed, startDatePassed, endDatePassed, serverdailywork = "http://103.229.84.171/dailywork.php",
            serviceselected = "", servernameforservice = "http://103.229.84.171/service.php",
            sernameforclientinfo = "http://103.229.84.171/clientnametoworkid.php", clients = "", clientselected = "", clientselectedid = "",
            temp = null, temp2 = null, servernameforwork = "http://103.229.84.171/wsnamesid.php", previousDate = "http://103.229.84.171/updateDataShow.php", updateServer = "http://103.229.84.171/workEntryUpdate.php";
    String sworkstationnamespinner, smediumoftransportspinner, sclientSpinner, sremarks, sstarttime, sendtime, sPRE_JOB_REF_NO, sclientId, mot, wsCode, jobid;
    TextView tv1, tvClientName, tvWorkstation, jobName, tvMot, tvendtimetext, tvEndtime, tvRemarks;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workentryviewdetails);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        refNoPassed = getIntent().getExtras().getString("refno");
        startDatePassed = getIntent().getExtras().getString("startDate");
        endDatePassed = getIntent().getExtras().getString("endDate");
        usernamepassed = getIntent().getExtras().getString("username");
        /*usernamepassed = "S-001";
        refNoPassed = "S-001-160716";*/
        requestQueue = Volley.newRequestQueue(workentryviewdetails.this);


        tv1 = (TextView) findViewById(R.id.textView22);
        tvClientName = (TextView) findViewById(R.id.textView23);
        tvWorkstation = (TextView) findViewById(R.id.textView24);
        jobName = (TextView) findViewById(R.id.textView25);
        tvMot = (TextView) findViewById(R.id.textView26);
        tvendtimetext = (TextView) findViewById(R.id.textView27);
        tvEndtime = (TextView) findViewById(R.id.textView28);
        tvRemarks = (TextView) findViewById(R.id.textView29);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, previousDate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("workentryviewdetails ", response);
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.e("Json array length", String.valueOf(jsonArray.length()));
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                   /* JSONObject jsonObject = new JSONObject(response);*/
                        Log.e("workupdate entry Json", String.valueOf(jsonObject));
                        if (jsonObject.isNull("PRE_JOB_REF_NO") == false) {
                            Log.e("job ref no", String.valueOf(jsonObject.get("PRE_JOB_REF_NO")));
                        } else {

                        }
                        if (jsonObject.isNull("CLIENT_ID") == false) {
                            Log.e("CLIENT_ID", String.valueOf(jsonObject.get("CLIENT_ID")));
                            sclientId = String.valueOf(jsonObject.get("CLIENT_ID"));
                            Log.e("sclientId", sclientId);

                            //client name spinner
                            StringRequest stringRequestClient = new StringRequest(Request.Method.POST, sernameforclientinfo, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    JSONArray jsonArray = null;
                                    try {
                                        jsonArray = new JSONArray(response);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            temp = null;
                                            temp2 = null;
                                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                            temp = (String) jsonObject.get("CLIENT_NAME");
                                            temp2 = (String) jsonObject.get("CLIENT_ID");
                                            if (temp2.equals(sclientId))
                                                tvClientName.setText("Client Name :" + temp);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Volley E client ", error.toString());
                                }
                            }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("user", usernamepassed);
                                    return params;
                                }
                            };
                            requestQueue.add(stringRequestClient);
                        } else {
                        }
                        if (jsonObject.isNull("JOB_ID") == false) {
                            Log.e("JOB_ID", String.valueOf(jsonObject.get("JOB_ID")));
                            jobid = String.valueOf(jsonObject.get("JOB_ID"));
                            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(servernameforservice, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray jsonArray) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        try {
                                            JSONObject object = (JSONObject) jsonArray.get(i);
                                            temp = null;
                                            temp2 = null;
                                            temp = (String) object.get("JOB_ID");
                                            temp2 = (String) object.get("JOB_NAME");
                                            if (temp.equals(jobid))
                                                jobName.setText("Job Name :" + temp2);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    ;
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                }
                            });
                            requestQueue.add(jsonArrayRequest);
                        } else {
                            jobid = "1";
                        }
                        if (jsonObject.isNull("REMARKS") == false) {
                            sremarks = String.valueOf(jsonObject.get("REMARKS"));
                            tvRemarks.setText("Remarks :" + sremarks);
                        } else {
                            tvRemarks.setText("Not Found");
                        }
                        if (jsonObject.isNull("START_TIME") == false) {
                            sstarttime = (String) jsonObject.get("START_TIME");
                            tvendtimetext.setText("Start Time :" + sstarttime);

                        } else {
                            tvendtimetext.setText("Could not find Start Time");
                        }
                        if (jsonObject.isNull("MOT") == false) {
                            Log.e("MOT", String.valueOf(jsonObject.get("MOT")));
                            mot = (String) jsonObject.get("MOT");
                            //setting up the medium of transport
                            String[] mots = new String[]{"Train", "Rickshaw", "CNG", "Taxicab", "Bus"};
                            tvMot.setText("MOT Name :" + mot);
                        } else {
                        }
                        if (jsonObject.isNull("FWS_CODE") == false) {
                            Log.e("FWS_CODE", String.valueOf(jsonObject.get("FWS_CODE")));
                            wsCode = String.valueOf(jsonObject.get("FWS_CODE"));
                            JsonArrayRequest jsonArrayRequestworkstation = new JsonArrayRequest(servernameforwork, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray jsonArray) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        try {
                                            temp = null;
                                            temp2 = null;
                                            JSONObject object = (JSONObject) jsonArray.get(i);
                                            temp = object.getString("WS_NAME");
                                            Log.e("workstationname", temp);
                                            temp2 = object.getString("WS_CODE");
                                            Log.e("workstationcode", temp2);
                                            if (temp2.equals(wsCode))
                                                tvWorkstation.setText("Work Station Name :" + temp);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Log.e("ws error", volleyError.toString());
                                }
                            });
                            requestQueue.add(jsonArrayRequestworkstation);
                        } else {

                        }
                        if (jsonObject.isNull("END_TIME") == false) {

                            sendtime = (String) jsonObject.get("END_TIME");
                            tvEndtime.setText("End Time :" + sendtime);
                            Log.e("END_TIME", String.valueOf(jsonObject.get("END_TIME")));
                        } else {
                            tvEndtime.setText("Could not find end time");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", " preset data" + error.toString());
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("refno", refNoPassed);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            Toast.makeText(getApplicationContext(), "Logging Out", Toast.LENGTH_SHORT).show();
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent i = new Intent(workentryviewdetails.this, Client_Setup.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(workentryviewdetails.this, dailyworkprogram.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(workentryviewdetails.this, clientView.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(workentryviewdetails.this, clientViewByDate.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        } else if (id == R.id.nav_dash) {
            Intent i = new Intent(workentryviewdetails.this, dash.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
