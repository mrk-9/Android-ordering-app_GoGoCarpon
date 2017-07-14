package com.gogocarpon.gogocarpon._app;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.models.Category;
import com.gogocarpon.gogocarpon._app.models.CategoryDatabaseHelper;


public class CategoryFragmentActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FragmentManager fm = getSupportFragmentManager();

		// Create the list fragment and add it as our sole content.
		if (fm.findFragmentById(android.R.id.content) == null) {
			CategoryFragment list = new CategoryFragment();
			fm.beginTransaction().add(android.R.id.content, list).commit();
		}
	}

	public static class CategoryFragment extends ListFragment {
		
        // Container Activity must implement this interface
    	protected CategoryListSelectedListener mCallback;
        public interface CategoryListSelectedListener {
            public void onCategorySelected(Category cat, int position);
        }
    	
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            
            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                mCallback = (CategoryListSelectedListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnHeadlineSelectedListener");
            }
        }

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			CatItem item = (CatItem) l.getAdapter().getItem(position);
			mCallback.onCategorySelected(item.objCat, position);
			//super.onListItemClick(l, v, position, id);
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.list, null);
		}

		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			CategoryAdapter adapter = new CategoryAdapter(getActivity());
			
			CategoryDatabaseHelper db = new CategoryDatabaseHelper(getActivity());

			Category obj = new Category();
			obj._id = "0";
			obj._name = getActivity().getString(R.string.em_cat_show_all);
			adapter.add(new CatItem(obj._name,obj,
					android.R.drawable.ic_menu_search));			

			try {

				Cursor c = db.getAll();
				int numRows = c.getCount();
				c.moveToFirst();
				for (int i = 0; i < numRows; ++i) {
					
					obj = new Category();

					obj._id = c.getString(c.getColumnIndex(CategoryDatabaseHelper.colID));
					obj._name = c.getString(c.getColumnIndex(CategoryDatabaseHelper.colName));

					obj._created_at = c.getString(c.getColumnIndex(CategoryDatabaseHelper.colCreatedAt));
					obj._updated_at = c.getString(c.getColumnIndex(CategoryDatabaseHelper.colUpdatedAt));

					obj._desc = c.getString(c.getColumnIndex(CategoryDatabaseHelper.colDest));
					
//					Log.i("LOG APP", "category name = " + obj._name);
					
					adapter.add(new CatItem(obj._name,obj,
							android.R.drawable.ic_menu_search));					
					
					c.moveToNext();
				}
				c.close();
				db.close();
			} catch (SQLException e) {
				Log.e("Exception on query", e.toString());
			}
			

			
			setListAdapter(adapter);
		}

		private class CatItem {
			public String tag;
			@SuppressWarnings("unused")
			public int iconRes;
			public Category objCat;

			public CatItem(String tag, Category cat, int iconRes) {
				this.tag = tag;
				this.iconRes = iconRes;
				this.objCat = cat;
			}
		}

		public class CategoryAdapter extends ArrayAdapter<CatItem> {

			public CategoryAdapter(Context context) {
				super(context, 0);
			}

			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = LayoutInflater.from(getContext()).inflate(
							R.layout.row_item_category, null);
				}
				// ImageView icon = (ImageView)
				// convertView.findViewById(R.id.row_icon);
				// icon.setImageResource(getItem(position).iconRes);
				TextView title = (TextView) convertView
						.findViewById(R.id.row_cat_title);
				title.setText(getItem(position).tag);

				return convertView;
			}

		}
	}

}
