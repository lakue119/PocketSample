package com.lakue.pockettest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lakue.pockettest.model.ResponsePocket
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
    private val _livePocketInfo = MutableLiveData<ArrayList<ResponsePocket>>()
    val livePocketInfo: LiveData<ArrayList<ResponsePocket>> = _livePocketInfo
    val listPocketInfo = ArrayList<ResultPocket>()

    //마지막 페이징 Boolean
    private val _isFinish = MutableLiveData<Boolean>(false)
    val isFinish: LiveData<Boolean> get() = _isFinish

    //Activity Event전달 - Toast
    private val _toastEvent = MutableLiveData<Event<String>>()
    val toastEvent: LiveData<Event<String>> = _toastEvent

    //Activity Event전달 - Loading
    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    val adapter = MainAdapter(this)

    //BottomCatch Event - 마지막 Item개수
    val rvBottomCatch: Function1<Int, Unit> = this::onBottomCatch

    private var rvloading = false

    //RecyclerView Bottom Catch
    fun onBottomCatch(lastPositionCount: Int) {
        if(!rvloading && lastPositionCount >= adapter.itemCount - 2){
            rvloading = true
            fetchPocketInfo()
        }
    }

    fun fetchPocketInfo(){
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                //네트워크 연결
                pocketRepository.getPocketInfo(
                    LIMIT_COUNT,
                    max(0,adapter.itemCount-1)
                ).let { reponseSearch ->
                    if (reponseSearch.isSuccessful) {
                        //API Success
                        val data = reponseSearch.body()!!
                        if(data.next == null){
                            _isFinish.value = true
                        }
                        adapter.addItems(ArrayList(data.results))
                        listPocketInfo.addAll(ArrayList(data.results))
                    } else {
                        //Api Fail
                        _toastEvent.value = Event("API 호출에 실패했습니다.")
                    }
                }
                rvloading = false
                _isLoading.value = Event(false)
            } else {
                //네트워크 연결 실패
                _toastEvent.value = Event("네트워크 연결을 확인해주세요.")
                rvloading = false
                _isLoading.value = Event(false)
            }
        }
    }

    fun onPocketClilck(name: String){
        _toastEvent.value = Event(name)
    }
}