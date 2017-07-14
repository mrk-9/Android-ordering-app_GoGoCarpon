package com.gogocarpon.gogocarpon._app.ui.adapters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.ImageLoader;
import com.gogocarpon.gogocarpon._app.models.Deal;


public class DealListAdapter extends BaseAdapter implements ImageLoader.ImageLoaderListener {

	private Activity activity;
	private ArrayList<Deal> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	
	public DealListAdapter(Activity a, ArrayList<Deal> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		imageLoader = new ImageLoader(activity.getApplicationContext());
		imageLoader = ImageLoader.getInstance(activity.getApplicationContext());
		imageLoader.imgLoaderListener = this;
	}

	public DealListAdapter(Activity a, ArrayList<Deal> d,ImageLoader iLoader) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = iLoader;
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public TextView name, price;
		public ImageView image;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {
			vi = inflater.inflate(
					R.layout.row_item_deal, null);
			holder = new ViewHolder();
			holder.name = (TextView) vi.findViewById(R.id.lblName);
			holder.price = (TextView) vi.findViewById(R.id.lblPrice);
			holder.image = (ImageView) vi.findViewById(R.id.imgProduct);

			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		Deal temp = (Deal) data.get(position);
		
		holder.name.setText(temp._name);
        String price = String.format("%.2f", temp._price_buy);
		holder.price.setText(activity.getString(R.string.em_price) 
				+ " : " + activity.getString(R.string.em_price_signal) + price);
		
		if(temp._pic_dir.trim().length() > 0)
		{
			// Load image
			String img_url = temp._pic_dir.replace(" ", "%20");
			holder.image.setTag(img_url);
			imageLoader.DisplayImage(img_url, activity, holder.image, temp);
		}
		return vi;
	}
	
	
	@Override
	public Bitmap onImageLoadFinished(File imageFile)
			throws FileNotFoundException {
		
		return BitmapFactory.decodeStream(new FileInputStream(imageFile));
		
//        //decode image size
//        BitmapFactory.Options o = new BitmapFactory.Options();
//        o.inJustDecodeBounds = true;
//        BitmapFactory.decodeStream(new FileInputStream(imageFile),null,o);
//
//        //Find the correct scale value. It should be the power of 2.
//        final int REQUIRED_SIZE = 76;
//        int width_tmp = o.outWidth, height_tmp = o.outHeight;
//        int scale = 1;
//        while(true){
//            if(width_tmp/2 < REQUIRED_SIZE || height_tmp/2 < REQUIRED_SIZE)
//                break;
//            width_tmp/=2;
//            height_tmp/=2;
//            scale *= 2;
//        }
//        
//        //decode with inSampleSize
//        BitmapFactory.Options o2 = new BitmapFactory.Options();
//        o2.inSampleSize=scale;
//        
////        Log.i("LOG APP", "XU ly trong ham onImageLoadFinished");
////        Log.i("LOG APP", "Paht : " + imageFile.getAbsolutePath());
////        Log.i("LOG APP", "scale : " + scale);
////        Log.i("LOG APP", "outHeight : " + o.outHeight);
////        Log.i("LOG APP", "outWidth : " + o.outWidth);
//        
//        return BitmapFactory.decodeStream(new FileInputStream(imageFile), null, o2);
	}

	@Override
	public Bitmap onDownloadFinished(File imageFile, Object data)
			throws FileNotFoundException {
		
		Bitmap result = BitmapFactory.decodeStream(new FileInputStream(imageFile));
		
		// TODO Auto-generated method stub
		Deal temp = (Deal) data;
		
//		Log.i("LOG APP", "temp._id = " + temp._id);
//		Log.i("LOG APP", "temp._name = " + temp._name);
//		Log.i("LOG APP", "onDownloadFinished = " + imageFile.toString());
		
		if (temp._hot_deal != 0) {
			
			int drawable_id = 0;
			
			if (temp._hot_deal == 1) {
				drawable_id = R.drawable.emu_ribbon_red;
			} else if (temp._hot_deal == 2) {
				drawable_id = R.drawable.emu_ribbon_blue;
			} else if (temp._hot_deal == 3) {
				drawable_id = R.drawable.emu_ribbon_yellow;
			}
			
			Bitmap bmp1 = BitmapFactory.decodeStream(new FileInputStream(imageFile));
			Bitmap bmp2 = BitmapFactory.decodeResource(activity.getResources(), drawable_id);
			
			result = this.combineImages(bmp1, bmp2);
			
			// Save override bitmap
			OutputStream os = null; 
		    try { 
		      os = new FileOutputStream(imageFile); 
		      result.compress(CompressFormat.PNG, 100, os);
		      Log.i("LOG APP", "Override fie = " + result.toString());
		      
		    } catch(IOException e) { 
		      Log.e("combineImages", "problem combining images", e); 
		    }
		    
		}
		
	    return result;
//		return this.combineImages(bmp1, bmp2);
//		return null;
	}

	private Bitmap combineImages(Bitmap c, Bitmap s) { 
		// can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom 
	    Bitmap cs = null; 
	 
	    cs = Bitmap.createBitmap(c.getWidth(), c.getHeight(), Bitmap.Config.ARGB_8888); 
	 
	    Canvas comboImage = new Canvas(cs); 
	 
	    // Process location ribbon image
	    float offsetWidth = c.getWidth() - s.getWidth();
	    
	    comboImage.drawBitmap(c, 0f, 0f, null); 
	    comboImage.drawBitmap(s, offsetWidth, 0f, null); 
	 
	    return cs; 
	  } 	
	
	
}