package com.justinmutsito.zapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.justinmutsito.zapp.R;

public class PaymentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

    }

    public void selectItem(View view){
        EditText txtTithe, txtOffering, txtBuilding, txtOther;
        TextView vTithe, vOffering, vBuilding, vOther;

        txtTithe = findViewById(R.id.txtTithe);
        txtOffering =  findViewById(R.id.txtOffering);
        txtBuilding = findViewById(R.id.txtBuilding);
        txtOther = findViewById(R.id.txtOther);

        vTithe = findViewById(R.id.vTithe);
        vOffering= findViewById(R.id.vOffering);
        vBuilding =  findViewById(R.id.vBuilding);
        vOther = findViewById(R.id.vOther);

        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()){
            case R.id.chkTithe:
                if (checked){
                    txtTithe.setVisibility(View.VISIBLE);
                    vTithe.setVisibility(View.VISIBLE);
                }else{
                    txtTithe.setVisibility(View.GONE);
                    vTithe.setVisibility(View.GONE);
                }
                break;

            case R.id.chkOffering:
                if (checked){
                    txtOffering.setVisibility(View.VISIBLE);
                    vOffering.setVisibility(View.VISIBLE);
                }else{
                    txtOffering.setVisibility(View.GONE);
                    vOffering.setVisibility(View.GONE);
                }
                break;

            case R.id.chkBuilding:
                if (checked){
                    txtBuilding.setVisibility(View.VISIBLE);
                    vBuilding.setVisibility(View.VISIBLE);
                }else{
                    txtBuilding.setVisibility(View.GONE);
                    vBuilding.setVisibility(View.GONE);
                }
                break;

            case R.id.chkOther:
                if (checked){
                    txtOther.setVisibility(View.VISIBLE);
                    vOther.setVisibility(View.VISIBLE);
                }else{
                    txtOther.setVisibility(View.GONE);
                    vOther.setVisibility(View.GONE);
                }
                break;

        }
    }
}
