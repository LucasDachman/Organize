package com.lucas_dachman.organize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmBaseAdapter;

/**
 * Created by Lucas on 4/16/2016.
 */
public class MyRealmAdapter extends RealmBaseAdapter<Note> {

    Realm realm;

    public MyRealmAdapter(Context context, OrderedRealmCollection<Note> data) {
        super(context, data, true);
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

        Note note = adapterData.get(position);
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

    public OrderedRealmCollection<Note> getAdapterData() {
        return adapterData;
    }
}
