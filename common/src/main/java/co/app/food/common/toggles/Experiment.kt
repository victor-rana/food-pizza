package co.app.food.common.toggles

import com.google.gson.annotations.SerializedName

data class Experiment(
    @SerializedName("experiment") val experimentName: String,
    @SerializedName("variant") val variant: String?,
    @SerializedName("version") val version: Int?,
    @SerializedName("run_id") val runId: Int?,
    @SerializedName("exp_id") val experimentId: String?,
    @SerializedName("exp_type") val experimentType: String?,
    @SerializedName("properties") val properties: Map<String, Any> = emptyMap(),
    @SerializedName("lastFetchedTimestamp") val lastFetchedTimestamp: Long =
        System.currentTimeMillis()
)
