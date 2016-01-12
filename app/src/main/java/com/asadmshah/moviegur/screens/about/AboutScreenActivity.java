package com.asadmshah.moviegur.screens.about;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.asadmshah.moviegur.MoviegurApplication;
import com.asadmshah.moviegur.R;

public class AboutScreenActivity extends AppCompatActivity implements AboutScreenContract.View {

    private final AboutScreenContract.Presenter presenter = new AboutScreenPresenter();

    private RecyclerView viewList;
    private LibrariesAdapter librariesAdapter = new LibrariesAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        viewList = (RecyclerView) findViewById(R.id.libraries_list);
        viewList.setLayoutManager(new LinearLayoutManager(this));

        presenter.onCreate(MoviegurApplication.getGraph(this), this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return presenter.onOptionsItemSelected(item.getItemId()) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void notifyDataSetChanged() {
        librariesAdapter.setDataSource(presenter);
        librariesAdapter.notifyDataSetChanged();
        viewList.setAdapter(librariesAdapter);
    }

    @Override
    public void navigateUpFromSameTask() {
        NavUtils.navigateUpFromSameTask(this);
    }
}
