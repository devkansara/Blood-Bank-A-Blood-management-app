package lalitjain.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminViewSwipe extends AppCompatActivity {
    TextView tvName, tvEmail, tvPhone, tvAadhar, tvBloodGroup, tvAmount;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String donorr_name, donorr_email, donorr_blood_group, donorr_blood_quantity, donorr_phone, donorr_aadhar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_swipe);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvAadhar = findViewById(R.id.tvAadhar);
        tvBloodGroup = findViewById(R.id.tvBloodGroup);
        tvAmount = findViewById(R.id.tvAmount);

        Intent a1 = getIntent();

        String hospital_name = a1.getStringExtra("hospital_name");
        String patient_name = a1.getStringExtra("patient_name");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(hospital_name);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot d : snapshot.getChildren())
                {
                    Donor d1 = d.getValue(Donor.class);
                    String d_name = d1.getName();
                    if(d_name.equals(patient_name))
                    {
                        donorr_aadhar = d1.getAadhar();
                        donorr_name = patient_name;
                        donorr_phone = d1.getPhone();
                        donorr_email = d1.getEmail();
                        donorr_blood_quantity = d1.getBloodamt();
                        donorr_blood_group = d1.getBg();
                        break;
                    }
                }

                tvAadhar.setText("Aadhar: " + donorr_aadhar);
                tvName.setText("Name: " + donorr_name);
                tvPhone.setText("Mobile: " + donorr_phone);
                tvEmail.setText("Email: " + donorr_email);
                tvBloodGroup.setText("Blood Group: " + donorr_blood_group);
                tvAmount.setText("Blood Quantity: " + donorr_blood_quantity + " ml");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}

