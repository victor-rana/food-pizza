package co.app.food.common.session

import co.app.food.common.session.model.SessionUser
import org.json.JSONException
import org.json.JSONObject

class UserReader {

    companion object {
        const val KEY_USER_ID = "userId"
        const val KEY_USERNAME = "userName"
        const val KEY_ACCESS_TOKEN = "accessToken"
    }

    fun read(userJson: String): SessionUser? {
        val jsonObject = try {
            JSONObject(userJson)
        } catch (exception: JSONException) {
            // Timber.wtf(exception, "Invalid user JSON.")
            return null
        }

        return SessionUser(
            userId = jsonObject.getString(KEY_USER_ID),
            username = jsonObject.getString(KEY_USERNAME),
            accessToken = jsonObject.getString(KEY_ACCESS_TOKEN)
        )
    }
}
