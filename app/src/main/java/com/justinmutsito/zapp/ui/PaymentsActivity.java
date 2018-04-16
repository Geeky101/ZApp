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
    int itemCount = 0;
    Integer totalAmount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.amountLabel, R.id.amountField, R.id.titheCheckBox, R.id.offeringCheckBox, R.id.buildingCheckBox, R.id.otherCheckBox})
    public void onViewClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.titheCheckBox:
                if (checked) {
                    itemCount++;
                    mTitheLabel.setVisibility(View.VISIBLE);
                    mTitheField.setVisibility(View.VISIBLE);
                    if (itemCount == 1) {
                        if (!"".equals(mAmountField.getText().toString())) {
                            mTitheField.setText(mAmountField.getText());
                            mTitheField.setEnabled(false);
                        }else{
                            mTitheField.setEnabled(true);
                        }
                    }
                } else {
                    itemCount--;
                    mTitheLabel.setVisibility(View.GONE);
                    mTitheField.setVisibility(View.GONE);

                }
                break;
            case R.id.offeringCheckBox:
                if (checked) {
                    itemCount++;
                    mOfferingLabel.setVisibility(View.VISIBLE);
                    mOfferingField.setVisibility(View.VISIBLE);
                    if (itemCount == 1) {
                        if (!"".equals(mAmountField.getText().toString())) {
                            mOfferingField.setText(mAmountField.getText().toString());
                            mOfferingField.setEnabled(false);
                        }else{
                            mOfferingField.setEnabled(true);
                        }
                    }
                } else {
                    itemCount--;
                    mOfferingLabel.setVisibility(View.GONE);
                    mOfferingField.setVisibility(View.GONE);

                }
                break;
            case R.id.buildingCheckBox:
                if (checked) {
                    itemCount++;
                    mBuildingLabel.setVisibility(View.VISIBLE);
                    mBuildingField.setVisibility(View.VISIBLE);
                    if (itemCount == 1) {
                        if (!"".equals(mAmountField.getText().toString())) {
                            mBuildingField.setText(mAmountField.getText());
                            mBuildingField.setEnabled(false);
                        }else{
                            mBuildingField.setEnabled(true);
                        }
                    }
                } else {
                    itemCount--;
                    mBuildingLabel.setVisibility(View.GONE);
                    mBuildingField.setVisibility(View.GONE);

                }
                break;
            case R.id.otherCheckBox:
                if (checked) {
                    itemCount++;
                    mOtherLabel.setVisibility(View.VISIBLE);
                    mOtherField.setVisibility(View.VISIBLE);
                } else {
                    itemCount--;
                    mOtherLabel.setVisibility(View.GONE);
                    mOtherField.setVisibility(View.GONE);
                }
                break;
        }

    }

    @OnClick(R.id.paymentButton)
    public void onPaymentClicked() {
        if (totalAmount==0) {
            Toast.makeText(PaymentsActivity.this, "Enter amount to make payment", Toast.LENGTH_SHORT).show();
        }
        if (!totalAmount.toString().equals(mAmountField.toString())){
            Toast.makeText(PaymentsActivity.this, "Total amount does not equal individual amounts", Toast.LENGTH_SHORT).show();
        }else{
//            initiate phone api and callback method
        }
    }
}
