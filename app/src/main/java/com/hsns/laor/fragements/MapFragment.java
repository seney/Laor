package com.hsns.laor.fragements;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hsns.laor.R;

import java.util.List;
import java.util.Locale;


/**
 * Created by seney on 12/14/15.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initMapFragment();
        return view;
    }

    private void initMapFragment() {
        if (mMapFragment == null) {
            mMapFragment = new SupportMapFragment() {
                @Override
                public void onActivityCreated(Bundle savedInstanceState) {
                    super.onActivityCreated(savedInstanceState);
                    mMap = mMapFragment.getMap();
                    if (mMap != null) {
                        mMapFragment.getMapAsync(MapFragment.this);
                    }
                }
            };
            getChildFragmentManager().beginTransaction().add(R.id.map, mMapFragment).commit();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupMap();
                } else {
                    Toast.makeText(getContext(), "Location permission is granted", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFadButton();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        initMap();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initFadButton() {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String latitude = mPrefs.getString(getString(R.string.pref_latitude_key),
                        getString(R.string.pref_latitude_default));
                String longitude = mPrefs.getString(getString(R.string.pref_longitude_key),
                        getString(R.string.pref_longitude_default));
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude))).title("You are here!").snippet("Hold and drag to change location.").draggable(true));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude))));
            }
        });
    }

    private void initMap() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            setupMap();
        } else {
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(getContext(), "Location permission is needed for map.", Toast.LENGTH_LONG).show();
            }

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void setupMap() {
        mMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {

            LatLng target = new LatLng(location.getLatitude(), location.getLongitude());

//            CameraPosition position = this.mMap.getCameraPosition();

            CameraPosition.Builder builder = new CameraPosition.Builder();
            builder.zoom(15);
            builder.target(target);

            this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()));

            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {
                    // TODO Auto-generated method stub
                    Log.d("System out", "onMarkerDragStart..." + marker.getPosition().latitude + "..." + marker.getPosition().longitude);
                }

                @SuppressWarnings("unchecked")
                @Override
                public void onMarkerDragEnd(Marker marker) {
                    // TODO Auto-generated method stub
                    Log.d("System out", "onMarkerDragEnd..." + marker.getPosition().latitude + "..." + marker.getPosition().longitude);

                    mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));

                    LatLng position = marker.getPosition();

                    String snippedInfo = getAddress(position.latitude, position.longitude);
                    snippedInfo = (snippedInfo.equals("")) ? "Undefined" : snippedInfo;
                    marker.setSnippet(snippedInfo);

                    Toast.makeText(getContext(), getAddress(position.latitude, position.longitude), Toast.LENGTH_LONG).show();

                    SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putString(getString(R.string.pref_latitude_key), position.latitude + "");
                    editor.putString(getString(R.string.pref_longitude_key), position.longitude + "");
                    editor.putString(getString(R.string.pref_address_key), snippedInfo);
                    editor.commit();
                }

                @Override
                public void onMarkerDrag(Marker marker) {
                    // TODO Auto-generated method stub
                    Log.i("System out", "onMarkerDrag...");
                }
            });
            mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("You are here!").snippet("Hold and drag to change location.").draggable(true));
        }
    }

    private String getAddress(double latitude, double longitude) {
        String fullAddress = "";
        try {
            Geocoder geo = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (!addresses.isEmpty()) {
                if (addresses.size() > 0) {
                    //yourtextfieldname.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                    //Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
                    //address = "Address:- " + addresses.get(0).getFeatureName() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getLocality();

                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();

                    fullAddress = address + "," + city + "," + state + "," + country + "," + postalCode + "," + knownName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }

        fullAddress = fullAddress.replace("null", "");
        fullAddress = fullAddress.replace("Unnamed Rd", "");
        fullAddress = fullAddress.replace(",,", ",");
        if (fullAddress.lastIndexOf(",") == fullAddress.length() - 1)
            fullAddress = fullAddress.substring(0, fullAddress.lastIndexOf(","));
        if (fullAddress.substring(0, 1).equals(","))
            fullAddress = fullAddress.substring(1);
        fullAddress = fullAddress.replace(",", ", ");

        return fullAddress;
    }
}
