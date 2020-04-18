package com.example.covid_19;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.List;

public class MainActivity extends AppCompatActivity implements AysncResponse {

    private RecyclerView recyclerView;
    private RecyclerviewAdapter recyclerviewAdapter;
    private RecyclerTouchListener touchListener;
    private LinearLayoutManager layoutManager;
    public int mExpandedPosition=-1;
    public boolean isExpanded = false;
    public List<Task> taskList;
    private fetchPapers asyncTask;
    private String url;
    private ProgressBar progressBar;
    private ImageView imageView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.prog);
        //progressBar.setProgressDrawable(getDrawable(R.drawable.progressbar));

        url = "http://biomed-sanity.com/";

        progressBar.setVisibility(View.VISIBLE);

        asyncTask = new fetchPapers(url);
        asyncTask.delegate = this;
        //execute the async task
        asyncTask.execute();

        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                taskList.clear();
                recyclerviewAdapter.notifyDataSetChanged();
                asyncTask = new fetchPapers("http://biomed-sanity.com/");
                asyncTask.delegate = MainActivity.this;
                //execute the async task
                asyncTask.execute();
            }
        });

        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerviewAdapter = new RecyclerviewAdapter(this);

        //recyclerviewAdapter.setTaskList(taskList);
        recyclerView.setAdapter(recyclerviewAdapter);

        imageView = findViewById(R.id.search_button);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText query = findViewById(R.id.search);
                String q = query.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                taskList.clear();
                recyclerviewAdapter.notifyDataSetChanged();
                url = "http://biomed-sanity.com/search?q=" + q;
                Log.i("po",url);
                asyncTask = new fetchPapers(url);
                asyncTask.delegate = MainActivity.this;
                //execute the async task
                asyncTask.execute();
            }
        });

        touchListener = new RecyclerTouchListener(this,recyclerView);
        touchListener
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        //Toast.makeText(getApplicationContext(),taskList.get(position).getName(),Toast.LENGTH_SHORT).show();
                        RecyclerviewAdapter.MyViewHolder v = (RecyclerviewAdapter.MyViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

                        mExpandedPosition = isExpanded ? -1:position;
                        isExpanded = position==mExpandedPosition;
                        v.details.setVisibility(isExpanded?View.VISIBLE:View.GONE);
                        v.tvTaskDesc.setText(isExpanded?"":taskList.get(position).getDesc());
                        v.Taskdet.setVisibility(isExpanded?View.VISIBLE:View.GONE);
                        v.tvTaskName.setText(isExpanded?"":taskList.get(position).getName());
                        v.itemView.setActivated(isExpanded);
                        Log.i("pos", String.valueOf(position) + " "+ String.valueOf(isExpanded));
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {

                    }
                })
                .setSwipeOptionViews(R.id.delete_task,R.id.edit_task)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        switch (viewID){
                            case R.id.delete_task:
                                taskList.remove(position);
                                recyclerviewAdapter.setTaskList(taskList);
                                break;
                            case R.id.edit_task:
                                Toast.makeText(getApplicationContext(),"Edit Not Available",Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }
                });
    }

    @Override
    public void processFinish(List<Task> output){
        //Log.i("titles", output.toString());
        progressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        recyclerviewAdapter.setTaskList(output);
        recyclerviewAdapter.notifyDataSetChanged();
        taskList = output;
        Log.i("done", output.toString());

    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.addOnItemTouchListener(touchListener);
    }
}
