package lalitjain.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<String> h;
    ArrayList<String> q;

    public CustomAdapter(Context c, ArrayList<String> h, ArrayList<String> q) {
        this.c = c;
        this.h = h;
        this.q = q;
    }

    @Override
    public int getCount() {
        return h.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.listview_item, viewGroup, false);

        TextView tvHospitalName, tvQuantity;
        tvHospitalName = view.findViewById(R.id.tvHospitalName);
        tvQuantity = view.findViewById(R.id.tvQuantity);

        tvHospitalName.setText(h.get(i));
        tvQuantity.setText(String.valueOf(q.get(i)));


        return view;
    }
}
