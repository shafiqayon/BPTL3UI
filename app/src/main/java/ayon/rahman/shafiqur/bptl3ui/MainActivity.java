package ayon.rahman.shafiqur.bptl3ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText usernameet, passwordet;
    Button loginbtn;
    public String category = null;
    String servername = "http://103.229.84.171/s.php", name = "name", password = "password", statusReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        usernameet = (EditText) findViewById(R.id.user_Name);
        passwordet = (EditText) findViewById(R.id.password);
        usernameet.setHintTextColor(getResources().getColor(R.color.grey));
        passwordet.setHintTextColor(getResources().getColor(R.color.grey));
        loginbtn = (Button) findViewById(R.id.loginbutton);
/*        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String TempUser = String.valueOf(usernameet.getText());
                final String tempPassword = String.valueOf(passwordet.getText());
                if (TempUser.equals("S-002") && tempPassword.equals("52146")) {
                    Toast.makeText(MainActivity.this, "Right Credentials", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MainActivity.this, dash.class);
                    i.putExtra("username", TempUser);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Wrong Credentials", Toast.LENGTH_LONG).show();
                }
            }
        });*/
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String TempUser = String.valueOf(usernameet.getText());
                final String tempPassword = String.valueOf(passwordet.getText());
                //Toast.makeText(MainActivity.this, "Sending " + TempUser + " and " + tempPassword, Toast.LENGTH_LONG).show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, servername, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            category = object.getString("status");
                            if (category.equals("1")) {
                                /*Toast.makeText(MainActivity.this, "Right Credentials", Toast.LENGTH_LONG).show();*/
                                Intent i = new Intent(MainActivity.this, dash.class);
                                i.putExtra("username", TempUser);
                                startActivity(i);
                            } else if (category.equals("0")) {
                                Toast.makeText(MainActivity.this, "Wrong Credentials", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Log.e("Server Reply", e.toString());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put(name, TempUser);
                        params.put(password, tempPassword);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        /*Toast.makeText(MainActivity.this, "Inside OnCreate", Toast.LENGTH_LONG).show();*/
    }


}
