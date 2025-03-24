package com.pratham.networkmodule

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class ConnectivityProviderImpl(
    private val connectivityManager: ConnectivityManager
) : ConnectivityProvider {
    override fun isConnected() = getActiveNetworksCapabilities().any {
        it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                it.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    private fun getActiveNetworksCapabilities(): List<NetworkCapabilities> {
        val returnedNetworks = mutableListOf<NetworkCapabilities>()
        connectivityManager.run {
            val activeNetworkCapabilities = getNetworkCapabilities(activeNetwork) ?: return emptyList()

            if (activeNetworkCapabilities.isVpn()) {
                // If VPN is the main active network return all other networks
                returnedNetworks.addAll(allNetworksCapabilitiesExceptVpn())
            } else {
                // If VPN is not active just return main active network
                returnedNetworks.add(activeNetworkCapabilities)
            }
        }
        return returnedNetworks
    }

    private fun ConnectivityManager.allNetworksCapabilitiesExceptVpn(): List<NetworkCapabilities> =
        allNetworks
            .filter { getNetworkCapabilities(it)?.isVpn() == false }
            .mapNotNull { getNetworkCapabilities(it) }

    private fun NetworkCapabilities.isVpn() = hasTransport(NetworkCapabilities.TRANSPORT_VPN)
}
