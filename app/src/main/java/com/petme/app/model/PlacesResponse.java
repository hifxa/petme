package com.petme.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings ( "unused" )
public class PlacesResponse {

	@SerializedName ( "results" )
	public List < Results > results;

	public static class Results {

		@SerializedName ( "types" )
		public List < String > types;

		@SerializedName ( "business_status" )
		public String businessStatus;

		@SerializedName ( "icon" )
		public String icon;

		@SerializedName ( "rating" )
		public double rating;

		@SerializedName ( "icon_background_color" )
		public String iconBackgroundColor;

		@SerializedName ( "photos" )
		public List < Results.PhotosItem > photos;

		@SerializedName ( "reference" )
		public String reference;

		@SerializedName ( "user_ratings_total" )
		public int userRatingsTotal;

		@SerializedName ( "scope" )
		public String scope;

		@SerializedName ( "name" )
		public String name;

		@SerializedName ( "opening_hours" )
		public Results.OpeningHours openingHours;

		@SerializedName ( "geometry" )
		public Results.Geometry geometry;

		@SerializedName ( "icon_mask_base_uri" )
		public String iconMaskBaseUri;

		@SerializedName ( "vicinity" )
		public String vicinity;

		@SerializedName ( "plus_code" )
		public Results.PlusCode plusCode;

		@SerializedName ( "place_id" )
		public String placeId;

		public static class Geometry {

			@SerializedName ( "viewport" )
			public Results.Viewport viewport;

			@SerializedName ( "location" )
			public Results.Location location;
		}

		public static class Location {

			@SerializedName ( "lng" )
			public double lng;

			@SerializedName ( "lat" )
			public double lat;
		}

		public static class Northeast {

			@SerializedName ( "lng" )
			public double lng;

			@SerializedName ( "lat" )
			public double lat;
		}

		public static class OpeningHours {

			@SerializedName ( "open_now" )
			public boolean openNow;
		}

		public static class PhotosItem {

			@SerializedName ( "photo_reference" )
			public String photoReference;

			@SerializedName ( "width" )
			public int width;

			@SerializedName ( "html_attributions" )
			public List < String > htmlAttributions;

			@SerializedName ( "height" )
			public int height;
		}

		public static class PlusCode {

			@SerializedName ( "compound_code" )
			public String compoundCode;

			@SerializedName ( "global_code" )
			public String globalCode;
		}

		public static class Southwest {

			@SerializedName ( "lng" )
			public double lng;

			@SerializedName ( "lat" )
			public double lat;
		}

		public static class Viewport {

			@SerializedName ( "southwest" )
			public Results.Southwest southwest;

			@SerializedName ( "northeast" )
			public Results.Northeast northeast;
		}
	}

}