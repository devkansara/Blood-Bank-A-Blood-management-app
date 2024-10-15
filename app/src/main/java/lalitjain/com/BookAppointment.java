package lalitjain.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class BookAppointment extends AppCompatActivity {

    TextView tvFilterBloodGroup;
    Spinner spnBloodGroup;
    Button btnSearchBloodGroup;
    ListView lvHospitalFound;
    FirebaseDatabase firebaseDatabase, fbdb;
    DatabaseReference databaseReference, dbrf;
    String quantity = "0.0";
    String qty = "0.0";
    //    String stored_qty = "0.0";
    ArrayList<String> hospital_found = new ArrayList<>();
    ArrayList<String> bg_qty_found = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        tvFilterBloodGroup = findViewById(R.id.tvFilterBloodGroup);
        spnBloodGroup = findViewById(R.id.spnBloodGroup);
        btnSearchBloodGroup = findViewById(R.id.btnSearchBloodGroup);
        lvHospitalFound = findViewById(R.id.lvHospitalFound);

        Intent a1 = getIntent();
        String reciever_name = a1.getStringExtra("name");
        String reciever_mail = a1.getStringExtra("email");

        //Step 1: ArrayList
        ArrayList<String> blood_group = new ArrayList<>();

        blood_group.add("A-");
        blood_group.add("A+");
        blood_group.add("B-");
        blood_group.add("B+");
        blood_group.add("AB-");
        blood_group.add("AB+");
        blood_group.add("O-");
        blood_group.add("O+");

        //Step 2: ArrayAdapter
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                blood_group);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Step 3: Set Adapter
        spnBloodGroup.setAdapter(arrayAdapter);

        btnSearchBloodGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSearchBloodGroup.setEnabled(false);
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Hospital");

                hospital_found.clear();
                bg_qty_found.clear();

                int p = spnBloodGroup.getSelectedItemPosition();
                String selected_bg = blood_group.get(p);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        for (DataSnapshot d : snapshot.getChildren())
                        {
                            Hospital hos = d.getValue(Hospital.class);

                            String table_name = hos.getUn() + "_BloodGroupDetails";
                            qty = checkIndividualHospital(hos.getName(), table_name, selected_bg);
                            btnSearchBloodGroup.setEnabled(true);
//                            if (!qty.equals("0.0"))
//                            {
//                                hospital_found.add(hos.getName());
//                                bg_qty_found.add(qty);
//                            }
                            qty="0.0";
                        }
