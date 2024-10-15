package lalitjain.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HospitalViewAppointment extends AppCompatActivity {

    ListView lvHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_view_appointment);

        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> blood_group = new ArrayList<>();
        ArrayList<String> blood_quantity = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String> time_slot = new ArrayList<>();
        lvHospital = findViewById(R.id.lvHospital);

        Intent a = getIntent();
        String h_name = a.getStringExtra("name");

        DatabaseReference dbrf = FirebaseDatabase.getInstance().getReference("Appointments/"+h_name);
        dbrf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()){
//                    Toast.makeText(HospitalViewAppointment.this, ""+d.getKey(), Toast.LENGTH_SHORT).show();
                    DatabaseReference dbrf1 = FirebaseDatabase.getInstance().getReference("Appointments/"+h_name+"/"+d.getKey());
                    dbrf1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot d1 : snapshot.getChildren()){
//                                Toast.makeText(HospitalViewAppointment.this, ""+d1.getKey(), Toast.LENGTH_SHORT).show();
                                DatabaseReference dbrf2 = FirebaseDatabase.getInstance().getReference("Appointments/"+h_name+"/"+d.getKey()+"/"+d1.getKey());
                                dbrf2.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot d2: snapshot.getChildren()){
//                                            Toast.makeText(HospitalViewAppointment.this, ""+d2.getKey(), Toast.LENGTH_SHORT).show();
                                            Appointments ap = d2.getValue(Appointments.class);
//                                            Toast.makeText(HospitalViewAppointment.this, "" + ap, Toast.LENGTH_SHORT).show();

                                            name.add(ap.getName());
                                            blood_group.add("Blood Group: " + ap.getBlood_group());
                                            blood_quantity.add("Quantity: " + ap.getBlood_quantity()+" ml");
                                            date.add("Booking Date: " + ap.getDate());
                                            time_slot.add("Time: " + ap.getTime_slot());

//                                            Toast.makeText(HospitalViewAppointment.this, "" + date, Toast.LENGTH_SHORT).show();

                                            CustomizedAdapter customizedAdapter = new CustomizedAdapter(HospitalViewAppointment.this, name, blood_quantity, date, time_slot, blood_group);
                                            lvHospital.setAdapter(customizedAdapter);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
