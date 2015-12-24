package com.grafixartist.gallery;

import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.grafixartist.gallery.retrogram.Instagram;
import com.grafixartist.gallery.retrogram.model.Media;
import com.grafixartist.gallery.retrogram.model.SearchMediaResponse;
import com.grafixartist.gallery.util.GPSTracker;
import com.grafixartist.gallery.vk.VK;
import com.grafixartist.gallery.vk.model.Items;
import com.grafixartist.gallery.vk.model.VkResponse;

import java.util.ArrayList;

import retrofit.RestAdapter;

public class MainActivity extends AppCompatActivity {

    GalleryAdapter mAdapter;
    RecyclerView mRecyclerView;

    ArrayList<ImageModel> data = new ArrayList<>();

    protected static String AccessToken = "398315918.19f142f.1a6004bc7ce04dc1bc0a7914095a30cb";
    protected static String ClientId = "19f142f8b92641e7b528497c9d206379";
    protected ArrayList<String> imagesList = new ArrayList<>();

    protected Double latitude;
    protected Double longitude;

    protected Instagram instagram;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    protected GPSTracker gps;


//    public static String IMGS[] = {
//           "https://images.unsplash.com/photo-1444090542259-0af8fa96557e?q=80&fm=jpg&w=1080&fit=max&s=4b703b77b42e067f949d14581f35019b",
//            "https://images.unsplash.com/photo-1439546743462-802cabef8e97?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
//            "https://images.unsplash.com/photo-1441155472722-d17942a2b76a?q=80&fm=jpg&w=1080&fit=max&s=80cb5dbcf01265bb81c5e8380e4f5cc1",
//            "https://images.unsplash.com/photo-1437651025703-2858c944e3eb?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
//            "https://images.unsplash.com/photo-1431538510849-b719825bf08b?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
//            "https://images.unsplash.com/photo-1434873740857-1bc5653afda8?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
//            "https://images.unsplash.com/photo-1439396087961-98bc12c21176?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
//            "https://images.unsplash.com/photo-1433616174899-f847df236857?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
//            "https://images.unsplash.com/photo-1438480478735-3234e63615bb?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
//            "https://images.unsplash.com/photo-1438027316524-6078d503224b?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300"
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gps = new GPSTracker(MainActivity.this);

        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.i("latitude {}", latitude.toString());
            Log.i("longitude {}", longitude.toString());
//            double altitude = gps.getAltitude();


        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }



        new AsyncHttpTask().execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setHasFixedSize(true);


        mAdapter = new GalleryAdapter(MainActivity.this, data);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putParcelableArrayListExtra("data", data);
                        intent.putExtra("pos", position);
                        startActivity(intent);

                    }
                }));

    }

    //Downloading data asynchronously
    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            try {
                instagram = new Instagram(AccessToken, RestAdapter.LogLevel.BASIC);
                long min = (System.currentTimeMillis()/ 1000L) - 1000000000;
                long max = System.currentTimeMillis() / 1000L;
                final SearchMediaResponse response = instagram.getMediaEndpoint().search(5000 ,longitude, latitude);
//                if (response.getMediaList() != null) {
//                    for (Media media : response.getMediaList()) {
//                        logger.info("link: {}", media.getLink());
//                    }
                VK vk = new VK(RestAdapter.LogLevel.FULL);
                VkResponse resp = vk.getUsersEndpoint().search(latitude, longitude, 1000, 5000, 5.37);
                parseResultVk(resp);
                parseResult(response);
                result = 1; // Successful
            } catch (Exception e) {
                String msg = (e.getMessage()==null)?"Login failed!":e.getMessage();
                Log.i("error", msg);
                result = 0; //"Failed
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Lets update UI

            if (result == 1) {
                int counter = 0;
                for (String url : imagesList) {
                    ImageModel imageModel = new ImageModel();
                    imageModel.setName("Image " + counter);
                    imageModel.setUrl(url);
                    data.add(imageModel);
                    counter++;
                }
                mAdapter.setImageData(data);
            } else {
//				Toast.makeText(GridViewActivity.this, "Failed1 to fetch data!", Toast.LENGTH_SHORT).show();
            }

            //Hide progressbar
//			mProgressBar.setVisibility(View.GONE);
        }
    }

    /**
     * Parsing the feed results and get the list
     *
     * @param popular
     */
    private void parseResult(SearchMediaResponse popular) {
        if (popular.getMediaList() != null) {
            for (Media media : popular.getMediaList()) {
                Log.i("link:", media.getImages().getLowResolution().getUrl());
                imagesList.add(media.getImages().getStandardResolution().getUrl());
            }
        }
    }

    /**
     * Parsing the feed results and get the list
     *
     * @param popular
     */
    private  void parseResultVk(VkResponse popular) {
        if (popular.getResponse().getItems() != null) {
            for (Items media : popular.getResponse().getItems()) {
                Log.i("link:", media.getPhoto_130());
                imagesList.add(media.getPhoto_604());
            }
        }
    }


}
