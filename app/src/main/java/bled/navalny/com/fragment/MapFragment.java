package bled.navalny.com.fragment;


import android.Manifest;
import android.app.Application;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import bled.navalny.com.ApplicationWrapper;
import bled.navalny.com.R;
import bled.navalny.com.views.AlertView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by persick on 12/08/2017.
 */

public class MapFragment extends Fragment
{
	@BindView(R.id.pulsator)
	AlertView pulsator;
	@BindView(R.id.mapView)
	MapView mMapView;
	@BindView(R.id.alertButton)
	AppCompatButton alertButton;

	Unbinder unbinder;

	private GoogleMap mMap;

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
		View view = inflater.inflate(R.layout.fragment_map, container, false);
		unbinder = ButterKnife.bind(this, view);

		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		mMapView.onCreate(savedInstanceState);
		mMapView.onResume();

		mMapView.getMapAsync(new OnMapReadyCallback()
		{
			@Override
			public void onMapReady(GoogleMap googleMap)
			{
				mMap = googleMap;
				mMap.getUiSettings().setZoomGesturesEnabled(false);

				if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
						== PackageManager.PERMISSION_GRANTED) {
					mMap.setMyLocationEnabled(true);
				} else {
					Toast.makeText(getActivity(), "You have to accept to enjoy all app's services!", Toast.LENGTH_SHORT).show();
					if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
							== PackageManager.PERMISSION_GRANTED) {
						mMap.setMyLocationEnabled(true);
					}
				}

				final LatLng sydney = new LatLng(55.709003, 37.655043);
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f));

				mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener()
				{
					@Override
					public void onCameraMove()
					{
						Point position = mMap.getProjection().toScreenLocation(sydney);

						FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) pulsator.getLayoutParams();
						int pulseSize = getResources().getDimensionPixelSize(R.dimen.pulse_size);
						params.setMargins(position.x - pulseSize/2, position.y - pulseSize/2, 0, 0);
						pulsator.setLayoutParams(params);
					}
				});

				if (mMapView.getViewTreeObserver().isAlive()) {
					mMapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
						@Override
						public void onGlobalLayout() {
							mMapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
							Point position = mMap.getProjection().toScreenLocation(sydney);

							FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) pulsator.getLayoutParams();
							int pulseSize = getResources().getDimensionPixelSize(R.dimen.pulse_size);
							params.setMargins(position.x - pulseSize/2, position.y - pulseSize/2, 0, 0);
							pulsator.setLayoutParams(params);

							pulsator.setVisibility(View.VISIBLE);
							pulsator.start();
						}
					});
				}
			}
		});

		alertButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.alert_strings));
				new AlertDialog.Builder(getActivity()).setAdapter(adapter, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
			}
		});

		pulsator.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Toast.makeText(getContext(), "CLICK", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void startAlertsTrecking() {
		Handler.Callback callback = new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				//ApplicationWrapper.bledService.getAlerts()

				return true;
			}
		};

		new Handler(callback).sendEmptyMessage(0);
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		unbinder.unbind();
	}
}
