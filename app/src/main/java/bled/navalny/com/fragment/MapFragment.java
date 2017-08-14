package bled.navalny.com.fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
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

import java.util.List;

import bled.navalny.com.ApplicationWrapper;
import bled.navalny.com.R;
import bled.navalny.com.model.Alert;
import bled.navalny.com.views.AlertView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by persick on 12/08/2017.
 */

public class MapFragment extends Fragment
{
	@BindView(R.id.mapView)
	MapView mMapView;
	@BindView(R.id.alertButton)
	AppCompatButton alertButton;

	Unbinder unbinder;
	@BindView(R.id.rootLayout)
	FrameLayout rootLayout;
	@BindView(R.id.alertsContainer)
	FrameLayout alertsContainer;

	private GoogleMap mMap;
	private List<Alert> alerts;

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
						== PackageManager.PERMISSION_GRANTED)
				{
					mMap.setMyLocationEnabled(true);
				}
				else
				{
					Toast.makeText(getActivity(), "You have to accept to enjoy all app's services!", Toast.LENGTH_SHORT).show();
					if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
							== PackageManager.PERMISSION_GRANTED)
					{
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
						placeAlerts();
					}
				});
			}
		});

		alertButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
//				ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.alert_strings));
//				new AlertDialog.Builder(getActivity()).setAdapter(adapter, new DialogInterface.OnClickListener()
//				{
//					@Override
//					public void onClick(DialogInterface dialog, int which)
//					{
//
//					}
//				}).show();

				final ProgressDialog dialog = ProgressDialog.show(getContext(), "Ждите", "Помощь уже в пути");
				Alert testAlert = new Alert();
				testAlert.comment = "На хакатон напали краснодарские бабки!";
				testAlert.lat = 55.709003;
				testAlert.lon = 37.655043;

				ApplicationWrapper.bledService.addAlert(testAlert).enqueue(new Callback<Void>()
				{
					@Override
					public void onResponse(Call<Void> call, Response<Void> response)
					{
						mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(55.709003, 37.655043), 15f));
						dialog.dismiss();
						Toast.makeText(getContext(), "Готово", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onFailure(Call<Void> call, Throwable t)
					{
						Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});

//		pulsator.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				Toast.makeText(getContext(), "CLICK", Toast.LENGTH_SHORT).show();
//			}
//		});

		startAlertsTracking();
	}

	public void startAlertsTracking()
	{
		Handler.Callback callback = new Handler.Callback()
		{
			@Override
			public boolean handleMessage(Message msg)
			{
				final Handler.Callback delayedCallback = this;

				ApplicationWrapper.bledService.getAlerts(ApplicationWrapper.location.getLatitude(), ApplicationWrapper.location.getLongitude()).enqueue(new Callback<List<Alert>>()
				{
					@Override
					public void onResponse(Call<List<Alert>> call, Response<List<Alert>> response)
					{
						alerts = response.body();
						updateAlerts();
						new Handler(delayedCallback).sendEmptyMessageDelayed(0, 3000);
					}

					@Override
					public void onFailure(Call<List<Alert>> call, Throwable t)
					{
						Toast.makeText(getContext(), "Не удалось загрузить список событий", Toast.LENGTH_SHORT).show();
					}
				});

				return true;
			}
		};

		new Handler(callback).sendEmptyMessage(0);
	}

	private void updateAlerts() {
		alertsContainer.removeAllViews();
		LayoutInflater inflater = (LayoutInflater) ApplicationWrapper.context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		for (Alert alert : alerts) {
			AlertView alertView = (AlertView) inflater.inflate(R.layout.alert_item, alertsContainer, false);
			alertView.setTag(alert);
			alertView.setVisibility(View.GONE);
			alertView.start();

			alertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Alert alert = (Alert) v.getTag();
					new AlertDialog.Builder(getContext()).setMessage(alert.comment).show();
				}
			});

			alertsContainer.addView(alertView);
		}

		placeAlerts();
	}

	private void placeAlerts() {
		for (int i = 0; i < alertsContainer.getChildCount(); i++) {
			AlertView alertView = (AlertView) alertsContainer.getChildAt(i);
			Alert alert = (Alert) alertView.getTag();

			Point position = mMap.getProjection().toScreenLocation(new LatLng(alert.lat, alert.lon));

			FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) alertView.getLayoutParams();
			int pulseSize = getResources().getDimensionPixelSize(R.dimen.pulse_size);
			params.setMargins(position.x - pulseSize / 2, position.y - pulseSize / 2, 0, 0);
			alertView.setLayoutParams(params);

			alertView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		unbinder.unbind();
	}
}
