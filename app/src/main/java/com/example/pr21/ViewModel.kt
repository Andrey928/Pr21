package com.example.pr21

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.concurrent.thread

class ViewModel : ViewModel() {

    private val _elapsedTime = MutableLiveData<Long>()
    val elapsedTime: LiveData<Long>
        get() = _elapsedTime

    private var isRunning = false
    private var startTime: Long = 0
    private var pausedTime: Long = 0

    init {
        _elapsedTime.value = 0
    }

    fun start() {
        if (!isRunning) {
            isRunning = true
            startTime = System.currentTimeMillis() - pausedTime

            thread {
                while (isRunning) {
                    val currentTime = System.currentTimeMillis()
                    val elapsedTime = currentTime - startTime

                    _elapsedTime.postValue(elapsedTime)

                    Thread.sleep(1000)
                }
            }
        }
    }
    fun pause() {
        if (isRunning) {
            isRunning = false
            pausedTime = System.currentTimeMillis() - startTime
        }
    }

    fun stop() {
        isRunning = false
        pausedTime = 0
        _elapsedTime.value = 0
    }
}