//                        Toast.makeText(BookAppointment.this, "upper" + qty, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(BookAppointment.this, "upper" + hospital_found, Toast.LENGTH_SHORT).show();
//
//                        ArrayAdapter adapter = new ArrayAdapter(BookAppointment.this, android.R.layout.simple_list_item_1, bg_qty_found);
//
//                        lvHospitalFound.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        lvHospitalFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String h_name = hospital_found.get(i);
                String qtyy = bg_qty_found.get(i);
                int p = spnBloodGroup.getSelectedItemPosition();
                String selected_bg1 = blood_group.get(p);

                DatabaseReference dbrf = FirebaseDatabase.getInstance().getReference("Hospital");
                dbrf.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot d : snapshot.getChildren())
                        {
                            Hospital hos = d.getValue(Hospital.class);
                            String hosp_name = hos.getName();

                            if(h_name.equals(hosp_name))
                            {
                                String tablename= hos.getUn() + "_BloodGroupDetails";
                                Intent a = new Intent(BookAppointment.this,BookingSlot.class);
                                a.putExtra("table_name",tablename);
                                a.putExtra("receiver_name",reciever_name);
                                a.putExtra("receiver_email",reciever_mail);
                                a.putExtra("h_name",h_name);
                                a.putExtra("qtyy",qtyy);
                                a.putExtra("bloodgrp",selected_bg1);
                                startActivity(a);
                                finish();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });
    }

    private String checkIndividualHospital(String n, String table_name, String selected_bg)
    {
        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference(table_name);
//quantity = "0.0";
        dbrf.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                quantity = "0.0";
                DataSnapshot d = snapshot;
                BloodGroupDetails b = d.getValue(BloodGroupDetails.class);

                if (selected_bg.equals("A-"))
                {
                    quantity = b.getA_negative();
                   // Toast.makeText(BookAppointment.this, "lower "+quantity, Toast.LENGTH_SHORT).show();
                    if (!quantity.equals("0.0"))
                    {
                        hospital_found.add(n);
                        bg_qty_found.add(quantity);
                    }
//                    Toast.makeText(BookAppointment.this, "upper " + qty, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(BookAppointment.this, "upper " + hospital_found, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(BookAppointment.this, "upper " + bg_qty_found, Toast.LENGTH_SHORT).show();

                    CustomAdapter adapter = new CustomAdapter(BookAppointment.this, hospital_found, bg_qty_found);
                    lvHospitalFound.setAdapter(adapter);
                }
                if (selected_bg.equals("A+"))
                {
                    quantity = b.getA_positive();
                    //Toast.makeText(BookAppointment.this, "lower "+quantity, Toast.LENGTH_SHORT).show();
                    if (!quantity.equals("0.0"))
                    {
                        hospital_found.add(n);
                        bg_qty_found.add(quantity);
                    }
                    CustomAdapter adapter = new CustomAdapter(BookAppointment.this, hospital_found, bg_qty_found);
                    lvHospitalFound.setAdapter(adapter);
                }
                if (selected_bg.equals("B-"))
                {
                    quantity = b.getB_negative();
                    //Toast.makeText(BookAppointment.this, "lower "+quantity, Toast.LENGTH_SHORT).show();
                    if (!quantity.equals("0.0"))
                    {
                        hospital_found.add(n);
                        bg_qty_found.add(quantity);
                    }
                    CustomAdapter adapter = new CustomAdapter(BookAppointment.this, hospital_found, bg_qty_found);
                    lvHospitalFound.setAdapter(adapter);
                }
                if (selected_bg.equals("B+"))
                {
                    quantity = b.getB_positive();
                    //Toast.makeText(BookAppointment.this, "lower "+quantity, Toast.LENGTH_SHORT).show();
                    if (!quantity.equals("0.0"))
                    {
                        hospital_found.add(n);
                        bg_qty_found.add(quantity);
                    }
                    CustomAdapter adapter = new CustomAdapter(BookAppointment.this, hospital_found, bg_qty_found);
                    lvHospitalFound.setAdapter(adapter);
                }
                if (selected_bg.equals("AB-"))
                {
                    quantity = b.getAB_negative();
                    //Toast.makeText(BookAppointment.this, "lower "+quantity, Toast.LENGTH_SHORT).show();
                    if (!quantity.equals("0.0"))
                    {
                        hospital_found.add(n);
                        bg_qty_found.add(quantity);
                    }
                    CustomAdapter adapter = new CustomAdapter(BookAppointment.this, hospital_found, bg_qty_found);
                    lvHospitalFound.setAdapter(adapter);
                }
                if (selected_bg.equals("AB+"))
                {
                    quantity = b.getAB_positive();
                    //Toast.makeText(BookAppointment.this, "lower "+quantity, Toast.LENGTH_SHORT).show();
                    if (!quantity.equals("0.0"))
                    {
                        hospital_found.add(n);
                        bg_qty_found.add(quantity);
                    }
                    CustomAdapter adapter = new CustomAdapter(BookAppointment.this, hospital_found, bg_qty_found);
                    lvHospitalFound.setAdapter(adapter);
                }
                if (selected_bg.equals("O-"))
                {
                    quantity = b.getO_negative();
                    //Toast.makeText(BookAppointment.this, "lower "+quantity, Toast.LENGTH_SHORT).show();
                    if (!quantity.equals("0.0"))
                    {
                        hospital_found.add(n);
                        bg_qty_found.add(quantity);
                    }
                    CustomAdapter adapter = new CustomAdapter(BookAppointment.this, hospital_found, bg_qty_found);
                    lvHospitalFound.setAdapter(adapter);
                }
                if (selected_bg.equals("O+"))
                {
                    quantity = b.getO_positive();
                    //Toast.makeText(BookAppointment.this, "lower "+quantity, Toast.LENGTH_SHORT).show();
                    if (!quantity.equals("0.0"))
                    {
                        hospital_found.add(n);
                        bg_qty_found.add(quantity);
                    }
                    CustomAdapter adapter = new CustomAdapter(BookAppointment.this, hospital_found, bg_qty_found);
                    lvHospitalFound.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return quantity;
    }

}