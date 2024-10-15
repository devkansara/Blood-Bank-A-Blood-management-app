package lalitjain.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminLoggedInMenu extends AppCompatActivity {
    Button btnViewAllHospitalPatient,btnViewAllAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_logged_in_menu);

        btnViewAllHospitalPatient = findViewById(R.id.btnViewAllHospitalPatient);
        btnViewAllAppointment = findViewById(R.id.btnViewAllAppointment);

        btnViewAllHospitalPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(AdminLoggedInMenu.this, AdminSeeHospital.class);
                startActivity(a);
            }
        });

        btnViewAllAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(AdminLoggedInMenu.this, AdminViewAppointment.class);
                startActivity(a);
            }
        });
    }

    }
