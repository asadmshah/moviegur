package com.asadmshah.moviegur.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asadmshah.moviegur.R;
import com.asadmshah.moviegur.models.Review;

import java.util.List;

public class ReviewsView extends LinearLayout {

    public ReviewsView(Context context) {
        super(context);
        init();
    }

    public ReviewsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReviewsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ReviewsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.widget_reviews_view, this);
    }

    public void setReviews(List<Review> reviews) {
        int n = Math.min(reviews.size(), getChildCount());
        for (int i = 0; i < n; i++) {
            ((TextView) ((ViewGroup) getChildAt(i)).getChildAt(0)).setText(reviews.get(i).author());
            ((TextView) ((ViewGroup) getChildAt(i)).getChildAt(1)).setText(reviews.get(i).content());
        }
    }

}
