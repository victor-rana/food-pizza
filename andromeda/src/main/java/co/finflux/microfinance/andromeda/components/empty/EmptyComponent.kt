package co.app.food.andromeda.components.empty

import android.annotation.SuppressLint
import co.app.food.andromeda.ComposableHolder
import co.app.food.andromeda.R
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass
abstract class EmptyComponent : EpoxyModelWithHolder<EmptyComponent.Holder>() {

    override fun getDefaultLayout(): Int {
        return R.layout.layout_empty_component
    }

    inner class Holder : ComposableHolder()
}
