package bled.navalny.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import bled.navalny.com.R;
import bled.navalny.com.RegisterActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by persick on 12/08/2017.
 */

public class ProfileFragment extends Fragment
{
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
	@BindView(R.id.exitButton)
	AppCompatButton exitButton;

	public ProfileFragment()
	{
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_profile, container, false);
		unbinder = ButterKnife.bind(this, view);

		exitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), RegisterActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		onViewClicked();

		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		userNameTextView.setText(getArguments().getString("NAME"));
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		unbinder.unbind();
	}

	@OnClick(R.id.permissionSwitch)
	public void onViewClicked()
	{
		if (permissionSwitch.isChecked())
		{
			chooseTimeRadioGroup.setVisibility(View.VISIBLE);
		}
		else
		{
			chooseTimeRadioGroup.setVisibility(View.GONE);
		}
	}
}
