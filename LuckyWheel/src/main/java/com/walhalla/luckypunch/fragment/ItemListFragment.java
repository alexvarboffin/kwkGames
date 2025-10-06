package com.walhalla.luckypunch.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.walhalla.luckypunch.R;

import com.walhalla.luckypunch.SwipeHelper;
import com.walhalla.luckypunch.db.DatabaseManager;
import com.walhalla.luckypunch.ui.ProxyAdapter;
import com.walhalla.luckypunch.ui.RecyclerTouchListener;
import com.walhalla.boilerplate.domain.executor.impl.BackgroundExecutor;
import com.walhalla.boilerplate.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;

import rubikstudio.library.model.LuckyItem;

public class ItemListFragment extends ListFragment
        implements DatabaseManager.DatabaseCallBack<List<LuckyItem>> {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.fab.setOnClickListener(view0 ->
                insertOrUpdateItem(getActivity(), Events.INSERT, -1));

        adapter = new ProxyAdapter(getContext(), new ArrayList<>());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mBinding.recyclerView.setLayoutManager(mLayoutManager);
        mBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new MyDividerItemDecoration(this,
//                LinearLayoutManager.VERTICAL, 16));


        mBinding.recyclerView.setAdapter(adapter);
//        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//            private final ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.background));
//
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                //adapter.showMenu(viewHolder.getAdapterPosition());
//            }
//
//            @Override
//            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//
//                View itemView = viewHolder.itemView;
//
//                if (dX > 0) {
//                    background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX), itemView.getBottom());
//                } else if (dX < 0) {
//                    background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
//                } else {
//                    background.setBounds(0, 0, 0, 0);
//                }
//
//                background.draw(c);
//            }
//        };
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
//        itemTouchHelper.attachToRecyclerView(mBinding.recyclerView);

        SwipeHelper aa = new SwipeHelper(getActivity(), mBinding.recyclerView, false) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {

                underlayButtons.add(new UnderlayButton("Archive",
                        AppCompatResources.getDrawable(
                                getActivity(),
                                R.drawable.ic_baseline_more_horiz_24//ic_archive_black_24dp
                        ),
                        Color.parseColor("#000000"), Color.parseColor("#ffffff"), new UnderlayButtonClickListener() {
                    @Override
                    public void onClick(int pos) {
                        Toast.makeText(getActivity(),"Delete clicked at " + pos, Toast.LENGTH_SHORT).show();
                        //adapter.modelList.removeAt(pos);
                        adapter.notifyItemRemoved(pos);
                    }
                }));

                underlayButtons.add(new UnderlayButton("Flag",
                        AppCompatResources.getDrawable(
                                getActivity(),
                                R.drawable.ic_baseline_more_horiz_24//ic_flag_black_24dp
                        ),
                        Color.parseColor("#FF0000"), Color.parseColor("#ffffff"), new UnderlayButtonClickListener() {
                    @Override
                    public void onClick(int pos) {
                        Toast.makeText(getActivity(),"Flag Button Clicked at Position: " + pos, Toast.LENGTH_SHORT).show();
                        adapter.notifyItemChanged(pos);
                    }
                }));

                underlayButtons.add(new UnderlayButton("More",
                        AppCompatResources.getDrawable(
                                getActivity(),
                                R.drawable.ic_baseline_more_horiz_24
                        ),
                        Color.parseColor("#00FF00"), Color.parseColor("#ffffff"), new UnderlayButtonClickListener() {
                    @Override
                    public void onClick(int pos) {
                        Toast.makeText(getActivity(),"More Button CLicked at Position: " + pos, Toast.LENGTH_SHORT).show();
                        adapter.notifyItemChanged(pos);
                    }
                }));
            }
        };

        mBinding.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                mBinding.recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, final int position) {
                //if (!drawer.isDrawerOpen(GravityCompat.START)) {
                LuckyItem proxy = adapter.notesList.get(position);
                showActionsDialog(getContext(), position);
                //}
            }

            @Override
            public void onLongClick(View view, int position) {
                //if (!drawer.isDrawerOpen(GravityCompat.START)) {
                //showActionsDialog(getContext(), position);
                //}
            }
        }));

        databaseManager = new DatabaseManager(BackgroundExecutor.getInstance(), MainThreadImpl.getInstance());

    }

    private void changeProxySettings(LuckyItem proxy) {
    }

    @Override
    public void returnData(List<LuckyItem> data) {
        adapter.swap(data);
        toggleEmptyProxys();
        //updateProxyStatus();
    }

    @Override
    public void onResume() {
        super.onResume();
        databaseManager.loadNotesList0(this);
        //demo();
    }

    private void demo() {
        List<LuckyItem> objects = new ArrayList<>();
        objects.add(new LuckyItem("1", "2"));
        toggleEmptyProxys();
        adapter.swap(objects);
    }
}
