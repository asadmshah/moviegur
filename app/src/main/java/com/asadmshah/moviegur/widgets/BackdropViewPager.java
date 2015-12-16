package com.asadmshah.moviegur.widgets;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.asadmshah.moviegur.R;
import com.asadmshah.moviegur.models.BackdropPath;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class BackdropViewPager extends FrameLayout {

    private ViewPager viewPager;

    public BackdropViewPager(Context context) {
        super(context);
        init();
    }

    public BackdropViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BackdropViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BackdropViewPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.widget_backdrop_view_pager, this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    public void setBackdropPaths(List<BackdropPath> backdropPaths) {
        viewPager.setAdapter(new Adapter(backdropPaths));
    }

    private static final class Adapter extends PagerAdapter {

        private final List<BackdropPath> backdropPaths;

        private Adapter(List<BackdropPath> backdropPaths) {
            this.backdropPaths = backdropPaths;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));

            RequestManager rm = Glide.with(container.getContext());
            BackdropPath backdrop = backdropPaths.get(position);
            rm.load(backdrop)
                    .thumbnail(rm.load(backdrop).thumbnail(0.15f).centerCrop())
                    .centerCrop()
                    .dontAnimate()
                    .into(view);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return backdropPaths.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

}
