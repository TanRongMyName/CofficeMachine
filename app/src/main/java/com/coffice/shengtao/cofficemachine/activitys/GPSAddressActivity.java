package com.coffice.shengtao.cofficemachine.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.utils.LocationUtils;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;

import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class GPSAddressActivity extends BaseActivity {
    //https://blog.csdn.net/mingjiezuo/article/details/79755357
    //先获取当前的 经纬度 然后 在调用 百度地图 获取当前的位置地址明细
    private TextView tv;
    static final String[] LOCATIONGPS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE};

    private LocationManager lm;
    private boolean hasRequestPermission=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsaddress);
        tv=findViewById(R.id.showaddress);
    }
    /**
     * 检测GPS、位置权限是否开启
     */
    public void showGPSContacts() {
        lm = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
                    getLocation();//getLocation为定位方
        } else {
            ToastUtils.showShort(this, "系统检测到未开启GPS定位服务,请开启");
        }
    }



    /**
     * 获取具体位置的经纬度
     */
    private void getLocation() {
        // 获取位置管理服务
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) this.getSystemService(serviceName);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
        LogUtils.e("provider==="+provider);
        /**这段代码不需要深究，是locationManager.getLastKnownLocation(provider)自动生成的，不加会出错**/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
        updateLocation(location);
    }

    /**
     * 获取到当前位置的经纬度
     * @param location
     */
    private void updateLocation(final Location location) {
        final double[] latitude = new double[1];
        final double[] longitude = new double[1];
        if (location != null) {
            latitude[0] = location.getLatitude();
            longitude[0] = location.getLongitude();
            LogUtils.e("维度：" + latitude[0] + "\n经度" + longitude[0]);
            tv.setText("本地获取维度：" + latitude[0] + "\n经度" + longitude[0]);
        }else{
            final LocationUtils locationUtils= LocationUtils.getInstance(this);
            locationUtils.startMonitor();
            if(locationUtils.getLocation()!=null) {
                locationUtils.stopMonitor();
                latitude[0] = location.getLatitude();
                longitude[0] = location.getLongitude();
                LogUtils.e("维度：" + latitude[0] + "\n经度" + longitude[0]);
                tv.setText("百度地图获取维度：" + latitude[0] + "\n经度" + longitude[0]);
            }else {
                LogUtils.e("无法获取到位置信息");
            }
//            tv.postDelayed(new Runnable() {  view 定时去执行线程
//                @Override
//                public void run() {
//
//                }
//            },10000);
        }

    }
    public void ongetLocation(View view){
        showGPSContacts();
    }

    /**
     * 检查是否已被授权危险权限
     * @param permissions
     * @return
     */
    public boolean checkDangerousPermissions(Activity ac, String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(ac, permission)) {
                return false;
            }
        }
        return true;
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (checkDangerousPermissions(this, LOCATIONGPS)){
            LogUtils.e("有权限");
        }else {
            if (!hasRequestPermission){
                showScanCodeTip();
            }
        }
    }

    private void showScanCodeTip() {
        ScanCodeTipDialog dialog = new ScanCodeTipDialog();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                hasRequestPermission = true;
                requestDangerousPermissions(LOCATIONGPS, REQUEST_CODE_CAMERA);
            }
        });
        dialog.show(getSupportFragmentManager(), GPSAddressActivity.class.getSimpleName());
    }

    @Override
    public boolean handlePermissionResult(int requestCode, boolean granted) {
        if (requestCode == REQUEST_CODE_CAMERA){
            if (!granted){
                finish();
            }
            return true;
        }
        return super.handlePermissionResult(requestCode, granted);
    }
    public void onJump(View view){
        Intent intent=new Intent(GPSAddressActivity.this,GPSBaiDuActivity.class);
        startActivity(intent);
    }
}
