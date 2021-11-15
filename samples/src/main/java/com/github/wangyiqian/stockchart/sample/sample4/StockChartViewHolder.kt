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
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.wangyiqian.stockchart.StockChart
import com.github.wangyiqian.stockchart.StockChartConfig
import com.github.wangyiqian.stockchart.childchart.base.HighlightLabelConfig
import com.github.wangyiqian.stockchart.childchart.kchart.KChartConfig
import com.github.wangyiqian.stockchart.childchart.kchart.KChartFactory
import com.github.wangyiqian.stockchart.childchart.timebar.TimeBarConfig
import com.github.wangyiqian.stockchart.childchart.timebar.TimeBarFactory
import com.github.wangyiqian.stockchart.sample.R
import com.github.wangyiqian.stockchart.util.DimensionUtil
import kotlinx.android.synthetic.main.item_stock_chart.view.*
import java.text.DecimalFormat

/**
 * @author lucas
 * @date 2021/11/15
 */
class StockChartViewHolder(
    parent: ViewGroup,
    private val context: Context = parent.context
) : RecyclerView.ViewHolder(
    LayoutInflater.from(context).inflate(R.layout.item_stock_chart, parent, false)
) {

    private val mStockChartConfig = StockChartConfig()
    private val mKChartConfig = KChartConfig()
    private val mTimeBarConfig = TimeBarConfig()
    private val stockChart: StockChart

    init {
        // 总配置
        itemView.stock_chart.apply {
            stockChart = this
            setConfig(mStockChartConfig)
            // K线图的配置与工厂
            val kChartFactory = KChartFactory(stockChart = this, childChartConfig = mKChartConfig)

            // 时间条图的配置与工厂
            val timeBarFactory =
                TimeBarFactory(stockChart = this, childChartConfig = mTimeBarConfig)

            val highLightBgColor = Color.parseColor("#000000")
            val lineTextColor = Color.parseColor("#8E8E8E")
            val lineTextSize = DimensionUtil.dp2px(context, 10f).toFloat()

            // 将需要显示的子图的工厂加入全局配置
            mStockChartConfig.apply {
                addChildCharts(kChartFactory, timeBarFactory)

                // 网格线设置
                gridVerticalLineCount = 3
                gridHorizontalLineCount = 4

                backgroundColor = Color.WHITE
                highlightVerticalLineColor = highLightBgColor
                highlightHorizontalLineColor = highLightBgColor

                // 最大缩放比例
                scaleFactorMax = 2f

                // 最小缩放比例
                scaleFactorMin = 0.5f

                // 是否支持双指缩放
                scaleAble = true

                // 是否支持滑动
                scrollAble = true

                overScrollAble = false

                scrollSmoothly = false
            }
            mKChartConfig.apply {
                height = context.resources.displayMetrics.widthPixels * 25 / 36
                index = null

                val decimalFormat = DecimalFormat().apply {
                    maximumFractionDigits = 4
                    minimumFractionDigits = 2
                    groupingSize = 3
                }
                // y轴标签设置
                leftLabelConfig = KChartConfig.LabelConfig(
                    5,
                    { decimalFormat.format(it) },
                    lineTextSize,
                    lineTextColor,
                    DimensionUtil.dp2px(context, 10f).toFloat(),
                    DimensionUtil.dp2px(context, 30f).toFloat(),
                    DimensionUtil.dp2px(context, 30f).toFloat()
                )

                // 长按左侧标签配置
                HighlightLabelConfig(
                    textSize = DimensionUtil.sp2px(context, 10f).toFloat(),
                    bgColor = highLightBgColor,
                    padding = DimensionUtil.dp2px(context, 5f).toFloat(),
                    textFormat = { decimalFormat.format(it) }
                ).apply {
                    highlightLabelLeft = null
                    highlightLabelRight = this
                }
            }
            mTimeBarConfig.apply {
                labelTextColor = lineTextColor
                labelTextSize = lineTextSize
                highlightLabelBgColor = highLightBgColor
                highlightLabelTextSize = lineTextSize
            }
        }
    }

    fun onBind(model: StockModel) {
        // 加载模拟数据

        // 初始显示最后50条数据
        val pageSize = 50
        val kEntities = model.klineData ?: return
        // 设置加载到的数据
        mStockChartConfig.setKEntities(
            kEntities,
            showStartIndex = kEntities.size - pageSize,
            showEndIndex = kEntities.size - 1
        )

        // 通知更新K线图
        stockChart.notifyChanged()
    }
}