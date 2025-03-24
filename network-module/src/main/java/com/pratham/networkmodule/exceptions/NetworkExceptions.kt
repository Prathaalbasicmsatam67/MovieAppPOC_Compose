package com.pratham.networkmodule.exceptions

data class ServerErrorException(val errorCode: Int) : Throwable()
data class ClientErrorException(val errorCode: Int) : Throwable()
