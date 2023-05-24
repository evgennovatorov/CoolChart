package com.evgenii.coolgraph.common

import android.content.Context
import com.evgenii.coolgraph.R

class DeviceConfigurationProviderImpl(
    private val context: Context
): DeviceConfigurationProvider {

    override fun isLandscape() =
        context.resources.getBoolean(R.bool.isLandscape)
}