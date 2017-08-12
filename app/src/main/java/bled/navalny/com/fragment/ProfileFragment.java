package bled.navalny.com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import bled.navalny.com.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by persick on 12/08/2017.
 */

public class ProfileFragment extends Fragment {
    @BindView(R.id.headerLayout)
    LinearLayout headerLayout;
    @BindView(R.id.phoneNumberTextView)
    TextView phoneNumberTextView;

    Unbinder unbinder;
    @BindView(R.id.permissionSwitch)
    Switch permissionSwitch;
    @BindView(R.id.chooseTimeRadioGroup)
    RadioGroup chooseTimeRadioGroup;
    @BindView(R.id.userNameTextView)
    public TextView userNameTextView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

//        FormatWatcher formatWatcher = new MaskFormatWatcher(
//                MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER) // маска для серии и номера
//        );
//        formatWatcher.installOn(phoneNumberTextView);

        onViewClicked();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.permissionSwitch)
    public void onViewClicked() {
        if (permissionSwitch.isChecked()) {
            chooseTimeRadioGroup.setVisibility(View.VISIBLE);
        } else {
            chooseTimeRadioGroup.setVisibility(View.GONE);
        }
    }
}
