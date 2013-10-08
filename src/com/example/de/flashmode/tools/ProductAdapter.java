package com.example.de.flashmode.tools;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.example.de.flashmode.tools.model.ProductModel;
import com.example.de.flashmode.tools.util.BitmapLruCache;

public class ProductAdapter extends BaseAdapter{
		private LayoutInflater layoutInflater;
		private List<ProductModel> productList = new ArrayList<ProductModel>();
		private RequestQueue requestQueue;
		
		public LayoutInflater getLayoutInflater() {
			return layoutInflater;
		}

		public RequestQueue getRequestQueue() {
			return requestQueue;
		}

		public void setRequestQueue(RequestQueue requestQueue) {
			this.requestQueue = requestQueue;
		}

		public void setLayoutInflater(LayoutInflater layoutInflater) {
			this.layoutInflater = layoutInflater;
		}
		
		public void setProductList(List<ProductModel> productList) {
			this.productList = productList;
		}

		public List<ProductModel> getProductList() {
			return productList;
		}

		@Override
		public int getCount() {
			return productList.size();
		}

		@Override
		public Object getItem(int i) {
			return productList.get(i);
		}

		@Override
		public long getItemId(int i) {
			return 0;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
			ViewHolder vh;
			if (view == null) {
				vh = new ViewHolder();
				view = this.getLayoutInflater().inflate(R.layout.product_listview, null);
				vh.image = (ImageView) view.findViewById(R.id.productImageLink);
				vh.name = (TextView) view.findViewById(R.id.productNameValue);
				vh.amount = (TextView) view.findViewById(R.id.productAmountValue);
				vh.date = (TextView) view.findViewById(R.id.createDateValue);
				view.setTag(vh);
			} else {
				vh = (ViewHolder) view.getTag();
			}

			ProductModel nm = this.getProductList().get(i);
			Log.i(this.getClass().getName(), "amount is. " + nm.getAmount());
			
//			ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapLruCache());
//			BitmapLruCache bitmapLru = new BitmapLruCache();
//			Bitmap bigmap = bitmapLru.get("http://http://faculty.cs.byu.edu/~jay/csta/scratch-day-2013/ScratchCat-Small.png");
//			Log.i(this.getClass().getName(), "image height: " + String.valueOf(bigmap.getHeight()));
			
			ImageLoader imageLoader = new ImageLoader(this.getRequestQueue(), new BitmapLruCache());
			ImageContainer imageContainer = imageLoader.get("http://faculty.cs.byu.edu/~jay/csta/scratch-day-2013/ScratchCat-Small.png", new ImageLoader.ImageListener() {
		        @Override
		        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

		        }

		        @Override
		        public void onErrorResponse(VolleyError error) {

		        }
		    });
			
			vh.image.setImageBitmap(imageContainer.getBitmap());
			vh.name.setText(nm.getName());
			//Attention: Cast is very important
			vh.amount.setText(String.valueOf(nm.getAmount()));
			vh.date.setText(nm.getCreationDate());
			return view;
		}

		class ViewHolder {
			ImageView image;
			TextView name;
			TextView amount;
			TextView date;
		}

	}
