package com.lucas_dachman.organize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Lucas on 4/16/2016.
 */
public class MyRealmAdapter extends RealmBaseAdapter<Note> implements ListAdapter {

    Realm realm;

    public MyRealmAdapter(Context context, int resId,
                          RealmResults<Note> realmResults,
                          boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);

        realm = Realm.getDefaultInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Realm realm = Realm.getDefaultInstance();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.note_row_layout, parent, false);
        }
        TextView title_textView = (TextView) convertView.findViewById(R.id.note_title_preview);
        TextView text_textView = (TextView) convertView.findViewById(R.id.note_text_preview);

        Note note = realmResults.get(position);
        title_textView.setText(note.getTitle());
        if(note.getText().toString().length() <= 20) {
            text_textView.setText(note.getText().toString());
        }
        else {
            text_textView.setText(note.getText().substring(0, 20) + "...");
        }

        convertView.setTag(note);
        realm.close();
        return convertView;
    }

    public RealmResults<Note> getRealmResults() {
        return realmResults;
    }
}
