package com.example.jiwonchoi.currency;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Callback<CurrencyExchange> {

    InputMethodManager imm;
    String API_BASE_URL = "http://api.fixer.io/";
    private EditText money_input, money_output;
    private TextView updated_date;
    private List<Currency> currencyList;
    private DecimalFormat df = new DecimalFormat("###,###.##");
    private String result = "";
    private double currencyRate;
    private boolean dataLoad = false;
//    protected NavigationView navigationView;

    public void linearOnClick(View v) {
        imm.hideSoftInputFromWindow(money_input.getWindowToken(), 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadCurrencyExchangeData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Currency");

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.getMenu().getItem(0).setChecked(true);
//        onNavigationItemSelected(navigationView.getMenu().getItem(0));

        ArrayList<CountryData> countryData = new ArrayList<CountryData>();
        countryData.add(new CountryData("AUD", R.drawable.aud));
        countryData.add(new CountryData("BGN", R.drawable.bgn));
        countryData.add(new CountryData("BRL", R.drawable.brl));
        countryData.add(new CountryData("CAD", R.drawable.cad));
        countryData.add(new CountryData("CHF", R.drawable.chf));

        countryData.add(new CountryData("CNY", R.drawable.cny));
        countryData.add(new CountryData("CZK", R.drawable.czk));
        countryData.add(new CountryData("DKK", R.drawable.dkk));
        countryData.add(new CountryData("EUR", R.drawable.eur));
        countryData.add(new CountryData("GBP", R.drawable.gbp));

        countryData.add(new CountryData("HKD", R.drawable.hkd));
        countryData.add(new CountryData("HRK", R.drawable.hrk));
        countryData.add(new CountryData("HUF", R.drawable.huf));
        countryData.add(new CountryData("IDR", R.drawable.idr));
        countryData.add(new CountryData("ILS", R.drawable.ils));

        countryData.add(new CountryData("INR", R.drawable.inr));
        countryData.add(new CountryData("ISK", R.drawable.isk));
        countryData.add(new CountryData("JPY", R.drawable.jpy));
//        countryData.add(new CountryData("KRW", R.drawable.krw));
        countryData.add(new CountryData("MXN", R.drawable.mxn));
        countryData.add(new CountryData("MYR", R.drawable.myr));

        countryData.add(new CountryData("NOK", R.drawable.nok));
        countryData.add(new CountryData("NZD", R.drawable.nzd));
        countryData.add(new CountryData("PHP", R.drawable.php));
        countryData.add(new CountryData("PLN", R.drawable.pln));
        countryData.add(new CountryData("RON", R.drawable.ron));

        countryData.add(new CountryData("RUB", R.drawable.rub));
        countryData.add(new CountryData("SEK", R.drawable.sek));
        countryData.add(new CountryData("SGD", R.drawable.sgd));
        countryData.add(new CountryData("THB", R.drawable.thb));
        countryData.add(new CountryData("TRY", R.drawable.trry));

        countryData.add(new CountryData("USD", R.drawable.usd));
        countryData.add(new CountryData("ZAR", R.drawable.zar));

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_layout, R.id.country_code, countryData);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(dataLoad) {
                    currencyRate = currencyList.get(i).getRate();
                }
                imm.hideSoftInputFromWindow(money_input.getWindowToken(), 0);
                money_output.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        money_input = findViewById(R.id.money_1);
        money_output = findViewById(R.id.money_2);
        money_output.setKeyListener(null);
        updated_date = findViewById(R.id.date);
        money_input.addTextChangedListener(textWatcher);

        // Begin Button Click Event
        findViewById(R.id.button_s9).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(money_input.getWindowToken(), 0);
                TextView moneyInput = findViewById(R.id.money_1);
                moneyInput.setText("957000");
//                Button button = findViewById(R.id.button_s9);
//                button.setTypeface(null, Typeface.BOLD);
//                button.setTextColor(Color.parseColor("#FF800C1D"));
                money_input.setSelection(result.length());
                money_output.setText("");
            }
        });

        findViewById(R.id.button_s9_plus_64).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(money_input.getWindowToken(), 0);
                TextView moneyInput = findViewById(R.id.money_1);
                moneyInput.setText("1056000");
//                Button button = findViewById(R.id.button_s9_plus_64);
//                button.setTypeface(null, Typeface.BOLD);
//                button.setTextColor(Color.parseColor("#FF800C1D"));
                money_input.setSelection(result.length());
                money_output.setText("");
            }
        });

        findViewById(R.id.button_s9_plus_128).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(money_input.getWindowToken(), 0);
                TextView moneyInput = findViewById(R.id.money_1);
                moneyInput.setText("1155000");
                money_input.setSelection(result.length());
                money_output.setText("");
            }
        });

        findViewById(R.id.button_gear_s3).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(money_input.getWindowToken(), 0);
                TextView moneyInput = findViewById(R.id.money_1);
                moneyInput.setText("399000");
                money_input.setSelection(result.length());
                money_output.setText("");
            }
        });

        findViewById(R.id.button_gear_sport).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(money_input.getWindowToken(), 0);
                TextView moneyInput = findViewById(R.id.money_1);
                moneyInput.setText("299000");
                money_input.setSelection(result.length());
                money_output.setText("");
            }
        });

        findViewById(R.id.button_gear_fit2pro).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(money_input.getWindowToken(), 0);
                TextView moneyInput = findViewById(R.id.money_1);
                moneyInput.setText("220000");
                money_input.setSelection(result.length());
                money_output.setText("");
            }
        });

        findViewById(R.id.button_calculate).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(money_input.getWindowToken(), 0);
                double changeValue = Integer.parseInt(money_input.getText().toString().replaceAll(",", "")) * currencyRate;
                String formatted = df.format(changeValue);
                money_output.setText(formatted + ""); // 위에서 얻은 변경된값을 textView에 표시한다
            }
        });
        // End of Button Click Event

    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable edit) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(money_input.length() < 1) {
                money_input.setHint("Enter Amount in KRW");
                money_output.setText("");
            } else {
                if (!s.toString().equals(result)) {     // StackOverflow를 막기위해,
                    result = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    money_input.setText(result);    // 결과 텍스트 셋팅.
                    money_input.setSelection(result.length());     // 커서를 제일 끝으로 보냄.
                }
            }
        }
    };

    private void loadCurrencyExchangeData() {
        Retrofit builder = new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create()).build();

        CurrencyExchangeService service = builder.create(CurrencyExchangeService.class);
        Call<CurrencyExchange> call = service.loadCurrencyExchange("KRW");
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<CurrencyExchange> call, Response<CurrencyExchange> response) {
        //Toast.makeText(this, response.body().getBase(), Toast.LENGTH_LONG).show();
        CurrencyExchange currencyExchange = response.body();
        currencyList = currencyExchange.getCurrencyList();
        updated_date.setText("Updated: " + currencyExchange.getDate());
        currencyRate = currencyList.get(0).getRate();
        dataLoad = true;
    }

    @Override
    public void onFailure(Call<CurrencyExchange> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        ActionBar actionBar = getSupportActionBar();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_currency) {
            // Handle the camera action
            actionBar.setTitle("Currency");
        } else if (id == R.id.nav_series_9) {

        } else if (id == R.id.nav_series_8) {

        } else if (id == R.id.nav_series_A) {

        } else if (id == R.id.nav_series_J) {

        } else if (id == R.id.nav_smart_watch) {

        } else if (id == R.id.nav_gear) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
