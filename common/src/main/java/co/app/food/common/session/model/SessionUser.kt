package co.app.food.common.session.model

import com.google.gson.annotations.SerializedName

data class CurrencyOptionsItem(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("name")
    val name: String = ""
)

data class SessionUser(
    @SerializedName("authenticated")
    val authenticated: Boolean = false,
    @SerializedName("officeName")
    val officeName: String = "",
    @SerializedName("firstname")
    val firstname: String = "",
    @SerializedName("staffDisplayName")
    val staffDisplayName: String = "",
    @SerializedName("clientId")
    val clientId: String = "",
    @SerializedName("shouldRenewPassword")
    val shouldRenewPassword: Boolean = false,
    @SerializedName("roles")
    val roles: List<RolesItem> = emptyList(),
    @SerializedName("lastLoginDate")
    val lastLoginDate: String = "",
    @SerializedName("accessToken")
    val accessToken: String = "",
    @SerializedName("userId")
    val userId: String? = "",
    @SerializedName("officeId")
    val officeId: String = "",
    @SerializedName("permissions")
    val permissions: List<String> = emptyList(),
    @SerializedName("organisationalRole")
    val organisationalRole: OrganisationalRole? = null,
    @SerializedName("staffId")
    val staffId: String = "",
    @SerializedName("username")
    val username: String = ""
)

data class CurrencyData(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("name")
    val name: String = ""
)

data class EndTime(
    @SerializedName("hour")
    val hour: Int = 0,
    @SerializedName("nano")
    val nano: Int = 0,
    @SerializedName("minute")
    val minute: Int = 0,
    @SerializedName("second")
    val second: Int = 0
)

data class StartTime(
    @SerializedName("hour")
    val hour: Int = 0,
    @SerializedName("nano")
    val nano: Int = 0,
    @SerializedName("minute")
    val minute: Int = 0,
    @SerializedName("second")
    val second: Int = 0
)

data class RoleBasedLimitsItem(
    @SerializedName("roleBasedLimitId")
    val roleBasedLimitId: Int = 0,
    @SerializedName("loanApproval")
    val loanApproval: Int = 0,
    @SerializedName("currencyData")
    val currencyData: CurrencyData,
    @SerializedName("currencyCode")
    val currencyCode: String = ""
)

data class RoleOperationalWorkingHoursData(
    @SerializedName("roleId")
    val roleId: Int = 0,
    @SerializedName("startTime")
    val startTime: StartTime,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("endTime")
    val endTime: EndTime
)

data class RolesItem(
    @SerializedName("roleBasedLimits")
    val roleBasedLimits: List<RoleBasedLimitsItem>?,
    @SerializedName("currencyOptions")
    val currencyOptions: List<CurrencyOptionsItem>?,
    @SerializedName("roleOperationalWorkingHoursData")
    val roleOperationalWorkingHoursData: RoleOperationalWorkingHoursData,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("disabled")
    val disabled: Boolean = false,
    @SerializedName("id")
    val id: Int = 0
)

data class OrganisationalRole(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("systemCode")
    val systemCode: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("value")
    val value: String = ""
)
