package com.zhangqi.autohomerefreshlistview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhangqi on 15/10/18.
 */
public class PullToFreshActivity extends Activity implements AutoHomeListView.OnAutoHomeRefreshListener {
    private AutoHomeListView mListView;
    private ArrayAdapter<String> mAdapter;
    private final static int REFRESH_COMPLETE = 0;
    private List<String> mDatas;
    
    private Handler mHandler = new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		switch (msg.what) {
			case REFRESH_COMPLETE:
				mListView.setOnRefreshComplete();
				mAdapter.notifyDataSetChanged();
				mListView.setSelection(0);
				break;

			default:
				break;
			}
    	};
    };
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autohome);
        String[] data = new String[]{"hello world1","hello world2","hello world3","hello world4","hello world5","hello world6","hello world7","hello world8","hello world9","hello world10","hello world11","hello world12","hello world13","hello world14","hello world15"};
        mDatas = new ArrayList<String>(Arrays.asList(data));
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mDatas);
        mListView = (AutoHomeListView) findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);
        mListView.setOnAutoHomeRefreshListener(this);
    }
	@Override
	public void onRefresh() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					mDatas.add(0, "new data");
					mHandler.sendEmptyMessage(REFRESH_COMPLETE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
}
