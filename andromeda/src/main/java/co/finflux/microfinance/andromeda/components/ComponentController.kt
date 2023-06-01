package co.app.food.andromeda.components

import co.app.food.andromeda.ExpandHandler
import co.app.food.andromeda.NavBackHandler
import co.app.food.andromeda.ValidateHandler
import co.app.food.andromeda.ViewComponentNotDrawnHandler
import co.app.food.andromeda.inputfield.internal.AndromedaFieldValidation
import co.app.food.andromeda.viewpager.ViewPagerListener
import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.stickyheader.StickyHeaderCallbacks

class ComponentController(
    private val backHandler: NavBackHandler,
    private val uncaughtViewData: ViewComponentNotDrawnHandler,
    private val validateHandler: ValidateHandler,
    private val viewPagerListener: ViewPagerListener,
    val expandHandler: ExpandHandler
) : TypedEpoxyController<List<ComponentData>>(), StickyHeaderCallbacks {

    val textChangeMappings = mutableMapOf<String, CharSequence>()
    val selectionMappings = mutableMapOf<String, Boolean>()
    val keyValueMappings = mutableMapOf<String, Any>()
    private val fieldValidations: MutableList<AndromedaFieldValidation?> = mutableListOf()

    /***
     * Component id
     * AndromedaFieldValidation to check
     */

    override fun buildModels(data: List<ComponentData>) {
        data.map { componentData ->
            generateModel(
                data = componentData,
                textMappings = textChangeMappings,
                selectionMappings = selectionMappings,
                fieldValidations = fieldValidations,
                backHandler = {
                    backHandler()
                },
                viewComponentNotDrawnHandler = { data ->
                    uncaughtViewData(data)
                },
                viewPagerListener = viewPagerListener,
                expandHandler = expandHandler
            ).addTo(this)
        }
    }
}
