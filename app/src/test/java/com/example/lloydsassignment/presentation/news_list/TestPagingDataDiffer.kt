package com.example.lloydsassignment.presentation.news_list

import androidx.paging.DifferCallback
import androidx.paging.ItemSnapshotList
import androidx.paging.PagingDataDiffer
import androidx.paging.NullPaddedList
import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.Dispatchers

/**
 * A custom implementation of PagingDataDiffer for testing purposes.
 * This class is used to test the behavior of paging data in a controlled environment.
 */
class TestPagingDataDiffer<T : Any> : PagingDataDiffer<T>(
    differCallback = object : DiffUtil.ItemCallback<T>(), DifferCallback {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
        override fun onChanged(position: Int, count: Int) {}
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
    },
    mainContext = Dispatchers.Main,
    cachedPagingData = null
) {
    override suspend fun presentNewList(
        previousList: NullPaddedList<T>,
        newList: NullPaddedList<T>,
        lastAccessedIndex: Int,
        onListPresentable: () -> Unit
    ): Int? {
        // Call the onListPresentable callback
        onListPresentable()
        // Implement the method as needed for your test
        return null
    }

    fun getSnapshot(): ItemSnapshotList<T> {
        return super.snapshot()
    }
}