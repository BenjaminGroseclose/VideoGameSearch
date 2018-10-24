package bgroseclose.videogamesearch.Activity;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import bgroseclose.videogamesearch.Adapters.GameListAdapter;
import bgroseclose.videogamesearch.Components.DaggerIGameListComponent;
import bgroseclose.videogamesearch.Components.IGameListComponent;
import bgroseclose.videogamesearch.Models.SearchResult;
import bgroseclose.videogamesearch.Modules.ContextModule;
import bgroseclose.videogamesearch.R;
import bgroseclose.videogamesearch.Services.IGameListService;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edit_search)
    EditText mEditSearch;
    @BindView(R.id.btn_search)
    ImageButton mBtnSearch;
    @BindView(R.id.game_recycler_view)
    RecyclerView mGameRecyclerView;
    @BindView(R.id.main_edit_search)
    EditText mEditMainSearch;
    @BindView(R.id.main_btn_search)
    ImageButton mBtnMainSearch;
    @BindView(R.id.main_search_relative_layout)
    RelativeLayout mMainRelativeLayout;
    @BindView(R.id.recycler_view_relative_layout)
    RelativeLayout mRecyclerLayoutRelativeLayout;
    @BindView(R.id.game_list_refresh)
    SwipeRefreshLayout mGameListRefresh;
    IGameListComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        component = DaggerIGameListComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch();
            }
        });

        mGameListRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearList();
                doSearch();
            }
        });

        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    doSearch();
                    return true;
                }
                return false;
            }
        });

        mEditMainSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    doMainSearch();
                    return true;
                }
                return false;
            }
        });

        mBtnMainSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doMainSearch();
            }
        });
    }

    private void doMainSearch() {
        String search = mEditMainSearch.getText().toString();
        if(!search.isEmpty()) {
            mMainRelativeLayout.setVisibility(View.GONE);
            mRecyclerLayoutRelativeLayout.setVisibility(View.VISIBLE);
            setAdapterWithRetrofit(search);
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.blank_search_msg), Toast.LENGTH_LONG).show();
        }
    }

    private void doSearch() {
        String search = mEditSearch.getText().toString();
        if(!search.isEmpty()) {
            setAdapterWithRetrofit(search);
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.blank_search_msg), Toast.LENGTH_LONG).show();
        }
    }

    private void setAdapterWithRetrofit(String search) {
        IGameListService service = component.getGameListService();
        final Picasso picasso = component.getPicasso();
        Call<SearchResult> call = service.getSearchResult(getString(R.string.api_key),
                getString(R.string.json),
                "\"".concat(search).concat("\""),
                getString(R.string.game));
        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(final Call<SearchResult> call, Response<SearchResult> response) {
                SearchResult result = response.body();
                if(result.getSearchCount() != 0) {
                    GameListAdapter adapter = new GameListAdapter(MainActivity.this, result, picasso);
                    mGameRecyclerView.setAdapter(adapter);
                    mGameRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setIcon(R.drawable.ic_info)
                            .setTitle(getString(R.string.no_games_found_title))
                            .setMessage(getString(R.string.no_games_found_msg))
                            .setNeutralButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    clearList();
                                }
                            })
                            .create();
                    dialog.show();
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.ic_error)
                        .setTitle(getString(R.string.api_fail_title))
                        .setMessage(getString(R.string.api_fail_msg))
                        .setNeutralButton(getString(R.string.close), null)
                        .create();
                dialog.show();
            }
        });
    }

    private void clearList() {
        mGameRecyclerView.setAdapter(null);
        mGameRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_toolbar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.info) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_info)
                    .setTitle(getString(R.string.info_title))
                    .setMessage(getString(R.string.info_msg))
                    .setNeutralButton(getString(R.string.close), null)
                    .create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
