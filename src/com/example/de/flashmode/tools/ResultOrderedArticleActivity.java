package com.example.de.flashmode.tools;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.de.flashmode.tools.model.ProductModel;

public class ResultOrderedArticleActivity extends Activity {
	private String TAG = this.getClass().getSimpleName();
	private ListView listView;
	ArrayList<ProductModel> productList;
	private LayoutInflater layoutInflater;
	private ProductAdapter productAdapter;
	private TextView textView;
	private ProgressDialog progressDialog;
	
	private String TARGET_URL = "http://www.flashmode.de/shop/android/fetch_product.php?mail=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_ordered_article);
		
		Intent intent = getIntent();
		String mail = intent.getStringExtra("customerEmail");
		Log.d(this.getClass().getName(), "mail address: " + mail);
		TARGET_URL = TARGET_URL + mail;
		
		this.getProductList(textView);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result_ordered_article, menu);
		return true;
	}

	private void getProductList(final TextView txtDisplay) {
		RequestQueue queue = Volley.newRequestQueue(this);

		layoutInflater = LayoutInflater.from(this);

		productList = new ArrayList<ProductModel>();
		//TODO: add constructor in ProductAdapter with layoutInflater as parameter
		productAdapter = new ProductAdapter();
		productAdapter.setRequestQueue(queue);
		productAdapter.setLayoutInflater(layoutInflater);
		productAdapter.setProductList(productList);

		listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(this.productAdapter);

		progressDialog = ProgressDialog.show(this, "Please Wait...",
				"Please Wait...");

		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.GET, TARGET_URL, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.i(TAG, response.toString());
						parseJSON(response);
						productAdapter.notifyDataSetChanged();
						progressDialog.dismiss();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i(TAG, error.getMessage());
					}
				});

		queue.add(jsObjRequest);
	}
	
	private void parseJSON(JSONObject json) {
		try {
			JSONObject value = json.getJSONObject("value");
			JSONArray items = value.getJSONArray("items");
			for (int i = 0; i < items.length(); i++) {

				JSONObject item = items.getJSONObject(i);
				ProductModel product = new ProductModel();
				product.setImageLink(item.optString("image"));
				Log.i(TAG, "image " + item.optString("image"));
				product.setName(item.optString("name"));
				product.setAmount(item.optInt("amount"));
				product.setCreationDate(item.optString("date"));
//				product.setImageLink(item.optString("http://www.flashmode.de/shop/images/ebag4327A.jpg"));
//				product.setName("Abend Tasche");
//				product.setAmount(3);
//				product.setCreationDate("2013-05-12");
				productList.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
