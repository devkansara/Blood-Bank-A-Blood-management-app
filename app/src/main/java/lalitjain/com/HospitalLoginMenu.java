package lalitjain.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HospitalLoginMenu extends AppCompatActivity {
    Button btnAddDonor,btnViewDonor,btnDeleteDonor,btnViewAppointment;
    String h_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_login_menu);
        btnAddDonor=findViewById(R.id.btnAddDonor);
        btnViewDonor=findViewById(R.id.btnViewDonor);
        btnDeleteDonor=findViewById(R.id.btnDeleteDonor);
        btnViewAppointment=findViewById(R.id.btnViewAppointment);


        Intent a = getIntent() ;
        final String username = a.getStringExtra("username");
        Toast.makeText(HospitalLoginMenu.this, "Welcome " + username, Toast.LENGTH_SHORT).show();

        DatabaseReference dbrf = FirebaseDatabase.getInstance().getReference("Hospital");
        dbrf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()){
                    Hospital hos = d.getValue(Hospital.class);
                    if (hos.getUn().equals(username)){
                        h_name = hos.getName();
//                        Toast.makeText(HospitalLoggedInMenu.this, "in" + h_name, Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnAddDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(HospitalLoginMenu.this,HospitalAddDonor.class);
                a.putExtra("username",username);
                startActivity(a);
            }
        });

        btnViewDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(HospitalLoginMenu.this,HospitalViewDonor.class);
                a.putExtra("username",username);
                startActivity(a);


            }
        });
        btnDeleteDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(HospitalLoginMenu.this,HospitalDeleteDonor.class);
                a.putExtra("username",username);
                startActivity(a);
            }
        });

        btnViewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(HospitalLoginMenu.this,HospitalViewAppointment.class);
                a.putExtra("username",username);
                a.putExtra("name",h_name);
                startActivity(a);

            }
        });

    }
}