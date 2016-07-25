package ayon.rahman.shafiqur.bptl3ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class clientViewByDate extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button startDate, endDate, viewDate;
    String startDateString, endDateString, usernamepassed;
    Calendar c;
    TextView endTV, startTV;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private static String formatDate(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        return sdf.format(date);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_view_by_date);
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
        startDate = (Button) findViewById(R.id.bydate);
        endDate = (Button) findViewById(R.id.viewAll);
        viewDate = (Button) findViewById(R.id.viewDates);
        endTV = (TextView) findViewById(R.id.endtv);
        startTV = (TextView) findViewById(R.id.starttv);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(clientViewByDate.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String dateString = formatDate(year, monthOfYear, dayOfMonth);
                                startDateString = dateString;
                               /* Toast.makeText(clientViewByDate.this, "Inside End date", Toast.LENGTH_SHORT).show();*/
                                startTV.setText("Selected Start Date : " + dateString);
                              /*  Toast.makeText(clientViewByDate.this, dateString, Toast.LENGTH_SHORT).show();*/

                            }
                        }, mYear, mMonth, mDay);
                /*dpd.setButton(DialogInterface.BUTTON_NEUTRAL,"Select Start Date", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(clientViewByDate.this,"Mother Toast Clent",Toast.LENGTH_SHORT).show();
                    }
                });*/
                dpd.show();

            }

        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog enddpd = new DatePickerDialog(clientViewByDate.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String dateString = formatDate(year, monthOfYear, dayOfMonth);
                        endDateString = dateString;
                        endTV.setText("Selected End Date: " + dateString);
                      /*  Toast.makeText(clientViewByDate.this, dateString, Toast.LENGTH_SHORT).show();*/
                    }
                }, mYear, mMonth, mDay);

               /* enddpd.setButton(DialogInterface.BUTTON_NEUTRAL,"Select End Date", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(clientViewByDate.this,"Mother Toast Client end ",Toast.LENGTH_SHORT).show();
                    }
                });*/
                enddpd.show();
            }
        });


        viewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(clientViewByDate.this, workviewbydatebetween.class);
                i.putExtra("startDate", startDateString);
                i.putExtra("endDate", endDateString);
                i.putExtra("username", usernamepassed);
                startActivity(i);
              /*  Toast.makeText(clientViewByDate.this,startDateString+"\n"+endDateString,Toast.LENGTH_LONG).show();*/
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
            Intent i = new Intent(clientViewByDate.this, Client_Setup.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(clientViewByDate.this, dailyworkprogram.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(clientViewByDate.this, clientView.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(clientViewByDate.this, clientViewByDate.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);

        } else if (id == R.id.nav_dash) {
            Intent i = new Intent(clientViewByDate.this, dash.class);
            i.putExtra("username", usernamepassed);
            startActivity(i);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
