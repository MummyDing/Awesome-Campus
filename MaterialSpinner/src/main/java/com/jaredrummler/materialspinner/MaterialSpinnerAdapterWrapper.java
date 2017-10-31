/*
 * Copyright (C) 2016 Jared Rummler <jared.rummler@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.jaredrummler.materialspinner;

import android.content.Context;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

final class MaterialSpinnerAdapterWrapper extends MaterialSpinnerBaseAdapter {

  private final ListAdapter listAdapter;

  public MaterialSpinnerAdapterWrapper(Context context, ListAdapter toWrap) {
    super(context);
    listAdapter = toWrap;
  }

  @Override public int getCount() {
    return listAdapter.getCount() - 1;
  }

  @Override public Object getItem(int position) {
    if (position >= getSelectedIndex()) {
      return listAdapter.getItem(position + 1);
    } else {
      return listAdapter.getItem(position);
    }
  }

  @Override public Object get(int position) {
    return listAdapter.getItem(position);
  }

  @Override public List<Object> getItems() {
    List<Object> items = new ArrayList<>();
    for (int i = 0; i < getCount(); i++) {
      items.add(getItem(i));
    }
    return items;
  }

}