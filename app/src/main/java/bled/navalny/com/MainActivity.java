package bled.navalny.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import bled.navalny.com.fragment.MapFragment;
import bled.navalny.com.fragment.NotificationsFragment;
import bled.navalny.com.fragment.ProfileFragment;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_face_white_24dp,
            R.drawable.ic_place_white_24dp,
            R.drawable.ic_notifications_active_white_24dp
    };

    private GoogleMap mMap;
    private MapFragment mapFragment;

    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		mapFragment = new MapFragment();
		mapFragment.getMapAsync(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        profileFragment = new ProfileFragment();

        Intent intent = getIntent();
        //profileFragment.userNameTextView.setText(intent.getStringExtra("userName"));

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

		LatLng sydney = new LatLng(55.709003, 37.655043);
		mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in FBK"));
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14f));
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(profileFragment, getString(R.string.profile));
        adapter.addFrag(mapFragment, getString(R.string.map));
        adapter.addFrag(new NotificationsFragment(), getString(R.string.events));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
