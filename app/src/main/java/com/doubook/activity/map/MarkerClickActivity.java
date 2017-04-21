package com.doubook.activity.map;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.Projection;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.doubook.R;
import com.doubook.bean.User;
import com.doubook.utiltools.LogsUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * AMapV2地图中简单介绍一些Marker的用法.
 */
public class MarkerClickActivity extends Activity implements View.OnClickListener,LocationSource,
		AMapLocationListener {
	private MarkerOptions markerOption;
    private MarkerOptions markerOption2;
	private AMap aMap;
	private MapView mapView;
	private OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;

	private LatLng latlng = new LatLng(36.682684, 117.14706);
	//117.129669,36.681526
    private LatLng latlng2 = new LatLng(36.681526, 117.129669);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marker_activity);
		/*
		 * 设置离线地图存储目录，在下载离线地图或初始化地图设置; 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
		 * 则需要在离线地图下载和使用地图页面都进行路径设置
		 */
		// Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
//		 MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState); // 此方法必须重写
		init();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		Button clearMap = (Button) findViewById(R.id.clearMap);
		clearMap.setOnClickListener(this);
		Button resetMap = (Button) findViewById(R.id.resetMap);
		resetMap.setOnClickListener(this);
		if (aMap == null) {
			aMap = mapView.getMap();
			aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
			setUpMap();
		}
	}

	Marker curShowWindowMarker;

	private void setUpMap() {
		// 自定义系统定位小蓝点
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.gps_point));// 设置小蓝点的图标
		myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
//		 myLocationStyle.anchor(36,117);//设置小蓝点的锚点

		myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// aMap.setMyLocationType()
		aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				if (aMap != null) {
					jumpPoint(marker);
				}
                marker.showInfoWindow();
				Toast.makeText(MarkerClickActivity.this, "您点击了Marker", Toast.LENGTH_LONG).show();
				return true;
			}
		});
//		addMarkersToMap();// 往地图上添加marker



//		aMap.setOnMarkerClickListener(new OnMarkerClickListener() {
//			@Override
//			public boolean onMarkerClick(Marker marker) {
//				System.out.println("-->sssss");
//                curShowWindowMarker = marker;//保存当前点击的Marker，以便点击地图其他地方设置InfoWindow消失
//				return false;//返回true，消费此事件。
//			}
//		});
        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
//                marker.showInfoWindow();
                Toast.makeText(MarkerClickActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
                System.out.println("-->2222222"+marker.getTitle());
            }
        });
//        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
//            @Override
//            public void onTouch(MotionEvent motionEvent) {
//                if (aMap != null && curShowWindowMarker != null) {
//                    if (curShowWindowMarker.isInfoWindowShown()){
//                        curShowWindowMarker.hideInfoWindow();
//                    }
//                }
//            }
//        });
	}


	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	/**
	 * 在地图上添加marker
	 */
	private void addMarkersToMap() {


	}

	/**
	 * marker点击时跳动一下
	 */
	public void jumpPoint(final Marker marker) {
		final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        final LatLng markerLatlng = marker.getPosition();
        Point markerPoint = proj.toScreenLocation(markerLatlng);
        markerPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(markerPoint);
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * markerLatlng.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * markerLatlng.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/**
		 * 清空地图上所有已经标注的marker
		 */
		case R.id.clearMap:
			if (aMap != null) {
				aMap.clear();
			}
			break;
		/**
		 * 重新标注所有的marker
		 */
		case R.id.resetMap:
			if (aMap != null) {
				aMap.clear();
				addMarkersToMap();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null
					&& amapLocation.getErrorCode() == 0) {
				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
				//查询周边人
				BmobQuery<User> userBmobQuery = new BmobQuery<>();
				userBmobQuery.addWhereNear("loc",new BmobGeoPoint(amapLocation.getLongitude(),amapLocation.getLatitude()));
				userBmobQuery.setLimit(10);    //获取最接近用户地点的10条数据
				userBmobQuery.findObjects(new FindListener<User>() {
					@Override
					public void done(List<User> object, BmobException e) {
						if(e==null){
							LogsUtils.e("查询成功：共" + object.size() + "条数据。");
							for (User user :object) {
								markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
										.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
										.position(new LatLng(user.getLoc().getLatitude(),user.getLoc().getLongitude()))
										.title(user.getUsername())
										.snippet(user.getDesc())
										.draggable(true)
										.icon(BitmapDescriptorFactory.fromResource(R.drawable.a1));//设置图标;
								aMap.addMarker(markerOption);
							}
						}else{
							LogsUtils.e("查询失败：" + e.getMessage());
						}
					}
				});
			} else {
				String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
				Log.e("AmapErr",errText);
			}
		}
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener onLocationChangedListener) {
		mListener = onLocationChangedListener;
		if (mlocationClient == null) {
			mlocationClient = new AMapLocationClient(this);
			mLocationOption = new AMapLocationClientOption();
			//设置定位监听
			mlocationClient.setLocationListener(this);
			//设置是否只定位一次,默认为false
			mLocationOption.setOnceLocation(true);
			//设置是否允许模拟位置,默认为false，不允许模拟位置
			mLocationOption.setMockEnable(true);
			//设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
			//设置定位参数
			mlocationClient.setLocationOption(mLocationOption);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
			mlocationClient.startLocation();
		}
	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}
}
