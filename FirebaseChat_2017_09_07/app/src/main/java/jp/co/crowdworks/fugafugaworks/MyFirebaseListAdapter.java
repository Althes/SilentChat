/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.co.crowdworks.fugafugaworks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public abstract class MyFirebaseListAdapter<T> extends BaseAdapter {

    public class SampleListAdapter extends ArrayAdapter<Friend> {
        private int mResource;
        private List<Friend> mItems;
        private LayoutInflater mInflater;

        public SampleListAdapter(Context context, int resource, List<Friend> items) {
            super(context, resource, items);

            mResource = resource;
            mItems = items;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView != null) {
                view = convertView;
            } else {
                view = mInflater.inflate(mResource, null);
            }

            Friend item = mItems.get(position);

            //ListView thumbnail = (ListView) view.findViewById(R.id.listview_friend);
            //thumbnail.setImageBitmap(item;());

            TextView title = (TextView) view.findViewById(R.id.textlist);
            title.setText(item.getFriendId());

            return view;
        }
    }
}
