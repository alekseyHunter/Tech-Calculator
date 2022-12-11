package my.tech.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val _result: MutableLiveData<String> = MutableLiveData("")
    val result: LiveData<String>
        get() = _result

    fun addValue(value: String){
        val result = _result.value ?: ""
        _result.postValue(result + value)
    }

    fun addBracket(value: String){
        val result = _result.value ?: ""
        _result.postValue(result + value)
    }

    fun addOperation(value: String){
        val result = _result.value ?: ""
        _result.postValue(result + value)
    }

    fun clearExpression(){
        _result.postValue("")
    }

    fun removeSymbol(){

    }

    fun equality(){

    }
}