/**
 * Created by Spencer Searle on 6/26/24.
 */

package com.example.composebackbone.models

import com.google.gson.annotations.SerializedName
data class DataSet(
        @SerializedName("Location")
        private val location: List<String>,
        @SerializedName("Workspace")
        private val workspace: List<String>,
        @SerializedName("Asset")
        val asset: List<String>
    ) {
        fun getLocations(): List<String> {
            return location
        }

        fun getAssets(): List<String> {
            return asset
        }

    fun getWorkspaces(): List<String> {
        return workspace

    }
}
