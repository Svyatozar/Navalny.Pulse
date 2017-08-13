package bled.navalny.com;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.phoneEditText)
    EditText phoneEditText;
    @BindView(R.id.numberLayout)
    LinearLayout numberLayout;
    @BindView(R.id.smsCodeEditText)
    EditText smsCodeEditText;
    @BindView(R.id.smsLayout)
    LinearLayout smsLayout;
    @BindView(R.id.nameEditText)
    EditText nameEditText;
    @BindView(R.id.nameLayout)
    LinearLayout nameLayout;
    @BindView(R.id.sendNumberButton)
    AppCompatButton sendNumberButton;
    @BindView(R.id.sendCodeButton)
    AppCompatButton sendCodeButton;
    @BindView(R.id.sendNameButton)
    AppCompatButton sendNameButton;
    @BindView(R.id.mainRegisterLayout)
    FrameLayout mainRegisterLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

                FormatWatcher formatWatcher = new MaskFormatWatcher(
                MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER) // маска для серии и номера
        );
        formatWatcher.installOn(phoneEditText);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    private void checkInputNumber() {
            if (phoneEditText.getText().length() == 18) {
                smsLayout.setVisibility(View.VISIBLE);
                numberLayout.setVisibility(View.GONE);
            }
            //TODO else show message
    }

    private void checkInputCode() {
            if (smsCodeEditText.getText().length() == 4) {
                nameLayout.setVisibility(View.VISIBLE);
                smsLayout.setVisibility(View.GONE);
            }
            //TODO else show message
    }

    private void checkName() {
            if (nameEditText.getText().length() > 1) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("userName", nameEditText.getText().toString());
                startActivity(intent);
            }
    }

    @OnClick({R.id.sendNumberButton, R.id.sendCodeButton, R.id.sendNameButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sendNumberButton:
                checkInputNumber();
                break;
            case R.id.sendCodeButton:
                checkInputCode();
                break;
            case R.id.sendNameButton:
                checkName();
                break;
        }
    }

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
	{
		switch (requestCode)
		{
			case 0:
			{
				if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					//DO SOMETHING
				}
				else
				{
					Toast.makeText(this, R.string.permissions_alert, Toast.LENGTH_SHORT).show();
					finish();
				}
				return;
			}
		}
	}
}
