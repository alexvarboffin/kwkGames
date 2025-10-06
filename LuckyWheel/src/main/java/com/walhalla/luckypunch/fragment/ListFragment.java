package com.walhalla.luckypunch.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.walhalla.luckypunch.R;
import com.walhalla.luckypunch.databinding.FragmentListBinding;
import com.walhalla.luckypunch.databinding.NoteDialogBinding;
import com.walhalla.luckypunch.db.DatabaseManager;
import com.walhalla.luckypunch.ui.ProxyAdapter;
import com.walhalla.ui.DLog;

import java.util.ArrayList;
import java.util.List;

import in.goodiebag.carouselpicker.CarouselPicker;
import rubikstudio.library.model.LuckyItem;

public class ListFragment extends Fragment {

    private final int[] icons = {
            R.drawable.ic_item1,
            R.drawable.ic_item2,
            R.drawable.ic_item3,
            R.drawable.ic_item4,
            R.drawable.ic_item5,
            //R.drawable.ic_item1,
    };

    protected FragmentListBinding mBinding;
    protected ProxyAdapter adapter;
    protected DatabaseManager databaseManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentListBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    public void showActionsDialog(Context context, final int position) {
        CharSequence[] colors = new CharSequence[]{
                "Edit", "Delete"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.choose_option);
        builder.setItems(colors, (dialog, which) -> {
            if (which == 0) {
                insertOrUpdateItem(context, Events.UPDATE, position);
            } else {
                deleteProxy(position);
            }
        });
        builder.show();
    }

    private void deleteProxy(int position) {
        if (position < adapter.notesList.size()) {
            databaseManager.delete(adapter.notesList.get(position), result -> {
                if (result > 0) {
                    adapter.notesList.remove(position);
                    toggleEmptyProxys();
                    adapter.notifyItemRemoved(position);
                }
            });
        }
    }

    public void toggleEmptyProxys() {
//Only the original thread that created a view hierarchy can touch its views.
// you can check notesList.size() > 0
//        databaseManager.getProxyCount(data -> {
//            if (data > 0) {
//                mBinding.emptyNotesView.setVisibility(View.GONE);
//            } else {
//                mBinding.emptyNotesView.setVisibility(View.VISIBLE);
//            }
//        });
//        if (notesList.isEmpty()) {
//            mBinding.emptyNotesView.setVisibility(View.VISIBLE);
//            mBinding.recyclerView.setVisibility(View.GONE);
//        } else {

//            mBinding.emptyNotesView.setVisibility(View.GONE);
//            mBinding.recyclerView.setVisibility(View.VISIBLE);
//        }
    }

    public enum Events {
        UPDATE,
        INSERT
    }

