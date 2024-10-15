package lalitjain.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import lalitjain.com.databinding.ActivityAdminViewAppointmentBinding;

public class AdminViewAppointment extends AppCompatActivity {
    //    ListView lvHospitalList;
    private ActivityAdminViewAppointmentBinding binding;
    RecyclerView rvHospitalDonor;
    ArrayList<String> h_name  = new ArrayList<>();
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_appointment);

        rvHospitalDonor = findViewById(R.id.rvHospitalDonor);
        binding = ActivityAdminViewAppointmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.rvHospitalDonor.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference dbrf = FirebaseDatabase.getInstance().getReference("Hospital");

        dbrf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()){
                    Hospital hos = d.getValue(Hospital.class);
                    h_name.add(hos.getName());
                }
                adapter = new Adapter(h_name);
                binding.rvHospitalDonor.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                ItemTouchHelper.SimpleCallback simpleCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        String current_hospital = h_name.get(position);

                        //     Toast.makeText(AdminViewHospitalPatient.this, "" + donor_name_to_view, Toast.LENGTH_SHORT).show();


                        Intent a = new Intent(AdminViewAppointment.this, AdminActualViewAppointment.class);
                        a.putExtra("current_hospital", current_hospital);
                        startActivity(a);

                        adapter = new Adapter(h_name);
                        binding.rvHospitalDonor.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                };
                new ItemTouchHelper(simpleCallback1).attachToRecyclerView(binding.rvHospitalDonor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}