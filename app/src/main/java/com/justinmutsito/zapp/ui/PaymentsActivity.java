package com.justinmutsito.zapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.justinmutsito.zapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentsActivity extends AppCompatActivity {


    @BindView(R.id.amountLabel)
    TextView mAmountLabel;
    @BindView(R.id.amountField)
    EditText mAmountField;
    @BindView(R.id.paymentButton)
    Button mPaymentButton;
    @BindView(R.id.titheCheckBox)
    CheckBox mTitheCheckBox;
    @BindView(R.id.offeringCheckBox)
    CheckBox mOfferingCheckBox;
    @BindView(R.id.buildingCheckBox)
    CheckBox mBuildingCheckBox;
    @BindView(R.id.otherCheckBox)
    CheckBox mOtherCheckBox;
    @BindView(R.id.otherField)
    EditText mOtherField;
    @BindView(R.id.titheLabel)
    TextView mTitheLabel;
    @BindView(R.id.titheField)
    EditText mTitheField;
    @BindView(R.id.offeringLabel)
    TextView mOfferingLabel;
    @BindView(R.id.buildingLabel)
    TextView mBuildingLabel;
    @BindView(R.id.otherLabel)
    TextView mOtherLabel;
    @BindView(R.id.offeringField)
    EditText mOfferingField;
    @BindView(R.id.buildingField)
    EditText mBuildingField;
    int itemCount=0;
    Integer totalAmount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.amountLabel, R.id.amountField, R.id.paymentButton, R.id.titheCheckBox, R.id.offeringCheckBox, R.id.buildingCheckBox, R.id.otherCheckBox})
    public void onViewClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.paymentButton:
                totalAmount = (Integer.parseInt(mTitheField.getText().toString()) + Integer.parseInt(mOfferingField.getText().toString()) + Integer.parseInt(mBuildingField.getText().toString()));
                if (totalAmount.toString().equals(mAmountField.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Amounts equal", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Amounts do not equal", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.titheCheckBox:
                if (checked){
                    itemCount++;
                    mTitheLabel.setVisibility(View.VISIBLE);
                    mTitheField.setVisibility(View.VISIBLE);
                    if (itemCount==1){
                        mTitheField.setText(mAmountField.getText());
                        mTitheField.setEnabled(false);
                    }
                }else{
                    itemCount--;
                    mTitheLabel.setVisibility(View.GONE);
                    mTitheField.setVisibility(View.GONE);
                    mTitheField.setEnabled(true);
                }
                break;
            case R.id.offeringCheckBox:
                if (checked){
                    itemCount++;
                    mOfferingLabel.setVisibility(View.VISIBLE);
                    mOfferingField.setVisibility(View.VISIBLE);
                    if (itemCount==1){
                        mOfferingField.setText(mAmountField.getText());
                        mOfferingField.setEnabled(false);
                    }
                }else{
                    itemCount--;
                    mOfferingLabel.setVisibility(View.GONE);
                    mOfferingField.setVisibility(View.GONE);
                    mOfferingField.setEnabled(true);
                }
                break;
            case R.id.buildingCheckBox:
                if (checked){
                    itemCount++;
                    mBuildingLabel.setVisibility(View.VISIBLE);
                    mBuildingField.setVisibility(View.VISIBLE);
                    if (itemCount==1){
                        mBuildingField.setText(mAmountField.getText());
                        mBuildingField.setEnabled(false);
                    }
                }else{
                    itemCount--;
                    mBuildingLabel.setVisibility(View.GONE);
                    mBuildingField.setVisibility(View.GONE);
                    mBuildingField.setEnabled(true);
                }
                break;
            case R.id.otherCheckBox:
                if (checked){
                    itemCount++;
                    mOtherLabel.setVisibility(View.VISIBLE);
                    mOtherField.setVisibility(View.VISIBLE);
                }else{
                    itemCount--;
                    mOtherLabel.setVisibility(View.GONE);
                    mOtherField.setVisibility(View.GONE);
                }
                break;
        }

    }
}
