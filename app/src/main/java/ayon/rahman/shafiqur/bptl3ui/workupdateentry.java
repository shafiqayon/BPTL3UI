package ayon.rahman.shafiqur.bptl3ui;

import android.app.TimePickerDialog;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class workupdateentry extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public long endh, endm, starth, startm;
    public int endmonth, enddate, endyear, startmonth, startdate, startyear;
    Button starttime, endtime;
    TextView startTV, endTV;
    EditText Remarks, hourset;
    String[] servicenamearray;
    Spinner workstationnamespinner, mediumoftransportspinner, clientSpinner, servicenamespinner;
    String sworkstationnamespinner, smediumoftransportspinner, sclientSpinner, sremarks, sstarttime, sendtime, sPRE_JOB_REF_NO, sclientId, mot, wsCode, jobid;
    String selectedworkspinner, PRE_JOB_REF_NO, selectedmot, refNoPassed, usernamepassed, startDatePassed, endDatePassed, serverdailywork = "http://103.229.84.171/dailywork.php",
            serviceselected = "", servernameforservice = "http://103.229.84.171/service.php",
            sernameforclientinfo = "http://103.229.84.171/clientnametoworkid.php", clients = "", clientselected = "", clientselectedid = "",
            temp = null, temp2 = null, servernameforwork = "http://103.229.84.171/wsnamesid.php", previousDate = "http://103.229.84.171/updateDataShow.php", updateServer = "http://103.229.84.171/workEntryUpdate.php";
    ArrayAdapter<String> clientAdapter, serviceNameAdapter;
    long startmil;
    Button savebutton;
    RequestQueue requestQueue;
    private ArrayList<String> clientListName = new ArrayList<String>();
    private ArrayList<String> clientListId = new ArrayList<String>();
    private ArrayList<String> workstationnamelistforspinner = new ArrayList<String>();
    private ArrayList<String> workstationidlistforspinner = new ArrayList<String>();
    private ArrayList<String> serviceName = new ArrayList<String>();
    private ArrayList<String> serviceNameId = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workupdateentry);
        refNoPassed = getIntent().getExtras().getString("refno");
        startDatePassed = getIntent().getExtras().getString("startDate");
        endDatePassed = getIntent().getExtras().getString("endDate");
        usernamepassed = getIntent().getExtras().getString("username");
        Log.e("usrnam psd upd", usernamepassed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        requestQueue = Volley.newRequestQueue(workupdateentry.this);
        starttime = (Button) findViewById(R.id.startTimeBtnu);
        endtime = (Button) findViewById(R.id.endButtonu);
        startTV = (TextView) findViewById(R.id.startTimetextViewu);
        endTV = (TextView) findViewById(R.id.endTimetextViewu);
        Remarks = (EditText) findViewById(R.id.remarksu);
        savebutton = (Button) findViewById(R.id.saveworku);


        mediumoftransportspinner = (Spinner) findViewById(R.id.motu);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, previousDate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("workupdateentry ", response);
                Log.e("usrnam psd upd", usernamepassed);
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
                                            clientListName.add(temp);
                                            temp2 = (String) jsonObject.get("CLIENT_ID");
                                            clientListId.add(temp2);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    clientSpinner = (Spinner) findViewById(R.id.clientNameInSpinneru);
                                    clientAdapter = new ArrayAdapter<String>(workupdateentry.this, android.R.layout.simple_spinner_item, clientListName);
                                    clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    System.out.println("selection was" + clientAdapter.getPosition(clientListName.get(clientListId.indexOf(sclientId))));
                                    clientSpinner.setAdapter(clientAdapter);
                                    clientAdapter.notifyDataSetChanged();
                                    clientSpinner.setSelection(clientAdapter.getPosition(clientListName.get(clientListId.indexOf(sclientId))));
                                    clientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            Log.e("item", (String) parent.getItemAtPosition(position));
                                            clientselected = parent.getItemAtPosition(position).toString();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                        }
                                    });
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
                                            serviceName.add(temp2);
                                            serviceNameId.add(temp);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    //setting up the spinner
                                    servicenamespinner = (Spinner) findViewById(R.id.serviceNamespinneru);
                                    serviceNameAdapter = new ArrayAdapter<String>(workupdateentry.this, android.R.layout.simple_spinner_item, serviceName);
                                    serviceNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    servicenamespinner.setAdapter(serviceNameAdapter);
                                    serviceNameAdapter.notifyDataSetChanged();
                                    servicenamespinner.setSelection(serviceNameAdapter.getPosition(serviceName.get(serviceNameId.indexOf(jobid))));
                                    servicenamespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            Log.e("service selected", (String) parent.getItemAtPosition(position));
                                            serviceselected = parent.getItemAtPosition(position).toString();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                        }
                                    });
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
                            Remarks.setText(sremarks);
                        } else {

                        }
                        if (jsonObject.isNull("START_TIME") == false) {
                            sstarttime = (String) jsonObject.get("START_TIME");
                            startTV.setText(sstarttime);

                        } else {
                            startTV.setText("Could not find Start Time");
                        }
                        if (jsonObject.isNull("MOT") == false) {
                            Log.e("MOT", String.valueOf(jsonObject.get("MOT")));
                            mot = (String) jsonObject.get("MOT");
                            //setting up the medium of transport
                            String[] mots = new String[]{"Train", "Rickshaw", "CNG", "Taxicab", "Bus"};
                            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(workupdateentry.this, android.R.layout.simple_spinner_item, mots);
                            mediumoftransportspinner.setAdapter(adapter3);
                            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            int previoslySelectedMot = adapter3.getPosition(mot);
                            Log.e("mot in spinner", "Medium Of Transport " + previoslySelectedMot);
                            mediumoftransportspinner.setSelection(previoslySelectedMot);
                            mediumoftransportspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    Log.e("selectedmot", (String) parent.getItemAtPosition(position));
                                    selectedmot = parent.getItemAtPosition(position).toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
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
                                            temp2 = object.getString("WS_CODE");
                                            workstationnamelistforspinner.add(temp);
                                            workstationidlistforspinner.add(temp2);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    //setting up the spinner for workstations
                                    workstationnamespinner = (Spinner) findViewById(R.id.wsu);
                                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(workupdateentry.this, android.R.layout.simple_spinner_item, workstationnamelistforspinner);
                                    workstationnamespinner.setAdapter(adapter3);
                                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    workstationnamespinner.setSelection(adapter3.getPosition(workstationnamelistforspinner.get(workstationidlistforspinner.indexOf(wsCode))));
                                    workstationnamespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            selectedworkspinner = parent.getItemAtPosition(position).toString();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                        }
                                    });
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Log.e("ws error", volleyError.toString());
                                }
                            });
                            requestQueue.add(jsonArrayRequestworkstation);
                        } else {
                            wsCode = "Chi269";
                        }
                        if (jsonObject.isNull("END_TIME") == false) {

                            sendtime = (String) jsonObject.get("END_TIME");
                            endTV.setText(sendtime);
                            Log.e("END_TIME", String.valueOf(jsonObject.get("END_TIME")));
                        } else {
                            endTV.setText("Could not find end time");
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

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(workupdateentry.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        starth = hourOfDay;
                        startm = minute;
                        sstarttime = hourOfDay + ":" + minute;
                        startTV.setText(sstarttime);
                    }
                }, hour, minute, false);
                timePickerDialog.setTitle("Select Start Time");
                timePickerDialog.show();
            }
        });

        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(workupdateentry.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endh = hourOfDay;
                        endm = minute;
                        sendtime = hourOfDay + ":" + minute;
                        endTV.setText(sendtime);
                        startmil = (TimeUnit.HOURS.toMillis(endh) + TimeUnit.MINUTES.toMillis(endm)) - (TimeUnit.HOURS.toMillis(starth) + TimeUnit.MINUTES.toMillis(startm));
                        Toast.makeText(workupdateentry.this, "Time Duration " + TimeUnit.MILLISECONDS.toMinutes(startmil) / 60 + ":" + TimeUnit.MILLISECONDS.toMinutes(startmil) % 60, Toast.LENGTH_LONG).show();
                    }
                }, hour, minute, false);
                timePickerDialog.setTitle("Select End Time");
                timePickerDialog.show();


            }
        });
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Toast.makeText(workupdateentry.this, "Client Selected" + clientselected + " Service Type " + serviceselected
                        + " Start Time " + sstarttime + " End Time" + sendtime + " MOT " + selectedmot + "work station " + selectedworkspinner, Toast.LENGTH_LONG).show();*/
                Log.e("Send", "Client Selected" + clientselected + " Service Type " + serviceselected
                        + " Start Time " + sstarttime + " End Time" + sendtime + " MOT " + selectedmot + " work station " + selectedworkspinner + " Remarks " +
                        "" + sremarks + " Reference no" + refNoPassed);

                //sending volley post data to server for update

                sremarks = String.valueOf(Remarks.getText());
                StringRequest udpate = new StringRequest(Request.Method.POST, updateServer, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Reply ", "From server " + response);
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
                        params.put("clientName", clientselected);
                        params.put("servicename", serviceselected);
                        params.put("startTime", sstarttime);
                        params.put("tvendtimetext", sendtime);
                        params.put("mot", selectedmot);
                        params.put("wsname", selectedworkspinner);
                        params.put("remarks", sremarks);
                        params.put("referenceNo", refNoPassed);
                        return params;
                    }
                };
                requestQueue.add(udpate);


                Intent i = new Intent(workupdateentry.this, workviewbydatebetween.class);
                i.putExtra("startDate", startDatePassed);
                i.putExtra("endDate", endDatePassed);
                i.putExtra("username", usernamepassed);
                startActivity(i);
            }

        });

    } // end of on create

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            Intent i = new Intent(workupdateentry.this, Client_Setup.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(workupdateentry.this, dailyworkprogram.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(workupdateentry.this, clientView.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(workupdateentry.this, clientViewByDate.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
