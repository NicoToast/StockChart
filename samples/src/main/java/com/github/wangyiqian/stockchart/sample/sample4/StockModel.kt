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

import com.github.wangyiqian.stockchart.entities.IKEntity

/**
 * @author lucas
 * @date 2021/11/15
 */
data class StockModel(
    val itemType: Int,
    val title: String? = null,
    val klineData: List<IKEntity>? = null
){
    companion object {
        const val ITEM_TYPE_STOCK_CHART = 10
        const val ITEM_TYPE_COMMON = 11

    }
}
