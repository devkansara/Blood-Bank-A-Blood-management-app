package lalitjain.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomizedAdapter extends BaseAdapter {
    Context c;
    ArrayList<String> name;
    ArrayList<String> quantity;
    ArrayList<String> date;
    ArrayList<String> time_slot;
    ArrayList<String> blood_group;

    public CustomizedAdapter(Context c, ArrayList<String> name, ArrayList<String> quantity, ArrayList<String> date, ArrayList<String> time_slot, ArrayList<String> blood_group) {
        this.c = c;
        this.name = name;
        this.quantity = quantity;
        this.date = date;
        this.time_slot = time_slot;
        this.blood_group = blood_group;
    }


    @Override
    public int getCount() {
        return name.size();
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
        view = inflater.inflate(R.layout.listview_scam, viewGroup, false);

        TextView tvName, tvBloodGroup, tvBloodQuantity, tvDate, tvTimeSlot;
        tvName = view.findViewById(R.id.tvName);
        tvBloodGroup = view.findViewById(R.id.tvBloodGroup);
        tvBloodQuantity = view.findViewById(R.id.tvBloodQuantity);
        tvDate = view.findViewById(R.id.tvDate);
        tvTimeSlot = view.findViewById(R.id.tvTimeSlot);

        tvName.setText(name.get(i));
        tvBloodGroup.setText(blood_group.get(i));
        tvBloodQuantity.setText(quantity.get(i));
        tvDate.setText(date.get(i));
        tvTimeSlot.setText(time_slot.get(i));

        return view;
    }
}
