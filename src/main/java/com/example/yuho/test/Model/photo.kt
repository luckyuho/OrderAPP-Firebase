//package com.example.yuho.test.Model
//
//import com.google.gson.annotations.SerializedName
//import android.annotation.SuppressLint
//import android.content.Context
//import android.support.design.widget.Snackbar
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import com.example.yuho.test.R
//import com.squareup.picasso.Picasso
//import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.product_row_item.view.*
//
//data class Product(
//    @SerializedName("description")
//    var description: String? = null,
//
//    @SerializedName("id")
//    var id: Int? = null,
//
//    @SerializedName("name")
//    var name: String? = null,
//
//    @SerializedName("price")
//    var price: String? = null,
//
//    @SerializedName("photos")
//    var photos: List<Photo> = arrayListOf()
//)
//
//data class Photo(
//    @SerializedName("filename")
//    var filename: String? = null
//)
//
//class ProductAdapter(var context: Context, var products: List<Product> = arrayListOf()) :
//    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
//    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductAdapter.ViewHolder {
//        // The layout design used for each list item
//        val view = LayoutInflater.from(context).inflate(R.layout.product_row_item, null)
//        return ViewHolder(view)
//
//    }
//    // This returns the size of the list.
//    override fun getItemCount(): Int = products.size
//
//    override fun onBindViewHolder(viewHolder: ProductAdapter.ViewHolder, position: Int) {
//        //we simply call the `bindProduct` function here
//        viewHolder.bindProduct(products[position])
//    }
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//
//        // This displays the product information for each item
//        fun bindProduct(product: Product) {
//
//            itemView.product_name.text = product.name
//            itemView.product_price.text = "$${product.price.toString()}"
//            Picasso.get().load(product.photos[0].filename).fit().into(itemView.product_image)
//        }
//
//    }
//
//}