package com.petme.app.view.dash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.PagerSnapHelper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.petme.app.R;
import com.petme.app.base.BaseFragment;
import com.petme.app.controllers.MapsShopAdapter;
import com.petme.app.databinding.FragmentVetBinding;
import com.petme.app.interfaces.AlertClicks;
import com.petme.app.interfaces.RecyclerClicks;
import com.petme.app.model.PlacesResponse;
import com.petme.app.utils.Alerts;

@SuppressWarnings ( "ConstantConditions" )
@SuppressLint ( { "SetTextI18n" , "MissingPermission" , "StaticFieldLeak" } )
public class VetFragment extends BaseFragment < FragmentVetBinding > implements OnMapReadyCallback {

	private FusedLocationProviderClient fusedClient;
	private Location currentLocation;
	private GoogleMap mMap;
	private LocationManager locationManager;
	private MapsShopAdapter mAdapter;

	@Override
	public FragmentVetBinding getBind ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container ) {
		return FragmentVetBinding.inflate ( inflater , container , false );
	}

	@Override
	public void onViewCreated ( @NonNull View view , @Nullable Bundle savedInstanceState ) {
		super.onViewCreated ( view , savedInstanceState );

		bind.header.getBack ( ).setOnClickListener ( v -> Navigation.findNavController ( v ).popBackStack ( ) );
		bind.header.getTitle ( ).setText ( "Find Nearest Shop/Vet" );
		fusedClient = LocationServices.getFusedLocationProviderClient ( getActivity ( ) );
		locationManager = ( LocationManager ) mCtx.getSystemService ( Context.LOCATION_SERVICE );

		SupportMapFragment supportMapFragment = ( SupportMapFragment ) getChildFragmentManager ( ).findFragmentById ( R.id.map );
		supportMapFragment.getMapAsync ( this );

		checkIfLocationIsEnabled ( );
	}

	@Override
	public void onStart ( ) {
		super.onStart ( );
		fetchLastLocation ( );
	}

	@Override
	public void onMapReady ( @NonNull GoogleMap googleMap ) {
		mMap = googleMap;
		mMap.getUiSettings ( ).setMapToolbarEnabled ( false );
		mMap.getUiSettings ( ).setCompassEnabled ( true );
		mMap.getUiSettings ( ).setMyLocationButtonEnabled ( false );
		mMap.setMapType ( GoogleMap.MAP_TYPE_NORMAL );
	}

	private void checkIfLocationIsEnabled ( ) {
		boolean gps_enabled = false;
		boolean network_enabled = false;

		try {
			gps_enabled = locationManager.isProviderEnabled ( LocationManager.GPS_PROVIDER );
		}
		catch ( Exception ex ) {
			ex.printStackTrace ( );
		}

		try {
			network_enabled = locationManager.isProviderEnabled ( LocationManager.NETWORK_PROVIDER );
		}
		catch ( Exception ex ) {
			ex.printStackTrace ( );
		}

		if ( ! gps_enabled && ! network_enabled ) {
			Alerts.showAlert ( mCtx , "Oops!" , "Looks like location is not enabled." , false , true , new AlertClicks ( ) {
				@Override
				public void positiveClick ( DialogInterface alert ) {
					mCtx.startActivity ( new Intent ( Settings.ACTION_LOCATION_SOURCE_SETTINGS ) );
				}

				@Override
				public void negativeClick ( DialogInterface alert ) {
					Navigation.findNavController ( bind.getRoot ( ) ).popBackStack ( );
				}
			} );
		}
	}

	//this will fetch the most recent updated location of the device and save it as the current location
	private void fetchLastLocation ( ) {

		Task < Location > task = fusedClient.getLastLocation ( );

		task.addOnSuccessListener ( location -> {
			if ( location != null ) {
				currentLocation = location;
				LatLng latLng = new LatLng ( currentLocation.getLatitude ( ) , currentLocation.getLongitude ( ) );

				// this is the actual code to add the marker
				MarkerOptions markerOptions = new MarkerOptions ( ).position ( latLng ).title ( "Here I am!" ).icon ( vectorToBitmap ( R.drawable.pet , R.color.onShop ) );
				mMap.animateCamera ( CameraUpdateFactory.newLatLng ( latLng ) );
				mMap.animateCamera ( CameraUpdateFactory.newLatLngZoom ( latLng , 16 ) );
				mMap.addMarker ( markerOptions );

				getNearby ( mMap );
			}
		} );
	}

	//this is going to set a marker of the current location which is updated each and every time this is called

	private BitmapDescriptor vectorToBitmap ( @DrawableRes int id , int color ) {
		Drawable vectorDrawable = ContextCompat.getDrawable ( mCtx , id );
		Bitmap bitmap = Bitmap.createBitmap ( vectorDrawable.getIntrinsicWidth ( ) , vectorDrawable.getIntrinsicHeight ( ) , Bitmap.Config.ARGB_8888 );
		Canvas canvas = new Canvas ( bitmap );
		vectorDrawable.setBounds ( 0 , 0 , canvas.getWidth ( ) , canvas.getHeight ( ) );
		DrawableCompat.setTint ( vectorDrawable , ContextCompat.getColor ( mCtx , color ) );
		vectorDrawable.draw ( canvas );
		return BitmapDescriptorFactory.fromBitmap ( bitmap );
	}

	private void getNearby ( GoogleMap map ) {

		String mLoc = currentLocation.getLatitude ( ) + "," + currentLocation.getLongitude ( );
		String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
				"?location=" + mLoc +
				"&radius=50000" +
				"&types=pet_store" +
				"&name=" + "pets" +
				"&key=" + getResources ( ).getString ( R.string.api_key );

		StringRequest mRequest = new StringRequest ( Request.Method.GET , url , new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {

				PlacesResponse mPlace = new Gson().fromJson(response, PlacesResponse.class);

				for (PlacesResponse.Results result : mPlace.results) {
					LatLng latLng = new LatLng(result.geometry.location.lat, result.geometry.location.lng);

					MarkerOptions mOptions = new MarkerOptions ( )
							.position ( latLng )
							.title ( result.name )
							.icon ( VetFragment.this.vectorToBitmap ( R.drawable.ic_baseline_location_on_24 , R.color.shop ) );
					map.addMarker(mOptions);
				}

				mAdapter = new MapsShopAdapter(mCtx, mPlace.results, new RecyclerClicks() {
					@Override
					public void onItemClick(int pos, String type1) {

					}
				});
				bind.shops.setAdapter(mAdapter);

			new PagerSnapHelper( ).attachToRecyclerView ( bind.shops );

				bind.shops.setVisibility(View.VISIBLE);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				error.printStackTrace();
				Alerts.log(TAG, "ERROR : " + error.getLocalizedMessage());
			}
		});

		Volley.newRequestQueue ( mCtx ).add ( mRequest );

	}
}