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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class clientUpdate extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    public String clientTypeJson = null, addessTypeJson, industryname;
    public String category = null, usernamepassed, selectedInSpinner = "nothing selected", industrySelected = null, addressTypeSelected = null, clientTypeSelected = null, selectedInSpinnerAddresstype = null, selectedInSpinnerClientType = null;
    String serverAddressfordata = "http://103.229.84.171/clientUpdateView.php", serverAddressforupdate = "http://103.229.84.171/clientUpdate.php";
    String clientName = null;
    EditText sclientName, swebsite, scontactPerson, sphone, saddress, semail, sofficephone, sclientType, sindustryname,
            sdecisionMaker, sdecisionMakerNumber, smiddleMan, sconsultant, sfinance, spossibleRequirement, sremarks;
    Button updateButton;
    RequestQueue requestQueue;
    Spinner industrylistspinner, addresstype, ClientTypeSpinner;
    ArrayAdapter<String> industryAdapter;
    String[] clientType = new String[]{"Prospecting", "Existing"};
    String temp, temp2, industryServerforspinner = "http://103.229.84.171/industryspinner.php";
    private List<String> industryListforSpinner = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_update);
        clientName = getIntent().getExtras().getString("item");
        usernamepassed = getIntent().getExtras().getString("usernamepassed");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


     /*   Toast.makeText(clientUpdate.this, clientName, Toast.LENGTH_LONG).show();*/
        requestQueue = Volley.newRequestQueue(clientUpdate.this);

        updateButton = (Button) findViewById(R.id.update);


        industrylistspinner = (Spinner) findViewById(R.id.industry);
        addresstype = (Spinner) findViewById(R.id.addressType);
        ClientTypeSpinner = (Spinner) findViewById(R.id.clientType);


        sclientName = (EditText) findViewById(R.id.EditText2);
        swebsite = (EditText) findViewById(R.id.EditText3);
        scontactPerson = (EditText) findViewById(R.id.EditText4);
        sphone = (EditText) findViewById(R.id.EditText5);
        saddress = (EditText) findViewById(R.id.EditText6);
        semail = (EditText) findViewById(R.id.EditText7);
        sofficephone = (EditText) findViewById(R.id.EditText8);
        sclientType = (EditText) findViewById(R.id.EditText9);
        sindustryname = (EditText) findViewById(R.id.EditText10);
        sdecisionMaker = (EditText) findViewById(R.id.EditText11);
        sdecisionMakerNumber = (EditText) findViewById(R.id.EditText12);
        smiddleMan = (EditText) findViewById(R.id.EditText13);
        sconsultant = (EditText) findViewById(R.id.EditText14);
        sfinance = (EditText) findViewById(R.id.EditText15);
        spossibleRequirement = (EditText) findViewById(R.id.EditText16);
        sremarks = (EditText) findViewById(R.id.EditText17);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverAddressfordata, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray = null;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("ClientUpdate Json", String.valueOf(jsonObject));
                    String clientNameget;
                    sclientName.setText((String) jsonObject.get("CLIENT_NAME"));

                    if (jsonObject.isNull("WEB_ADDRESS") == false) {
                        swebsite.setText((String) jsonObject.get("WEB_ADDRESS"));
                    } else {
                        swebsite.setText("No Data for website");
                    }


                    if (jsonObject.isNull("INDUSTRY_NAME") == false) {
                        sindustryname.setText((String) jsonObject.get("INDUSTRY_NAME"));
                        industryname = (String) jsonObject.get("INDUSTRY_NAME");
                        //getting industry for spinner industry
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(industryServerforspinner, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray jsonArray) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        temp = null;
                                        JSONObject industry = (JSONObject) jsonArray.get(i);
                                        temp = (String) industry.get("INDUSTRY_NAME");

                                        industryListforSpinner.add(temp);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                industryAdapter = new ArrayAdapter<String>(clientUpdate.this, android.R.layout.simple_spinner_item, industryListforSpinner);
                                industryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                industrylistspinner.setAdapter(industryAdapter);

                                int industrySpinnerPosition = industryAdapter.getPosition(industryname);
                                Log.e("Industry from json", industryname);
                                Log.e("Industry Selected", String.valueOf(industrySpinnerPosition));
                                industrylistspinner.setSelection(industrySpinnerPosition);

                                industryAdapter.notifyDataSetChanged();


                                industrylistspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        // Log.e("item", (String) parent.getItemAtPosition(position));
                                        selectedInSpinner = parent.getItemAtPosition(position).toString();
                                         /* temp2 = String.valueOf(parent.getItemAtPosition(position));*/
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
                        sindustryname.setText("No Data  for industry");
                    }

                    //client type
                    if (jsonObject.isNull("CLIENT_TYPE") == false) {
                        String temp = (String) jsonObject.get("CLIENT_TYPE");
                        Log.e("json client", "converted json" + temp);
                        if (temp.equals("P")) {
                            Log.e("Check", "converted json" + temp);
                            clientTypeJson = null;
                            sclientType.setText("Preceding");
                            clientTypeJson = "Preceding";


                        } else if (temp.equals("E")) {
                            clientTypeJson = null;
                            sclientType.setText("Existing");
                            clientTypeJson = "Existing";
                        }

                        //setting up the client type spinner
                        ArrayAdapter<String> adapterforClientType = new ArrayAdapter<String>(clientUpdate.this, android.R.layout.simple_spinner_item, clientType);
                        adapterforClientType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        ClientTypeSpinner.setAdapter(adapterforClientType);
                        Log.e("Previously ", "Seelcted " + clientTypeJson);
                        int previouslySelectedClientType = adapterforClientType.getPosition(clientTypeJson);
                        ClientTypeSpinner.setSelection(previouslySelectedClientType);
                        ClientTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Log.e("item", (String) parent.getItemAtPosition(position));
                                selectedInSpinnerClientType = parent.getItemAtPosition(position).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        //clinet Type ends here





                    } else {
                        sclientType.setText("No Data  for Client Type");
                    }


                    if (jsonObject.isNull("CONTACT_PERSON") == false) {
                        scontactPerson.setText((String) jsonObject.get("CONTACT_PERSON"));
                    } else {
                        scontactPerson.setText("No Data  for Contact Person");
                    }


                    if (jsonObject.isNull("ADDRESS_LINE_1") == false) {
                        saddress.setText((String) jsonObject.get("ADDRESS_LINE_1"));
                    } else {
                        saddress.setText("No Data  for Address");
                    }


                    if (jsonObject.isNull("CONT_NUMBER") == false) {
                        sphone.setText((String) jsonObject.get("CONT_NUMBER"));
                    } else {
                        sphone.setText("0000000");
                    }


                    if (jsonObject.isNull("OFFICE_PHONE") == false) {
                        sofficephone.setText((String) jsonObject.get("OFFICE_PHONE"));
                    } else {
                        sofficephone.setText("0000000");
                    }


                    if (jsonObject.isNull("EMAIL") == false) {
                        semail.setText((String) jsonObject.get("EMAIL"));
                    } else {
                        semail.setText("No Data  for Email");
                    }


                    if (jsonObject.isNull("DECISION_MAKER") == false) {

                        sdecisionMaker.setText((String) jsonObject.get("DECISION_MAKER"));
                    } else {
                        sdecisionMaker.setText("No Data  for Decision Maker");
                    }


                    if (jsonObject.isNull("DEC_NUMBER") == false) {
                        sdecisionMakerNumber.setText((String) jsonObject.get("DEC_NUMBER"));
                    } else {
                        sdecisionMakerNumber.setText("0000000");
                    }


                    if (jsonObject.isNull("MIDDLE_MAN") == false) {
                        smiddleMan.setText((String) jsonObject.get("MIDDLE_MAN"));
                    } else {
                        smiddleMan.setText("No Data  for Middle Man");
                    }


                    if (jsonObject.isNull("CONSULTANT") == false) {
                        sconsultant.setText((String) jsonObject.get("CONSULTANT"));
                    } else {
                        sconsultant.setText("No Data  for Consultant");
                    }


                    if (jsonObject.isNull("FINANCE_FROM") == false) {
                        sfinance.setText((String) jsonObject.get("FINANCE_FROM"));
                    } else {
                        sfinance.setText("No Data  for Finance");
                    }


                    if (jsonObject.isNull("REMARKS") == false) {
                        sremarks.setText((String) jsonObject.get("REMARKS"));
                    } else {
                        sremarks.setText("No Data  for Remarks");
                    }


                    if (jsonObject.isNull("POSSIBLE_REQUIRMENT") == false) {
                        spossibleRequirement.setText((String) jsonObject.get("POSSIBLE_REQUIRMENT"));
                    } else {
                        spossibleRequirement.setText("No Data  for Possible Requirements");
                    }


                    if (jsonObject.isNull("ADDRESS_TYPE") == false) {
                        addessTypeJson = (String) jsonObject.get("ADDRESS_TYPE");
                        Log.e("RESTApi", (String) jsonObject.get("ADDRESS_TYPE"));
                        Log.e("RESTApi12", "address previous " + addessTypeJson);
                        //addresstype spinner starts here
                        String[] addressTypeItems = new String[]{"Local Office ", "Head Office", "Factory", "Regional", "Zonal Office", "Other Address"};
                        //setting up the address type spinner
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(clientUpdate.this,
                                android.R.layout.simple_spinner_item, addressTypeItems);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        addresstype.setAdapter(adapter2);
                        Log.e("RESTApi3", "address previous " + addessTypeJson);
                        int previouslySelectedAddressType = adapter2.getPosition(addessTypeJson);


                        addresstype.setSelection(previouslySelectedAddressType);

                        addresstype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Log.e("item", (String) parent.getItemAtPosition(position));
                                selectedInSpinnerAddresstype = parent.getItemAtPosition(position).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        //addresstype spinner ends here

                    } else {

                    }
                    //Log.e("check", "this is what we get " + (String) jsonObject.get("CLIENT_NAME"));


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
                params.put("clientName", clientName);
                return params;
            }
        };


        requestQueue.add(stringRequest);

        Log.e("RESTApi2", "address previous " + addessTypeJson);


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest sendUpdateData = new StringRequest(Request.Method.POST, serverAddressforupdate, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("send Json", String.valueOf(jsonObject));

                            Log.e("Return Json", String.valueOf(jsonObject));
                            Log.e("Return Json", response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.getStackTraceString(e);
                        }
                        ;

                      /*  Toast.makeText(clientUpdate.this, "webiste: - > " + swebsite.getText() + sclientName.getText() + sclientType.getText() + sconsultant.getText() + sphone.getText() + spossibleRequirement.getText() + sremarks.getText(), Toast.LENGTH_SHORT).show();*/

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("clientNameU", String.valueOf(sclientName.getText()));
                        params.put("clientName", clientName);
                        params.put("website", String.valueOf(swebsite.getText()));
                        params.put("companyName", "Dim");
                        params.put("contactPerson", String.valueOf(scontactPerson.getText()));
                        params.put("phone", String.valueOf(sphone.getText()));
                        params.put("address", String.valueOf(saddress.getText()));
                        params.put("email", String.valueOf(semail.getText()));
                        params.put("officephone", String.valueOf(sofficephone.getText()));
                        params.put("decisionMaker", String.valueOf(sdecisionMaker.getText()));
                        params.put("decisionMakerNumber", String.valueOf(sdecisionMakerNumber.getText()));
                        params.put("middleMan", String.valueOf(smiddleMan.getText()));
                        params.put("consultant", String.valueOf(sconsultant.getText()));
                        params.put("finance", String.valueOf(sfinance.getText()));
                        params.put("possibleRequirement", String.valueOf(spossibleRequirement.getText()));
                        params.put("remarks", String.valueOf(sremarks.getText()));
                        params.put("industry", selectedInSpinner);
                        params.put("addressType", selectedInSpinnerAddresstype);
                        params.put("clientType", selectedInSpinnerClientType);
                        params.put("userlogin", usernamepassed);
                        return params;
                    }
                };
                requestQueue.add(sendUpdateData);

//added for - not entering same thing again
                Intent i = new Intent(clientUpdate.this, clientView.class);
                i.putExtra("username", usernamepassed);
                startActivity(i);
            }
        });
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
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
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
            Intent i = new Intent(clientUpdate.this, Client_Setup.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(clientUpdate.this, dailyworkprogram.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(clientUpdate.this, clientView.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(clientUpdate.this, clientViewByDate.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
