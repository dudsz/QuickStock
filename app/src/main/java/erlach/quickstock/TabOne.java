package erlach.quickstock;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabOne extends Fragment {

    private ProgressDialog pDialog;
    EditText searchField;
    Button searchBtn, clearBtn;
    private static final String URL_STOCK = "http://finance.yahoo.com/webservice/v1/symbols/STAR-A.ST/quote?format=json&view=detail";

    public TabOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_one, container, false);

        searchField = (EditText) view.findViewById(R.id.field_search);
        searchBtn = (Button) view.findViewById(R.id.btn_search);
        clearBtn = (Button) view.findViewById(R.id.btn_clear);

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setIndeterminate(false);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String stock = searchField.getText().toString().trim();

                if (!stock.isEmpty()) {
                    findStock(stock);
                } else {
                    Toast.makeText(getActivity(), "Enter stock to find", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
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

                            Intent userScreen = new Intent(getContext(), ShowStockActivity.class);
                            userScreen.putExtra("sc", stockCode);
                            userScreen.putExtra("sn", stockName);
                            userScreen.putExtra("sp", stockPrice);
                            userScreen.putExtra("syh", stockYH);
                            userScreen.putExtra("syl", stockYl);
                            userScreen.putExtra("sdh", stockDH);
                            userScreen.putExtra("sdl", stockDL);
                            startActivity(userScreen);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("Error.Response: ", error.toString());
                    }
                }
        );
        AppRequestController.getInstance().addToRequestQueue(jsonRequest);
    }
}
