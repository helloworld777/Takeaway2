package com.lu.takeaway.util;

import android.R.integer;
import android.os.AsyncTask;

public class AsyncTaskUtil {

	private IAsyncTaskCallBack iAsyncTaskCallBack;

	public void setIAsyncTaskCallBack(IAsyncTaskCallBack iAsyncTaskCallBack) {
		this.iAsyncTaskCallBack = iAsyncTaskCallBack;
	}
	public AsyncTaskUtil(){
		
	}
	public AsyncTaskUtil(IAsyncTaskCallBack iAsyncTaskCallBack){
		this.iAsyncTaskCallBack=iAsyncTaskCallBack;
	}
	public void execute(String... params){
		new MyTask().execute(params);
	}
	class MyTask extends AsyncTask<String, integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			return iAsyncTaskCallBack.doInBackground(arg0);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			iAsyncTaskCallBack.onPostExecute(result);
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
		}
	}

	public interface IAsyncTaskCallBack {
		
		/**
		 * 在后台线程执行的方法
		 * @param arg0
		 * @return
		 */
		public Boolean doInBackground(String... arg0);

		public void onPostExecute(Boolean result);
	}
}
