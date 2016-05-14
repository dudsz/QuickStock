package erlach.quickstock;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabTwo extends Fragment {

    String sC, sN, sP, sYH, sYL, sDH, sDL;
    ListView lw;
    private ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> stockArrayList;

    // JSON names
    private static final String SID = "sID";
    private static final String SN = "sName";
    private static final String SP = "sPrice";

    public TabTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_two, container, false);

        // Get data from choice
        sC = getActivity().getIntent().getStringExtra("sc");
        sN = getActivity().getIntent().getStringExtra("sn");
        sP = getActivity().getIntent().getStringExtra("sp");
        lw = (ListView) view.findViewById(R.id.list);
        stockArrayList = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(SID, sC);
        map.put(SN, sN);
        map.put(SP, sP);
        stockArrayList.add(map);

        ListAdapter adapter = new SimpleAdapter(getActivity(), stockArrayList, R.layout.stocklist,
                new String[]{ SID, SN, SP},
                new int[]{R.id.sid, R.id.stockName, R.id.sPrice});
        lw.setAdapter(adapter);

        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get stock
                String stockName = ((TextView) view.findViewById(R.id.stockName)).getText().toString();
                Intent infoScreen = new Intent(getContext(), ShowStockActivity.class);
                // Sending sn
                infoScreen.putExtra(SN, stockName);
                startActivity(infoScreen);
            }
        });

        return view;
    }

}