    public void insertOrUpdateItem(Context context,
                                   final ListFragment.Events shouldUpdate, final int position) {

        LuckyItem luckyItem;
        if (position < 0) {
            luckyItem = new LuckyItem();
            luckyItem.icon = icons[0];
        } else {
            luckyItem = adapter.notesList.get(position);
        }


        //port.setFilters(new InputFilter[]{new InputFilterMinMax("1", "65535")});

        NoteDialogBinding mBind = NoteDialogBinding.inflate(LayoutInflater.from(context));
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(mBind.getRoot());
        CarouselPicker carouselPicker = mBind.carousel;


        // Case 1 : To populate the picker with images
        List<CarouselPicker.PickerItem> imageItems = new ArrayList<>();
        for (int i = 0; i < icons.length; i++) {
            imageItems.add(new CarouselPicker.DrawableItem(icons[i]));
        }

        //Create an adapter
        CarouselPicker.CarouselViewAdapter imageAdapter = new CarouselPicker.CarouselViewAdapter(context, imageItems, 0);
        //Set the adapter
        carouselPicker.setAdapter(imageAdapter);

        for (int i = 0; i < icons.length; i++) {
            if (icons[i] == luckyItem.icon) {
                carouselPicker.setCurrentItem(i);
                break;
            }
        }

        //Case 2 : To populate the picker with text
//        List<CarouselPicker.PickerItem> textItems = new ArrayList<>();
//        //20 here represents the textSize in dp, change it to the value you want.
//        textItems.add(new CarouselPicker.TextItem("hi", 20));
//        textItems.add(new CarouselPicker.TextItem("hi", 20));
//        textItems.add(new CarouselPicker.TextItem("hi", 20));
//        CarouselPicker.CarouselViewAdapter textAdapter = new CarouselPicker.CarouselViewAdapter(this, textItems, 0);
//        carouselPicker.setAdapter(textAdapter);

        //Case 3 : To populate the picker with both images and text
//        List<CarouselPicker.PickerItem> mixItems = new ArrayList<>();
//        mixItems.add(new CarouselPicker.DrawableItem(R.drawable.bulldog));
//        mixItems.add(new CarouselPicker.TextItem("hi", 20));
//        mixItems.add(new CarouselPicker.DrawableItem(R.drawable.butterfly));
//        mixItems.add(new CarouselPicker.TextItem("hi", 20));
//        CarouselPicker.CarouselViewAdapter mixAdapter = new CarouselPicker.CarouselViewAdapter(
//                context, mixItems, 0);
//        carouselPicker.setAdapter(mixAdapter);

        carouselPicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (luckyItem != null) {
                    luckyItem.icon = icons[position];
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBind.dialogTitle.setText(shouldUpdate == Events.INSERT ? getString(R.string.lbl_proxy_host_name)
                : getString(R.string.lbl_edit_note_title));

        if (shouldUpdate == Events.UPDATE && luckyItem != null) {
            mBind.host.setText(luckyItem.topText);
            mBind.port.setText("" + luckyItem.secondaryText);
            //description.setText(item.description);
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(
                        shouldUpdate == Events.UPDATE
                                ? getString(R.string.dialog_action_update)
                                : getString(R.string.action_save), (dialogBox, id) -> {

                        })
                .setNegativeButton("cancel",
                        (dialogBox, id) -> dialogBox.cancel());

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                if (noneValidInput(mBind.host.getText().toString(), mBind.port.getText().toString()
                        //        , description.getText().toString()
                )) {
                    return;
                } else {
                    alertDialog.dismiss();
                }


//                int p;
//                try {
//                    p = Integer.parseInt(port.getText().toString());
//                } catch (NumberFormatException e) {
//                    p = 0;
//                }


                luckyItem.topText = mBind.host.getText().toString();
                luckyItem.secondaryText = mBind.port.getText().toString();

                //, description.getText().toString()

                // check if user updating item
                if (shouldUpdate == Events.UPDATE) {
                    // update item by it's id
                    updateProxy(luckyItem, position);
                } else if (shouldUpdate == Events.INSERT) {
                    DLog.d("create new item");
                    createProxy(luckyItem);
                }
            }

            private boolean noneValidInput(String text1, String text2/*, String description*/) {

                if (TextUtils.isEmpty(text1)) {
                    Toast.makeText(getActivity(), "Enter text1!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (TextUtils.isEmpty(text2)) {
                    Toast.makeText(getActivity(), "Enter text2!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    private void createProxy(LuckyItem item) {
        // inserting item in db and getting
        // newly inserted item id
        databaseManager.insertAsc(item, id -> {
            databaseManager.getByIdAsc(id, data -> {
                // get the newly inserted item from db
                //LuckyItem data = databaseManager.getById(id);
                DLog.d("#==================" + data);
                if (data != null) {
                    adapter.notesList.add(0, data); // adding new item to array list at 0 position
                    toggleEmptyProxys();
                    adapter.notifyDataSetChanged();
                }
            });
        });
    }

    private void updateProxy(LuckyItem note, int position) {
        DLog.d("@@@@@@" + note.toString());
//        Proxy n = notesList.get(position);
//        // updating note text
//        n.setProxy(note);

        // updating note in db
        databaseManager.update(note, null);
        // refreshing the list
        adapter.notesList.set(position, note);
        toggleEmptyProxys();
        adapter.notifyItemChanged(position);
    }
}
