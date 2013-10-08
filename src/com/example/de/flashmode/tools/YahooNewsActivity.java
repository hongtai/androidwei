package com.example.de.flashmode.tools;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

public class YahooNewsActivity extends ListActivity {
	private String TAG = this.getClass().getSimpleName();
	private ListView listView;
	ArrayList<NewsModel> newsList;
	private LayoutInflater layoutInflater;
	private VolleyAdapter volleyAdapter;
	private TextView textView;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_ordered_article);

		this.getImageFromGoogle(textView);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result_ordered_article, menu);
		return true;
	}

	private void getImageFromGoogle(final TextView txtDisplay) {
		RequestQueue queue = Volley.newRequestQueue(this);

		String url = "http://pipes.yahooapis.com/pipes/pipe.run?_id=giWz8Vc33BG6rQEQo_NLYQ&_render=json";

		layoutInflater = LayoutInflater.from(this);

		newsList = new ArrayList<NewsModel>();
		volleyAdapter = new VolleyAdapter();

		listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(this.volleyAdapter);

//		listView.setCacheColorHint(Color.TRANSPARENT);
		
//		listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
//	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//	            Log.v("Module Item Trigger", "Module item was triggered");
//	            Toast.makeText(getApplicationContext(), "triggering " + (TextView)view.findViewById(position), Toast.LENGTH_SHORT).show();
//	        }
//	    });

		progressDialog = ProgressDialog.show(this, "Please Wait...",
				"Please Wait...");

		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.i(TAG, response.toString());
						parseJSON(response);
						volleyAdapter.notifyDataSetChanged();
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
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);
		Intent intent = new Intent(this, WebViewPage.class);
		startActivity(intent);
	}
	
	private void parseJSON(JSONObject json) {
		try {
			JSONObject value = json.getJSONObject("value");
			JSONArray items = value.getJSONArray("items");
			for (int i = 0; i < items.length(); i++) {

				JSONObject item = items.getJSONObject(i);
				NewsModel nm = new NewsModel();
				nm.setTitle(item.optString("title"));
				nm.setDescription(item.optString("description"));
				nm.setLink(item.optString("link"));
				nm.setPubDate(item.optString("pubDate"));
				newsList.add(nm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	class NewsModel {
		private String title;
		private String link;
		private String description;
		private String pubDate;

		void setTitle(String title) {
			this.title = title;
		}

		void setLink(String link) {
			this.link = link;
		}

		void setDescription(String description) {
			this.description = description;
		}

		void setPubDate(String pubDate) {
			this.pubDate = pubDate;
		}

		String getLink() {
			return link;
		}

		String getDescription() {
			return description;
		}

		String getPubDate() {
			return pubDate;
		}

		String getTitle() {

			return title;
		}
	}
	
	class VolleyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return newsList.size();
		}

		@Override
		public Object getItem(int i) {
			return newsList.get(i);
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
				view = layoutInflater.inflate(R.layout.row_listview, null);
				vh.tvTitle = (TextView) view.findViewById(R.id.txtTitle);
				vh.tvDesc = (TextView) view.findViewById(R.id.txtDesc);
				vh.tvDate = (TextView) view.findViewById(R.id.txtDate);
				vh.tvLink = (TextView) view.findViewById(R.id.txtLink);
				view.setTag(vh);
			} else {
				vh = (ViewHolder) view.getTag();
			}

			NewsModel nm = newsList.get(i);
			vh.tvTitle.setText(nm.getTitle());
			vh.tvDesc.setText(nm.getDescription());
			vh.tvDate.setText(nm.getPubDate());
			vh.tvLink.setText(nm.getLink());
			return view;
		}

		class ViewHolder {
			TextView tvTitle;
			TextView tvDesc;
			TextView tvDate;
			TextView tvLink;
		}

	}
}
