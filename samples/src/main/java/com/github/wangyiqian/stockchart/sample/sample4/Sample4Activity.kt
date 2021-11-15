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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.wangyiqian.stockchart.entities.IKEntity
import com.github.wangyiqian.stockchart.sample.DataMock
import com.github.wangyiqian.stockchart.sample.R
import kotlinx.android.synthetic.main.activity_sample4.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.suspendCoroutine

/**
 * @author lucas
 * @date 2021/11/15
 */
class Sample4Activity : AppCompatActivity() {

    private val mAdapter = StockAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample4)

        content_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        CoroutineScope(Dispatchers.Main).launch {
            val list = withContext(Dispatchers.IO) {
                createListData()
            }
            mAdapter.submitList(list)
        }
    }

    private suspend fun createListData() = run {
        val data = ArrayList<StockModel>()
        for (i in 0 until 30) {
            if (i == 0) {
                val kList =
                    suspendCoroutine<List<IKEntity>> {
                        DataMock.loadDayData(
                            this@Sample4Activity,
                            0
                        ) { kEntities: List<IKEntity> ->
                            it.resumeWith(Result.success(kEntities))
                        }
                    }
                data.add(StockModel(StockModel.ITEM_TYPE_STOCK_CHART, klineData = kList))
            } else {
                data.add(StockModel(StockModel.ITEM_TYPE_COMMON, title = "Item $i"))
            }
        }
        data
    }
}