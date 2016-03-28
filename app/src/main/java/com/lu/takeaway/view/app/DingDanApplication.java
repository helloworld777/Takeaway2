package com.lu.takeaway.view.app;

import android.app.Application;

import com.lidroid.xutils.BitmapUtils;
import com.lu.takeaway.bean.OrderBean;
import com.lu.takeaway.bean.UserBean;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import util.Debug;
import util.FileUtils;

public class DingDanApplication extends Application {
	private static DingDanApplication danApplication;
	private UserBean currenUserBean;
	private BitmapUtils bitmapUtils;
	private List<OrderBean> selectedOrderBean;
	private int maxOrderId;
	private int maxUserId;
	@Override
	public void onCreate() {
		super.onCreate();
		danApplication=this;
		long start=System.currentTimeMillis();
		setBitmapUtils(new BitmapUtils(getApplicationContext(), FileUtils.imgPathPath()));
		setSelectedOrderBean(new ArrayList<OrderBean>());

		initImageLoader();

		Debug.d(this,"DingDanApplication.......................onCreate..............speed time:"+(System.currentTimeMillis()-start)/1000.0+" s");
	}

	private void initImageLoader() {
		File cacheDir = new File(FileUtils.imgPathPath());
		ImageLoaderConfiguration config  = new ImageLoaderConfiguration
				.Builder(this)
				.memoryCacheExtraOptions(480, 800) // maxwidth, max height，即保存的每个缓存文件的最大长宽
				.threadPoolSize(3)//线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY -2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2* 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
				.memoryCacheSize(2 * 1024 * 1024)
				.diskCacheSize(50 * 1024 * 1024)
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.diskCacheFileCount(100) //缓存的文件数量
				.diskCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(new BaseImageDownloader(this,5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
				.writeDebugLogs() // Remove for releaseapp
				.build();//开始构建
		ImageLoader.getInstance().init(config);


//		DisplayImageOptions options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.ic_stub)            //加载图片时的图片
//				.showImageForEmptyUri(R.drawable.ic_empty)         //没有图片资源时的默认图片
//				.showImageOnFail(R.drawable.ic_error)              //加载失败时的图片
//				.cacheInMemory(true)                               //启用内存缓存
//				.cacheOnDisk(true)                                 //启用外存缓存
//				.considerExifParams(true)                          //启用EXIF和JPEG图像格式
//				.displayer(new RoundedBitmapDisplayer(20))         //设置显示风格这里是圆角矩形
//				.build();
	}

	public static DingDanApplication getDefault(){
		return danApplication;
	}
	public UserBean getCurrenUserBean() {
		return currenUserBean;
	}
	public  boolean isLogin(){
		return  currenUserBean!=null;
	}
	public void setCurrenUserBean(UserBean currenUserBean) {
		this.currenUserBean = currenUserBean;
	}
	public BitmapUtils getBitmapUtils() {
		return bitmapUtils;
	}
	public void setBitmapUtils(BitmapUtils bitmapUtils) {
		this.bitmapUtils = bitmapUtils;
	}

	public List<OrderBean> getSelectedOrderBean() {
		return selectedOrderBean;
	}

	public void setSelectedOrderBean(List<OrderBean> selectedOrderBean) {
		this.selectedOrderBean = selectedOrderBean;
	}

	public int getMaxOrderId() {
		return maxOrderId;
	}

	public void setMaxOrderId(int maxOrderId) {
		this.maxOrderId = maxOrderId;
	}

	public int getMaxUserId() {
		return maxUserId;
	}

	public void setMaxUserId(int maxUserId) {
		this.maxUserId = maxUserId;
	}
}
