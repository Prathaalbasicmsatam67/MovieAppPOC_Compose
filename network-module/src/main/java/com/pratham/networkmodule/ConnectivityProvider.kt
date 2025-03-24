package com.pratham.networkmodule

interface ConnectivityProvider {
    fun isConnected(): Boolean
}