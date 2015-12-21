package com.hsns.laor.ui.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hsns.laor.R;
import com.hsns.laor.databinding.ActivitySearchResultsBinding;
import com.hsns.laor.models.User;

public class SearchResultsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActivitySearchResultsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_results);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                /*Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.animator.push_right_in, R.animator.push_right_out);*/
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
/*        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.animator.push_left_in, R.animator.push_left_out);
        finish();*/
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            binding.setUser(new User(query, "dfa"));
            //Toast.makeText(getApplicationContext(), query , Toast.LENGTH_SHORT).show();
        } else if(intent.getExtras() != null) {
            String query = intent.getStringExtra("query");
            binding.setUser(new User(query, "dfa"));
        }
    }
}
