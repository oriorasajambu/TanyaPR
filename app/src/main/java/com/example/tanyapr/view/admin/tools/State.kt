/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.admin.tools

sealed class State<T>(val data: T? = null, val message: String? = null, val condition: Int? = null){
    class Success<T>(data: T) : State<T>(data)
    class Fail<T>(data: T? = null, message: String?) : State<T>(data, message)
    class Error<T>(data: T? = null, message: String?) : State<T>(data, message)
    class Invalid<T>(message: String, condition: Int) : State<T>(message = message, condition = condition)
    class Reset<T> : State<T>()
}