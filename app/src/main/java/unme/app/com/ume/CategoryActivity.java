package unme.app.com.ume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class CategoryActivity extends AppCompatActivity {
    private ImageButton btnWVenus, btnWstationayry, btnWdecoration, btnWbouqets, btnWCultural, btnWwear, btnWBridal, btnWcake,
            btnWent, btnWtransport, btnWphotography, btnWhoneymoon;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        btnWVenus = findViewById(R.id.btnWVenus);
        btnWstationayry = findViewById(R.id.btnWstationayry);
        btnWdecoration = findViewById(R.id.btnWdecoration);
        btnWbouqets = findViewById(R.id.btnWbouqets);
        btnWCultural = findViewById(R.id.btnWCultural);
        btnWwear = findViewById(R.id.btnWwear);
        btnWBridal = findViewById(R.id.btnWBridal);
        btnWcake = findViewById(R.id.btnWcake);
        btnWent = findViewById(R.id.btnWent);
        btnWtransport = findViewById(R.id.btnWtransport);
        btnWphotography = findViewById(R.id.btnWphotography);
        btnWhoneymoon = findViewById(R.id.btnWhoneymoon);
        intent = new Intent(CategoryActivity.this, SearchServicesActivity.class);
        btnWVenus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category","WEDDING VENUES");
                startActivity(intent);
            }
        });

        btnWstationayry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category","WEDDING STATIONARY");
                startActivity(intent);
            }
        });

        btnWdecoration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category","WEDDING DECORATION");
                startActivity(intent);
            }
        });

        btnWbouqets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category","WEDDING BOUQUETS");
                startActivity(intent);
            }
        });

        btnWCultural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category","CULTURAL REQUIREMENTS");
                startActivity(intent);
            }
        });
        btnWwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category","WEDDING WEAR");
                startActivity(intent);
            }
        });
        btnWBridal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category","BRIDAL BEAUTICIANS");
                startActivity(intent);
            }
        });

        btnWcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category","WEDDING CAKES");
                startActivity(intent);
            }
        });

        btnWent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category","WEDDING ENTERTAINMENT");
                startActivity(intent);
            }
        });

        btnWtransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category","WEDDING TRANSPORT");
                startActivity(intent);
            }
        });

        btnWphotography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category","WEDDING PHOTOGRAPHY");
                startActivity(intent);
            }
        });

        btnWhoneymoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category","HONEYMOON PLANNING");
                startActivity(intent);
            }
        });
        }
}
