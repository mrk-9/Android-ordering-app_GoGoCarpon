package com.gogocarpon.gogocarpon._app.baseclass;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Environment;

public class Utils {
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	// TODO : Convert Activity  <-> Content
	public static void ShowNotification(Context base, String title,
			String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(base);
		//AlertDialog alertDialog = new AlertDialog.Builder(base).create();
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
		builder.show();
	}

	public static void ShowNotification(Context base, String title,
			String message, DialogInterface.OnClickListener lis) {
        AlertDialog.Builder builder = new AlertDialog.Builder(base);
		//AlertDialog alertDialog = new AlertDialog.Builder(base).create();
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("OK", lis);
		builder.show();
	}

	public static String md5(String in) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(in.getBytes());
			byte[] a = digest.digest();
			int len = a.length;
			StringBuilder sb = new StringBuilder(len << 1);
			for (int i = 0; i < len; i++) {
				sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
				sb.append(Character.forDigit(a[i] & 0x0f, 16));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

		int width = bm.getWidth();
		int height = bm.getHeight();

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// create a matrix for the manipulation
		Matrix matrix = new Matrix();

		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);

		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
				matrix, false);

		return resizedBitmap;
	}

	public static File createTempFile(Context context, String filename) {

		// it will return /sdcard/[PackageName]/[filename]
		final File path = new File(Environment.getExternalStorageDirectory(),
				context.getPackageName());
		if (!path.exists()) {
			path.mkdir();
		}

		return new File(path, filename);
	}

	public static long totalSecondToNow(String beginDate, String formatDate) {

		try {

			SimpleDateFormat sdf = new SimpleDateFormat(formatDate,Locale.ENGLISH);
			Date dateObj = sdf.parse(beginDate);
			Date now = new Date();
			// Convert from millis second to second
			return (now.getTime() - dateObj.getTime()) / 1000;

		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}

	}

	public static long totalSecondBetween2Date(String beginDate,
			String endDate, String formatDate) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formatDate,Locale.ENGLISH);
			Date beginDateObj = sdf.parse(beginDate);
			Date endDateObj = sdf.parse(endDate);
			return (endDateObj.getTime() - beginDateObj.getTime()) / 1000;

		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}

	}

	public static String decimalFormat(String pattern, double value) {
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		return myFormatter.format(value);
	}

	// Custome format price
	public static String decimalFormatPrice(double value) {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
		otherSymbols.setDecimalSeparator(AppConfig.DECIMAL_SEPARATOR_FORMAT);
		otherSymbols.setGroupingSeparator(AppConfig.DECIMAL_GROUPING_FORMAT); 
		DecimalFormat df = new DecimalFormat(AppConfig.DECIMAL_NUMBER_FORMAT, otherSymbols);		
		return df.format(value);		
	}

	public static boolean isEmail(String email) {
		boolean isValid = false;

		// Initialize reg ex for email.
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		// Make the comparison case-insensitive.
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isNumeric(String number) {
		boolean isValid = false;

		/*
		 * Number: A numeric value will have following format: ^[-+]?: Starts
		 * with an optional "+" or "-" sign. [0-9]*: May have one or more
		 * digits. \\.? : May contain an optional "." (decimal point) character.
		 * [0-9]+$ : ends with numeric digit.
		 */

		// Initialize reg ex for numeric data.
		String expression = "^[-+]?[0-9]*\\.?[0-9]+$";
		CharSequence inputStr = number;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isEmpty(String string) {
		return (string.trim().equals("") || string == null);
	}

}
