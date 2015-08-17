package com.seeyuan.logistics.util;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.view.View;

import com.baidu.mapapi.map.MapController;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class BMapUtil {

	/**
	 * 从view 得到图片
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}

	public static void fitPoints(ArrayList<GeoPoint> points,
			MapController mMapController) {
		// set min and max for two points
		int nwLat = -90 * 1000000;
		int nwLng = 180 * 1000000;
		int seLat = 90 * 1000000;
		int seLng = -180 * 1000000;
		// find bounding lats and lngs
		for (GeoPoint point : points) {
			nwLat = Math.max(nwLat, point.getLatitudeE6());
			nwLng = Math.min(nwLng, point.getLongitudeE6());
			seLat = Math.min(seLat, point.getLatitudeE6());
			seLng = Math.max(seLng, point.getLongitudeE6());
		}
		GeoPoint center = new GeoPoint((nwLat + seLat) / 2, (nwLng + seLng) / 2);
		// add padding in each direction
		int spanLatDelta = (int) (Math.abs(nwLat - seLat) * 1.1);
		int spanLngDelta = (int) (Math.abs(seLng - nwLng) * 1.1);

		// fit map to points
		mMapController.setCenter(center);
		mMapController.zoomToSpan(spanLatDelta, spanLngDelta);
	}
}
