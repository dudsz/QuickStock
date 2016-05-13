package erlach.quickstock;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends Activity {

    private ProgressDialog pDialog;
    EditText searchField;
    Button searchBtn, clearBtn;
    private static final String URL_STOCK = "http://finance.yahoo.com/webservice/v1/symbols/STAR-A.ST/quote?format=json&view=detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchField = (EditText) findViewById(R.id.field_search);
        searchBtn = (Button) findViewById(R.id.btn_search);
        clearBtn = (Button) findViewById(R.id.btn_clear);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setIndeterminate(false);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String stock = searchField.getText().toString().trim();

                if (!stock.isEmpty()) {
                    findStock(stock);
                } else {
                    Toast.makeText(SearchActivity.this, "Enter stock to find", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void findStock(final String stock) {

        pDialog.setMessage("Retrieving stock info...");
        pDialog.show();

        // prepare the Request
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, URL_STOCK, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        Log.d("Response: ", response.toString());

                        try {
                            JSONObject list = response.getJSONObject("list");
                            JSONArray resources = list.getJSONArray("resources");
                            JSONObject first = resources.getJSONObject(0);
                            JSONObject resource = first.getJSONObject("resource");
                            JSONObject fields = resource.getJSONObject("fields");


                            String stockCode = fields.getString("symbol");
                            String stockName = fields.getString("issuer_name");
                            String stockPrice = fields.getString("price");
                            String stockYH = fields.getString("year_high");
                            String stockYl = fields.getString("year_low");
                            String stockDH = fields.getString("day_high");
                            String stockDL = fields.getString("day_low");
                            Log.d("Result: ", stockName);

                            Intent userScreen = new Intent(SearchActivity.this, ShowStockActivity.class);
                            userScreen.putExtra("sc", stockCode);
                            userScreen.putExtra("sn", stockName);
                            userScreen.putExtra("sp", stockPrice);
                            userScreen.putExtra("syh", stockYH);
                            userScreen.putExtra("syl", stockYl);
                            userScreen.putExtra("sdh", stockDH);
                            userScreen.putExtra("sdl", stockDL);
                            startActivity(userScreen);
                            finish();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(SearchActivity.this, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("Error.Response: ", error.toString());
                    }
                }
        );
        AppRequestController.getInstance().addToRequestQueue(jsonRequest);
    }
}
