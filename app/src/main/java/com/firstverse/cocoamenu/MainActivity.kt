package com.firstverse.cocoamenu

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firstverse.cocoamenu.ui.theme.CocoaMenuTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.yelp.com/v3/"
private const val TAG = "MainActivity"
private const val API_KEY = "GcAoGm-tKGZrIMKFBkJgP2RPjDOcE9N1WE2Iy4ytHQgfwBS_lFCTkpCHMWR-e_JA2qiew-uN7RaWtVKnu45tYhUlyW5pq3uVMvpCpZsDAqplRdywaG62MJ_Dt4fdZHYx"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val restaurants = mutableListOf<YelpRestaurant>()
        val adapter = RestaurantsAdapter(this, restaurants)

        val rvRestaurant = findViewById<RecyclerView>(R.id.rvResturants)

        rvRestaurant.adapter=adapter
        rvRestaurant.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchRestaurants("Bearer $API_KEY","Chinese", "Toronto").enqueue(object: Callback<YelpSearchResult> {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.i(TAG, "onResponse $response")
                val body = response.body()
                if (body==null){
                    Log.w(TAG, "Didn't receive valid response from Yelp API")
                    return
                }
                restaurants.addAll(body.restaurants)
                Log.i(TAG, "TOTAL: ${body.total}")
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.e(TAG, "onResponse $t")
            }

        })
    }
}