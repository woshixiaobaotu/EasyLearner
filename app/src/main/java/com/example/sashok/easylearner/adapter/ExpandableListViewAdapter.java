package com.example.sashok.easylearner.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sashok.easylearner.R;
import com.example.sashok.easylearner.activity.MainActivity;
import com.example.sashok.easylearner.fragment.AddWordDialogFragment;
import com.example.sashok.easylearner.model.Folder;
import com.example.sashok.easylearner.realm.RealmController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sashok on 25.9.17.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Activity _context;
   // header titles
    // child data in format of header title, child title
    private List<Folder> listFolders;

    public ExpandableListViewAdapter(Activity context, List<Folder> folders) {
        this._context=context;
        this.listFolders=folders;
    }

    @Override
    public int getGroupCount() {
        return listFolders.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listFolders.get(groupPosition).getWords().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listFolders.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listFolders.get(groupPosition).getWords().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = ((Folder)getGroup(groupPosition)).getName();
        ImageView indicator;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.folder_item_expandable_list_view, null);

        }
//        convertView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                return false;
//            }
//        });
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("TAG","lol");
//
//            }
//        });
        indicator=(ImageView)convertView.findViewById(R.id.toggleIcon);
        if (isExpanded) {
            indicator.setImageResource(R.drawable.ic_action_collapse);
        }else{
            indicator.setImageResource(R.drawable.ic_action_expanse);
        }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.headingTxt);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (listFolders.get(groupPosition).getWords().size()==0) return null;
        String headerTitle = ((Folder)getGroup(groupPosition)).getWords().get(childPosition).getEnWord();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.words_item_expendable_list_view, null);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddWordDialogFragment dialogFragment=new AddWordDialogFragment(_context,(MainActivity)_context,listFolders.get(groupPosition).getWords().get(childPosition));
                dialogFragment.getWindow().getAttributes().windowAnimations=R.style.RegistrationDialogAnimation;
                dialogFragment.setTitle(R.string.change_word);
                dialogFragment.show();
            }
        });
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.titleTxt);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void onCollapseFolder(int folderPos){

    }
    public void onExpanseFolder(int folderPos){
    }

    @Override
    public void notifyDataSetChanged() {

        super.notifyDataSetChanged();
    }
}
