package bled.navalny.com;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

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
    }

    private void checkInputNumber() {
        int check = phoneEditText.getText().length();
            if (phoneEditText.getText().length() == 18) {
                numberLayout.setVisibility(View.GONE);
                smsLayout.setVisibility(View.VISIBLE);
            }
            //TODO else show message
    }

    private void checkInputCode() {
            if (smsCodeEditText.getText().length() == 4) {
                smsLayout.setVisibility(View.GONE);
                nameLayout.setVisibility(View.VISIBLE);
            }
            //TODO else show message
    }

    private void checkName() {
            if (nameEditText.getText().length() > 1) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("userName", nameEditText.toString());
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
}
