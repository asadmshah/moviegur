package com.asadmshah.moviegur.screens.movies_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asadmshah.moviegur.MoviegurApplication;
import com.asadmshah.moviegur.R;
import com.asadmshah.moviegur.utils.BundleBackedPresenterSaverRestorer;
import com.asadmshah.moviegur.utils.OnViewHolderClickListener;
import com.asadmshah.moviegur.utils.PresenterRestorer;

public class MoviesListScreenFragment extends Fragment implements MoviesListScreenContract.View, OnViewHolderClickListener {

    private static final String KEY_LIST_TYPE = "list_type";

    private MoviesListScreenContract.Presenter presenter = new MoviesListScreenPresenter();

    private MoviesListScreenAdapter moviesListScreenAdapter;
    private LinearLayoutManager viewListLayoutManager;
    private RecyclerView viewList;

    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (dy > 0 && viewListLayoutManager.findLastVisibleItemPosition() == viewListLayoutManager.getItemCount()-1) {
                presenter.onScrolledToBottom();
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PresenterRestorer restorer;
        if (savedInstanceState == null) {
            restorer = null;
        } else {
            restorer = new BundleBackedPresenterSaverRestorer(savedInstanceState);
        }
        MoviesListScreenListType type = (MoviesListScreenListType) getArguments().getSerializable(KEY_LIST_TYPE);
        presenter.onCreate(MoviegurApplication.getGraph(getActivity()), this, type, restorer);

        moviesListScreenAdapter = new MoviesListScreenAdapter();
        moviesListScreenAdapter.setOnViewHolderClickListener(this);
        moviesListScreenAdapter.setDataSource(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewList = (RecyclerView) inflater.inflate(R.layout.fragment_movies_list, container, false);
        viewList.setHasFixedSize(true);
        viewList.setLayoutManager(viewListLayoutManager = new LinearLayoutManager(getActivity()));
        viewList.setAdapter(moviesListScreenAdapter);
        viewList.addOnScrollListener(onScrollListener);
        return viewList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            presenter.onActivityCreated(null);
        } else {
            presenter.onActivityCreated(new BundleBackedPresenterSaverRestorer(savedInstanceState));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(new BundleBackedPresenterSaverRestorer(outState));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewList.removeOnScrollListener(onScrollListener);
        viewList.setAdapter(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        moviesListScreenAdapter.setOnViewHolderClickListener(null);
        moviesListScreenAdapter.setDataSource(null);

        presenter.onDestroy();
        presenter = null;
    }

    @Override
    public void notifyDataSetChanged() {
        moviesListScreenAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged(int positionStart, int additionCount) {
        moviesListScreenAdapter.notifyItemRangeInserted(positionStart, additionCount);
    }

    @Override
    public int getScrollPosition() {
        return viewListLayoutManager.findFirstVisibleItemPosition();
    }

    @Override
    public void setScrollPosition(int position) {
        viewList.scrollToPosition(position);
    }

    @Override
    public void showError(@StringRes int stringResId) {
        Snackbar.make(viewList, stringResId, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onViewHolderClicked(RecyclerView.ViewHolder viewHolder) {
        presenter.onListItemClicked(viewHolder.getAdapterPosition());
    }

    public static MoviesListScreenFragment newInstance(MoviesListScreenListType type) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_LIST_TYPE, type);
        MoviesListScreenFragment fragment = new MoviesListScreenFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
