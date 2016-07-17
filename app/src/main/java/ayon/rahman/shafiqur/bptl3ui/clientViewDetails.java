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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class clientViewDetails extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String clientName = "empty", serverAddress = "http://103.229.84.171/clientDetail.php";
    String website, contactPerson, phone, address, email, officephone, clientType, usernamepassed, industryname,
            decisionMaker, decisionMakerNumber, middleMan, consultant, finance, possibleRequirement, remarks;
    TextView sclientName, swebsite, scontactPerson, sphone, saddress, semail, sofficephone, sclientType, sindustryname,
            sdecisionMaker, sdecisionMakerNumber, smiddleMan, sconsultant, sfinance, spossibleRequirement, sremarks;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_view_details);
        usernamepassed = getIntent().getExtras().getString("username");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        clientName = getIntent().getExtras().getString("item");
        requestQueue = Volley.newRequestQueue(clientViewDetails.this);
        sclientName = (TextView) findViewById(R.id.textView2);
        swebsite = (TextView) findViewById(R.id.textView3);
        scontactPerson = (TextView) findViewById(R.id.textView4);
        sphone = (TextView) findViewById(R.id.textView5);
        saddress = (TextView) findViewById(R.id.textView6);
        semail = (TextView) findViewById(R.id.textView7);
        sofficephone = (TextView) findViewById(R.id.textView8);
        sclientType = (TextView) findViewById(R.id.textView9);
        sindustryname = (TextView) findViewById(R.id.textView10);
        sdecisionMaker = (TextView) findViewById(R.id.textView11);
        sdecisionMakerNumber = (TextView) findViewById(R.id.textView12);
        smiddleMan = (TextView) findViewById(R.id.textView13);
        sconsultant = (TextView) findViewById(R.id.textView14);
        sfinance = (TextView) findViewById(R.id.textView15);
        spossibleRequirement = (TextView) findViewById(R.id.textView16);
        sremarks = (TextView) findViewById(R.id.textView17);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverAddress, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray = null;
                try {
//                    jsonArray = new JSONArray(response);
                    JSONObject jsonObject = new JSONObject(response);
//                    category = object.getString("status");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//
//                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String clientNameget;
                    sclientName.setText("Client Name : " + (String) jsonObject.get("CLIENT_NAME"));

                    if (jsonObject.isNull("WEB_ADDRESS") == false) {
                        swebsite.setText("Web Address : " + (String) jsonObject.get("WEB_ADDRESS"));
                    } else {
                        swebsite.setText("No Data for website");
                    }


                    if (jsonObject.isNull("INDUSTRY_NAME") == false) {
                        sindustryname.setText("Industry Name : " + (String) jsonObject.get("INDUSTRY_NAME"));
                    } else {
                        sindustryname.setText("No Data  for industry");
                    }

                    //client type
                    if (jsonObject.isNull("CLIENT_TYPE") == false) {
                        String temp = (String) jsonObject.get("CLIENT_TYPE");
                        if (temp.equals("P")) {
                            sclientType.setText("Client Type : Existing");
                        } else if (temp.equals("E")) {
                            sclientType.setText("Client Type : Preceding");
                        }

                    } else {
                        sclientType.setText("No Data  for Client Type");
                    }


                    if (jsonObject.isNull("CONTACT_PERSON") == false) {
                        scontactPerson.setText("Contact Person : " + (String) jsonObject.get("CONTACT_PERSON"));
                    } else {
                        scontactPerson.setText("No Data  for Contact Person");
                    }


                    if (jsonObject.isNull("ADDRESS_LINE_1") == false) {
                        saddress.setText("Address Line : " + (String) jsonObject.get("ADDRESS_LINE_1"));
                    } else {
                        saddress.setText("No Data  for Address");
                    }


                    if (jsonObject.isNull("CONT_NUMBER") == false) {
                        sphone.setText("Contact Number : " + (String) jsonObject.get("CONT_NUMBER"));
                    } else {
                        sphone.setText("No Data  for Contact Number");
                    }


                    if (jsonObject.isNull("OFFICE_PHONE") == false) {
                        sofficephone.setText("Office Phone : " + (String) jsonObject.get("OFFICE_PHONE"));
                    } else {
                        sofficephone.setText("No Data  for Office Phone");
                    }


                    if (jsonObject.isNull("EMAIL") == false) {
                        semail.setText("Email : " + (String) jsonObject.get("EMAIL"));
                    } else {
                        semail.setText("No Data  for Email");
                    }


                    if (jsonObject.isNull("DECISION_MAKER") == false) {

                        sdecisionMaker.setText("Decision Maker : " + (String) jsonObject.get("DECISION_MAKER"));
                    } else {
                        sdecisionMaker.setText("No Data  for Decision Maker");
                    }


                    if (jsonObject.isNull("DEC_NUMBER") == false) {
                        sdecisionMakerNumber.setText("Decision Maker Number : " + (String) jsonObject.get("DEC_NUMBER"));
                    } else {
                        sdecisionMakerNumber.setText("No Data  for Decision Maker phone number");
                    }


                    if (jsonObject.isNull("MIDDLE_MAN") == false) {
                        smiddleMan.setText("Middle Man : " + (String) jsonObject.get("MIDDLE_MAN"));
                    } else {
                        smiddleMan.setText("No Data  for Middle Man");
                    }


                    if (jsonObject.isNull("CONSULTANT") == false) {
                        sconsultant.setText("Consultant : " + (String) jsonObject.get("CONSULTANT"));
                    } else {
                        sconsultant.setText("No Data  for Consultant");
                    }


                    if (jsonObject.isNull("FINANCE_FROM") == false) {
                        sfinance.setText("Finance : " + (String) jsonObject.get("FINANCE_FROM"));
                    } else {
                        sfinance.setText("No Data  for Finance");
                    }


                    if (jsonObject.isNull("REMARKS") == false) {
                        sremarks.setText("Remarks : " + (String) jsonObject.get("REMARKS"));
                    } else {
                        sremarks.setText("No Data  for Remarks");
                    }


                    if (jsonObject.isNull("POSSIBLE_REQUIRMENT") == false) {
                        spossibleRequirement.setText("Possible Requirements : " + (String) jsonObject.get("POSSIBLE_REQUIRMENT"));
                    } else {
                        spossibleRequirement.setText("No Data  for Possible Requirements");
                    }
                    Log.e("check", "this is what we get " + (String) jsonObject.get("CLIENT_NAME"));


//                    }
                } catch (JSONException e) {
                    Log.e("error array conversion", e.toString());
                    e.printStackTrace();
                }

                Log.e("Response", response.toString());
//                Log.e("CONTACT_PERSON", "this is"+contactPerson);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
            }
        }


        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("customername", clientName);
                return params;
            }
        };
//        sclientName.setText(clientNameget);
//        swebsite.setText(website);
//        scontactPerson.setText(contactPerson);
//        sphone.setText(phone);
//        saddress.setText(address);
//        semail.setText(email);
//        sofficephone.setText(officephone);
//        sclientType.setText(clientType);
//        sindustryname.setText(industryname);
//        sdecisionMaker.setText(decisionMaker);
//        sdecisionMakerNumber.setText(decisionMakerNumber);
//        smiddleMan.setText(middleMan);
//        sconsultant.setText(consultant);
//        sfinance.setText(finance);
//        spossibleRequirement.setText(possibleRequirement);
//        sremarks.setText(remarks);


        requestQueue.add(stringRequest);

    }

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
            Intent i = new Intent(clientViewDetails.this, Client_Setup.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(clientViewDetails.this, dailyworkprogram.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(clientViewDetails.this, clientView.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(clientViewDetails.this, clientViewByDate.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
