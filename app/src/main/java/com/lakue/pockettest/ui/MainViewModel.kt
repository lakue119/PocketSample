package com.lakue.pockettest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.lakue.pockettest.model.ResultPocket
import com.lakue.pockettest.repository.PocketRepository
import com.lakue.pockettest.utils.Event
import com.lakue.pockettest.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val pocketRepository: PocketRepository
) : ViewModel() {

    val LIMIT_COUNT = 30

    //포켓몬 데이터
    private val _livePocketInfo = MutableLiveData<ArrayList<ResultPocket>>()
    val livePocketInfo: LiveData<ArrayList<ResultPocket>> = _livePocketInfo
    val listPocketInfo = ArrayList<ResultPocket>()

    //마지막 페이징 Boolean
    private val _liveIsFinish = MutableLiveData<Boolean>(false)
    val liveIsFinish: LiveData<Boolean> get() = _liveIsFinish

    //마지막 페이징 Boolean
    private val _liveIsEmpty = MutableLiveData<Boolean>(true)
    val liveIsEmpty: LiveData<Boolean> get() = _liveIsEmpty

    //Activity Event전달 - Toast
    private val _toastEvent = MutableLiveData<Event<String>>()
    val toastEvent: LiveData<Event<String>> = _toastEvent

    //Activity Event전달 - Loading
    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    val adapter = MainAdapter(this)

    //BottomCatch Event - 마지막 Item개수
    val rvBottomCatch: Function1<Int, Unit> = this::onBottomCatch

    //TextChagne Event - keyword change
    val etTextChange: Function1<String, Unit> = this::onEditorTextChange

    private var rvloading = false
    private var keyword = ""

    init {
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                checkEmpty()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                super.onItemRangeChanged(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                super.onItemRangeChanged(positionStart, itemCount, payload)
                checkEmpty()
            }

            fun checkEmpty() {
                _liveIsEmpty.postValue(adapter.itemCount == 0)
            }
        })
    }

    //RecyclerView Bottom Catch
    fun onBottomCatch(lastPositionCount: Int) {
        if(!rvloading && lastPositionCount >= adapter.itemCount - 2){
            rvloading = true
            fetchPocketInfo()
        }
    }

    //EditText TextChange - get Keyword
    fun onEditorTextChange(keyword: String) {
        this.keyword = keyword
        adapter.itemClear()
        if(keyword.isEmpty()){
            rvloading = false
            adapter.addItems(ArrayList(listPocketInfo), true)
        } else {
            var searchList = listPocketInfo.filter { it.name.contains(keyword) }
            rvloading = true
            adapter.addItems(ArrayList(searchList), false)
        }
        adapter.setKeyword(keyword)
    }

    fun fetchPocketInfo(limit:Int = LIMIT_COUNT, offset:Int = max(0,adapter.itemCount-1)){
        viewModelScope.launch {
            if(networkCheck()){
                pocketRepository.getPocketInfo(
                    limit,
                    offset
                ).let { reponseSearch ->
                    if (reponseSearch.isSuccessful) {
                        //API Success
                        val data = reponseSearch.body()!!
                        if(data.next == null){
                            _liveIsFinish.value = true
                        }
                        adapter.addItems(ArrayList(data.results), true)
                        listPocketInfo.addAll(ArrayList(data.results))
                    } else {
                        //Api Fail
                        _toastEvent.value = Event("API 호출에 실패했습니다.")
                    }

                    rvloading = false
                    _isLoading.value = Event(false)
                }
            }
        }
    }

    fun networkCheck(): Boolean{
        return if (networkHelper.isNetworkConnected()) {
            //네트워크 연결
            true
        } else {
            //네트워크 연결 실패
            _toastEvent.value = Event("네트워크 연결을 확인해주세요.")
            rvloading = false
            _isLoading.value = Event(false)
            false
        }
    }

    fun onPocketClilck(name: String){
        _toastEvent.value = Event(name)
    }
}