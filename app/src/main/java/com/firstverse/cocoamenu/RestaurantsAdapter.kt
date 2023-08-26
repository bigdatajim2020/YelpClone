package com.firstverse.cocoamenu

import android.content.Context
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class RestaurantsAdapter(val context: Context, val restaurants: List<YelpRestaurant>) :
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(restaurant: YelpRestaurant){
            val tvName = itemView.findViewById<TextView>(R.id.tvName)
            tvName.text = restaurant.name
            val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
            ratingBar.rating=restaurant.rating.toFloat()
            val tvNumViews = itemView.findViewById<TextView>(R.id.tvNumReviews)
            tvNumViews.text = "S{restaurant.numReviews} Reviews"
            val tvAdder=itemView.findViewById<TextView>(R.id.tvAddress)
            tvAdder.text=restaurant.location.address
            val tvCategory=itemView.findViewById<TextView>(R.id.tvCategory)
            tvCategory.text = restaurant.categories[0].title
            val tvDistance = itemView.findViewById<TextView>(R.id.tvDistance)
            tvDistance.text = restaurant.displayDistance()
            val tvPrice = itemView.findViewById<TextView>(R.id.tvPrice)
            tvPrice.text = restaurant.price
            val imageView = itemView.findViewById<ImageView>(R.id.imageView)
            Glide.with(context).load(restaurant.imageUrl).apply(RequestOptions()
                    .transform(CenterCrop())
                    .transform(RoundedCorners(20)))
                    .into(imageView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false))
    }

    override fun getItemCount() = restaurants.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.bind(restaurant)
    }

}
