package com.example.simpleweather;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;


import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements
		AMapLocationListener {

	private TextView temp;
	private TextView status;
	private TextView direction;
	private Button city;
	private Button district;

	private LocationManagerProxy locationManagerProxy;

	private String defaultcity;
	private String defaultdistrict;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
		setContentView(R.layout.activity_main);

		temp = (TextView) findViewById(R.id.temp);
		status = (TextView) findViewById(R.id.status);
		direction = (TextView) findViewById(R.id.direction);
		city = (Button) findViewById(R.id.province);
		district = (Button) findViewById(R.id.city);
		// Intent intent = new Intent(MainActivity.this, Getlocation.class);
		// startActivity(intent);
		init();
	}

	// 初始化定位，只采用网络定位
	public void init() {
		locationManagerProxy = LocationManagerProxy.getInstance(this);
		locationManagerProxy.setGpsEnable(false);
		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
		// 在定位结束后，在合适的生命周期调用destroy()方法
		// 其中如果间隔时间为-1，则定位只定一次,
		// 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
		locationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);
//		注册监听
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(AMapLocation aMapLocation) {
		// TODO Auto-generated method stub
		if (aMapLocation != null
				&& aMapLocation.getAMapException().getErrorCode() == 0) {
			// 定位成功回调信息，设置相关消息
			if (aMapLocation.getProvince() != null) {
				defaultcity = aMapLocation.getCity();
				defaultdistrict = aMapLocation.getDistrict();
			} else {
				defaultcity = "null";
				defaultdistrict = "null";
			}
			city.setText(defaultcity);
			district.setText(defaultdistrict);

		} else {
			Log.e("AmapErr", "Location ERR:"
					+ aMapLocation.getAMapException().getErrorCode());
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		locationManagerProxy.removeUpdates(this);
		locationManagerProxy.destroy();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
