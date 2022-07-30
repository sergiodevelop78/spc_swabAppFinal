package com.spcswabapp.models

data class ServerResponse<T>(
    val error: Boolean,
    val message: String,
    val data: T?,
)
