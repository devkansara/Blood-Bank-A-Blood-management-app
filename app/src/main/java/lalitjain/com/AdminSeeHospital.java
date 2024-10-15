package lalitjain.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import lalitjain.com.databinding.ActivityAdminSeeHospitalBinding;

public class AdminSeeHospital extends AppCompatActivity {
    private @NonNull ActivityAdminSeeHospitalBinding binding;
    RecyclerView recyclerview;
    ArrayList<String> hospital  = new ArrayList<>();
    String data;
    Adapter adapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String hosp_un="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_see_hospital);

        setContentView(R.layout.activity_admin_view_hospital_patient);
        recyclerview = findViewById(R.id.recyclerview);
        binding = ActivityAdminSeeHospitalBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Hospital");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hospital.clear();
                for(DataSnapshot d : snapshot.getChildren()){
                    Hospital h = d.getValue(Hospital.class);
                    String name = h.getName();
                    hospital.add(name);
                }

                adapter = new Adapter(hospital);
                binding.recyclerview.setAdapter(adapter);
                adapter.notifyDataSetChanged();


                ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        String deleteHospital;
                        int position = viewHolder.getAdapterPosition();
                        deleteHospital = hospital.get(position);

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot d : snapshot.getChildren()){
                                    Hospital hos = d.getValue(Hospital.class);
                                    String hosp_name = hos.getName();

                                    if (deleteHospital.equals(hosp_name))
                                    {
                                        hosp_un = hos.getUn();
                                        break;
                                    }
                                }



                                AlertDialog.Builder builder =new AlertDialog.Builder
                                        (AdminSeeHospital.this);
                                LayoutInflater layoutInflater = getLayoutInflater();
                                View v = layoutInflater.inflate(R.layout.del_layout, null);
                                builder.setView(v);
                                builder.setTitle("Do you want to delete? It will also delete donors details too!!");
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();


                                Button btnDelete = v.findViewById(R.id.btnDelete);

                                btnDelete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        DatabaseReference d1 = FirebaseDatabase.getInstance().getReference(hosp_un);
                                        d1.removeValue();

                                        DatabaseReference d2 = FirebaseDatabase.getInstance().getReference("Hospital").child(hosp_un);
                                        d2.removeValue();

                                        String table_name = hosp_un +"_BloodGroupDetails";
                                        DatabaseReference d3 = FirebaseDatabase.getInstance().getReference(table_name);
                                        d3.removeValue();

                                        Toast.makeText(AdminSeeHospital.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                                        alertDialog.dismiss();
                                    }
                                });
                                adapter = new Adapter(hospital);
                                binding.recyclerview.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                };

                ItemTouchHelper.SimpleCallback simpleCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                        int position = viewHolder.getAdapterPosition();
                        String patient_name_to_view = hospital.get(position);

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            String h_username = "";
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot d : snapshot.getChildren()){
                                    Hospital hos = d.getValue(Hospital.class);
                                    String name = hos.getName();

                                    if (patient_name_to_view.equals(name)){
                                        h_username = hos.getUn();
                                        Intent a = new Intent(AdminSeeHospital.this, AdminViewHospitalPatient.class);
                                        a.putExtra("h_username", h_username);
                                        startActivity(a);


                                    }
                                }
                                adapter = new Adapter(hospital);
                                binding.recyclerview.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                };
                new ItemTouchHelper(simpleCallback1).attachToRecyclerView(binding.recyclerview);
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.recyclerview);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}