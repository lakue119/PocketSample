package com.lakue.pockettest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lakue.pockettest.R
import com.lakue.pockettest.base.BaseAdapter
import com.lakue.pockettest.base.BaseViewHolder
import com.lakue.pockettest.databinding.ItemPocketBinding
import com.lakue.pockettest.databinding.ItemProgressBinding
import com.lakue.pockettest.model.ResultPocket


class MainAdapter(val viewModel: MainViewModel) : BaseAdapter() {

    val TYPE_PROGRESS = 1001
    val TYPE_ITEM = 1002

    val TAG = "MainAdapter"
    var pocketList = ArrayList<Any>()

    var key = ""

    //EditText 변경 시 주입
    fun setKeyword(keyword: String){
        this.key = keyword
    }

    fun itemClear(){
        pocketList.clear()
        notifyDataSetChanged()
    }

    fun addItems(items: ArrayList<ResultPocket>, isLoading: Boolean) {
        val pos = pocketList.size

        if (viewModel.liveIsFinish.value!!) {
            //마지막 페이지 호출 시 아이템만 로드
            pocketList.addAll(items)
        } else {
            //마지막 페이지가 아닐 경우
            //첫번째 TYPE_PROGRESS제거 후 리스트 추가 후 TYPE_PROGRESS추가
            if (pocketList.isNotEmpty()) {
                pocketList.removeAt(pocketList.size - 1)
                notifyItemRemoved(pocketList.size )
            }
            pocketList.addAll(items)
            if(isLoading){
                pocketList.add(true)
            }
        }
        notifyItemRangeInserted(pos, pocketList.size - 1)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == TYPE_PROGRESS) {
            DataBindingUtil.inflate<ItemProgressBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_progress,
                parent,
                false
            ).let {
                return ProgressViewHolder(it)
            }
        } else {
            DataBindingUtil.inflate<ItemPocketBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_pocket,
                parent,
                false
            ).let {
                return PocketViewHolder(it)
            }
        }
    }

    override fun getItemCount() = pocketList.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is ProgressViewHolder -> {
            }
            is PocketViewHolder -> {
                holder.onBind(pocketList[position] as ResultPocket, viewModel, key)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (pocketList[position]) {
            is Boolean -> TYPE_PROGRESS
            else -> TYPE_ITEM
        }
    }

    /**
     * ViewHolder
     */
    inner class ProgressViewHolder(private val binding: ItemProgressBinding) :
        BaseViewHolder(binding.root) {
        fun onBind() {

        }
    }

    inner class PocketViewHolder(private val binding: ItemPocketBinding) :
        BaseViewHolder(binding.root) {
        fun onBind(pocketItem: ResultPocket, viewModel: MainViewModel, key: String) {
            binding.apply {
                item = pocketItem
                vm = viewModel
                keyword = key
            }
        }
    }


}