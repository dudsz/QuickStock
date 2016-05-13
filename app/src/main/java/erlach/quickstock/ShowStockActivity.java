package erlach.quickstock;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowStockActivity extends Activity {

    String sC, sN, sP, sYH, sYL, sDH, sDL;
    ListView lw;
    private ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> stockArrayList;

    // JSON names
    private static final String SID = "sID";
    private static final String SN = "sName";
    private static final String SP = "sPrice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stock);

        // Get data from choice
        sC = getIntent().getStringExtra("sc");
        sN = getIntent().getStringExtra("sn");
        sP = getIntent().getStringExtra("sp");
        lw = (ListView) findViewById(R.id.list);
        stockArrayList = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(SID, sC);
        map.put(SN, sN);
        map.put(SP, sP);
        stockArrayList.add(map);

        ListAdapter adapter = new SimpleAdapter(ShowStockActivity.this, stockArrayList, R.layout.stocklist,
                new String[]{ SID, SN, SP},
                new int[]{R.id.sid, R.id.stockName, R.id.sPrice});
        lw.setAdapter(adapter);
    }
}
