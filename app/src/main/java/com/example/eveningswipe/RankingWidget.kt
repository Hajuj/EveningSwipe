package com.example.eveningswipe

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.provider.Settings.Global.getString
import android.widget.RemoteViews
import com.example.eveningswipe.ui.filmswipe.groupName

/**
 * Implementation of App Widget functionality
 * source: https://www.geeksforgeeks.org/how-to-create-a-basic-widget-of-an-android-app/
 */
var widgetIds: IntArray? = null

class RankingWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        widgetIds = appWidgetIds
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
}

/**
 * function to update widget
 * called in RankingActivity
 */
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    var widgetText: String? = null
    if (groupName == null || movie1 == null || movie2 == null || movie3 == null) {
        widgetText = context.getString(R.string.top_three, "'groupname'", "'movie'", "'movie'", "'movie'")
    } else {
        widgetText = context.getString(R.string.top_three, groupName, movie1, movie2, movie3)
    }
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.ranking_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
