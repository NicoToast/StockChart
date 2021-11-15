/*
 * Copyright 2021 WangYiqian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

package com.github.wangyiqian.stockchart.sample.sample4

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @author lucas
 * @date 2021/11/15
 */
class CommonViewHolder(
    parent: ViewGroup,
    private val context: Context = parent.context
) : RecyclerView.ViewHolder(
    LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
) {
    private val tv: TextView = itemView.findViewById(android.R.id.text1)

    fun onBind(model: StockModel) {
        tv.text = model.title
    }
}