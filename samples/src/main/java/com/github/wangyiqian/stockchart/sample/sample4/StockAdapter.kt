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

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.wangyiqian.stockchart.sample.sample4.StockModel.Companion.ITEM_TYPE_STOCK_CHART

/**
 * @author lucas
 * @date 2021/11/15
 */
class StockAdapter : ListAdapter<StockModel, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<StockModel>() {
        override fun areItemsTheSame(oldItem: StockModel, newItem: StockModel) =
            oldItem.itemType == oldItem.itemType

        override fun areContentsTheSame(oldItem: StockModel, newItem: StockModel) =
            oldItem == newItem
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ITEM_TYPE_STOCK_CHART -> StockChartViewHolder(parent)
            else -> CommonViewHolder(parent)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StockChartViewHolder -> holder.onBind(getItem(position))
            is CommonViewHolder -> holder.onBind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }
}