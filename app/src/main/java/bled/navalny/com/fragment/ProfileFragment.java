package bled.navalny.com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import bled.navalny.com.R;
import butterknife.BindView;

/**
 * Created by persick on 12/08/2017.
 */

public class ProfileFragment extends Fragment
{
	@BindView(R.id.headerLayout)
	LinearLayout headerLayout;
	@BindView(R.id.phoneNumberLayout)
	EditText phoneNumberLayout;

	butterknife.Unbinder unbinder;

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
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_profile, container, false);
		unbinder = butterknife.ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		unbinder.unbind();
	}
}
