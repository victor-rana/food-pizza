package co.app.food.andromeda.components

import co.app.food.andromeda.ExpandHandler
import co.app.food.andromeda.NavBackHandler
import co.app.food.andromeda.ViewComponentNotDrawnHandler
import co.app.food.andromeda.components.button.ButtonComponent_
import co.app.food.andromeda.components.button.data.ButtonComponentData
import co.app.food.andromeda.components.carousel.data.CarouselComponentData
import co.app.food.andromeda.components.empty.EmptyComponent_
import co.app.food.andromeda.components.text.TextComponent_
import co.app.food.andromeda.components.text.data.TextComponentData
import co.app.food.andromeda.components.viewgroup.CardGroupComponent_
import co.app.food.andromeda.components.viewgroup.GridComponent_
import co.app.food.andromeda.components.viewgroup.LinearGroupComponent_
import co.app.food.andromeda.components.viewgroup.data.ViewGroupComponentData
import co.app.food.andromeda.components.viewgroup.data.ViewGroupTypes
import co.app.food.andromeda.components.viewpager.ViewPagerComponent_
import co.app.food.andromeda.components.viewpager.data.ViewPagerComponentData
import co.app.food.andromeda.extensions.dpToPixels
import co.app.food.andromeda.inputfield.internal.AndromedaFieldValidation
import co.app.food.andromeda.replace
import co.app.food.andromeda.viewpager.ViewPagerListener
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyModel

fun generateModel(
    textMappings: MutableMap<String, CharSequence>,
    selectionMappings: MutableMap<String, Boolean>,
    fieldValidations: MutableList<AndromedaFieldValidation?>,
    data: ComponentData,
    backHandler: NavBackHandler,
    viewComponentNotDrawnHandler: ViewComponentNotDrawnHandler,
    viewPagerListener: ViewPagerListener,
    expandHandler: ExpandHandler
): EpoxyModel<*> {
    return when (data) {
        is TextComponentData -> {
            TextComponent_()
                .id(data.id)
                .textComponentData(data)
        }
        is ButtonComponentData -> {
            ButtonComponent_()
                .id(data.id)
                .buttComponentData(data)
        }
        is CarouselComponentData -> {
            val children = data.children.map { dj ->
                generateModel(
                    textMappings = textMappings,
                    selectionMappings = selectionMappings,
                    fieldValidations = fieldValidations,
                    data = dj,
                    backHandler = backHandler,
                    viewComponentNotDrawnHandler = viewComponentNotDrawnHandler,
                    viewPagerListener = viewPagerListener,
                    expandHandler = expandHandler,
                )
            }
            CarouselModel_()
                .id(data.id)
                .padding(
                    Carousel.Padding(
                        data.paddingHorizontal.dpToPixels(),
                        data.paddingVertical.dpToPixels(),
                        data.paddingHorizontal.dpToPixels(),
                        data.paddingVertical.dpToPixels(),
                        0
                    )
                )
                .models(children)
        }
        is ViewGroupComponentData -> {
            if (data.isCollapsed) {
                data.children.removeAll(data.collapsedChildren)
            } else {
                data.children.replace(data.collapsedChildren)
            }
            val models = data.children.map { dj ->
                generateModel(
                    textMappings = textMappings,
                    selectionMappings = selectionMappings,
                    fieldValidations = fieldValidations,
                    data = dj,
                    backHandler = backHandler,
                    viewComponentNotDrawnHandler = viewComponentNotDrawnHandler,
                    viewPagerListener = viewPagerListener,
                    expandHandler = expandHandler,
                )
            }
            when (data.type) {
                ViewGroupTypes.CARD -> CardGroupComponent_(models)
                    .id(data.id)
                    .viewGroup(data)
                    .expandHandler(expandHandler)
                ViewGroupTypes.LINEAR -> LinearGroupComponent_(models)
                    .id(data.id)
                    .viewGroupData(data)
                    .expandHandler(expandHandler)
                ViewGroupTypes.GRID -> GridComponent_()
                    .id(data.id)
                    .gridComponentData(data)
            }
        }
        is ViewPagerComponentData -> {
            ViewPagerComponent_()
                .id(data.id)
                .pages(data.pages)
                .viewPagerListener(viewPagerListener)
        }
        else -> {
            viewComponentNotDrawnHandler(data)
            EmptyComponent_().id(data.id)
        }
    }
}
