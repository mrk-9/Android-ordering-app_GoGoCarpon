package com.gogocarpon.gogocarpon._app.ui.listeners;

import java.io.File;
import java.io.FileNotFoundException;

import android.graphics.Bitmap;

public interface ImageLoaderListener_BAK {
	public Bitmap onImageLoadFinished(File imageFile) throws FileNotFoundException;

}