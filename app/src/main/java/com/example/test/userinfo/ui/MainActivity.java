package com.example.test.userinfo.ui;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.test.userinfo.R;
import com.example.test.userinfo.adapter.UserInfoAdapter;
import com.example.test.userinfo.model.network.UserInfoResponse;
import com.example.test.userinfo.presenter.Presenter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("Registered")
public class MainActivity extends AppCompatActivity implements View {

    private Presenter presenter;
    private UserInfoAdapter adapter;
    private ProgressDialog dialog;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<UserInfoResponse.Results> usersListAllItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        presenter = new Presenter(this, MainActivity.this);
        dialog = new ProgressDialog(MainActivity.this);
        usersListAllItems = new ArrayList<>();
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new UserInfoAdapter(recyclerView,  usersListAllItems, MainActivity.this);
        recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(() -> {
            presenter.getUsers(true);
        });

        adapter.setOnItemClickListener((pos, imageView) -> {
            UserInfoResponse.Results userInfo = usersListAllItems.get(pos);
            Pair[] pair = new Pair[1];
            pair[0] = new Pair<ImageView, String>(imageView, "imageViewShare");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pair);

            Intent intent = new Intent(this, UserInfoActivity.class);
            intent.putExtra("userObject", userInfo);
            startActivity(intent, options.toBundle());
        });
    }

    @OnClick(R.id.get_users)
    void onLoadFromNetworkClick(android.view.View view) {
        showProgressBar();
        presenter.getUsers(false);
    }

    @Override
    public void showErrorMsg(String msg) {
        dismissProgressBar();
        adapter.setLoaded();
        Toast.makeText(this, msg,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayUsersList(List<UserInfoResponse.Results> usersList) {
        if (usersList != null) {
            int position = usersListAllItems.size();
            for (UserInfoResponse.Results obj : usersList)
                usersListAllItems.add(presenter.getEndOfList(usersListAllItems), obj);
            dismissProgressBar();
            recyclerView.scrollToPosition(position);
            adapter.setLoaded();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void removeLastItem() {
        usersListAllItems.remove(usersListAllItems.size() - 1);
        adapter.notifyItemRemoved(usersListAllItems.size());
    }

    @Override
    public void addProgressItem() {
        usersListAllItems.add(presenter.getEndOfList(usersListAllItems), null);
        adapter.notifyItemInserted(usersListAllItems.size() - 1);
    }

    private void showProgressBar() {
        dialog.setMessage(getString(R.string.loading));
        dialog.show();
    }
    private void dismissProgressBar() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

}
