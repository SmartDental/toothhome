package com.edu.thss.smartdental;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class HelpSlide extends Activity implements OnClickListener, OnPageChangeListener{
	private ViewPager mPager;

    /**
     * Pager适配器，用于提供ViewPager控件的具体页面。
     */
    private PagerAdapter mPagerAdapter;
    private ImageView[] dots;
    private int[] ids = {R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4};
    private int currentIndex; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	setContentView(R.layout.slidepage);
    	
    	List<View> views;   	 
    	
   	    LayoutInflater inflater = LayoutInflater.from(this);
   	    View view1 = inflater.inflate(R.layout.one, null);
   	    View view2 = inflater.inflate(R.layout.two, null);
   	    View view3 = inflater.inflate(R.layout.three, null);
   	    View view4 = inflater.inflate(R.layout.four, null);
   	    views = new ArrayList<View>();
   	    views.add(view1);
   	    views.add(view2);
   	    views.add(view3);
   	    views.add(view4);
   	    mPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new ViewPagerAdapter(views,HelpSlide.this);
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(this); 
        
        
        dots = new ImageView[views.size()];
        for (int i = 0; i < views.size(); i++) {
             dots[i] = (ImageView) findViewById(ids[i]);
        }
      
    }
    
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		for (int i = 0; i < ids.length; i++) {
            //若当前的界面是用户选中的界面，则点设置为选中状态，反之，则为没选中状态
             if (arg0 == i) {
                 dots[i].setImageResource(R.drawable.point_selected);
             } else {
                 dots[i].setImageResource(R.drawable.point_unselected);
             }
        }
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	public class ViewPagerAdapter extends PagerAdapter {
        
	   	 private List<View> views;
	   	 private Context context;
	   	 
	   	 /**
	   	 * 构造方法
	   	 * @param views  
	   	 * @param context
	   	 */
	   	 public ViewPagerAdapter(List<View> views, Context context) {
	   	
	   	     this.views = views;
	   	     this.context = context;
	   	 }
	   	/**
	   	 * 销毁View
	   	 */
	   	 @Override
	   	public void destroyItem(View container, int position, Object object) {
	   	
	   	 ((ViewPager) container).removeView(views.get(position));
	   	}
	       /**
	   	   * 实例化View
	   	*/
	   	 @Override
	   	 public Object instantiateItem(View container, int position) {
	   
	               ((ViewPager) container).addView(views.get(position));
	   	        return views.get(position);
	     }
	   	 @Override
	   	 public int getCount() {
	   	       return views.size();
	   	 }
	   	    
	   	 @Override
	   	 public boolean isViewFromObject(View arg0, Object arg1) {
	   	     return (arg0 == arg1);
	   	 }
	   	 
	}
}
