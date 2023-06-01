package co.app.food.andromeda.list

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.app.food.andromeda.ExpandHandler
import co.app.food.andromeda.NavBackHandler
import co.app.food.andromeda.R
import co.app.food.andromeda.ValidateHandler
import co.app.food.andromeda.ViewComponentNotDrawnHandler
import co.app.food.andromeda.components.ComponentController
import co.app.food.andromeda.components.ComponentData
import co.app.food.andromeda.components.ComponentDataWithCollapseAction
import co.app.food.andromeda.databinding.AndromedaLayoutListBinding
import co.app.food.andromeda.databinding.AndromedaLayoutViewPagerItemsBinding
import co.app.food.andromeda.extensions.readAttributes
import co.app.food.andromeda.viewpager.ViewPagerListener
import com.airbnb.epoxy.Carousel

class AndromedaListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val data: MutableList<ComponentData> = mutableListOf()
    private var backHandler: NavBackHandler = {}
    private var viewComponentNotDrawnHandler: ViewComponentNotDrawnHandler = {}
    private var validateHandler: ValidateHandler = {}
    private var viewPagerListener: ViewPagerListener? = null
    private var expandHandler: ExpandHandler = { }

    enum class Type {
        LINEAR,
        GRID
    }

    private val controller: ComponentController by lazy {
        ComponentController(
            backHandler = {
                backHandler()
            },
            uncaughtViewData = { data ->
                viewComponentNotDrawnHandler(data)
            },
            validateHandler = { errorMsg ->
                validateHandler(errorMsg)
            },
            viewPagerListener = object : ViewPagerListener {
                override fun onPageLoad(
                    position: Int,
                    urlToLoad: String,
                    binding: AndromedaLayoutViewPagerItemsBinding
                ) {
                    viewPagerListener?.onPageLoad(position, urlToLoad, binding)
                }

                override fun onPageDestroy(position: Int, view: View) {
                    viewPagerListener?.onPageDestroy(position, view)
                }

                override fun onPageSelected(position: Int, urlToLoad: String) {
                    viewPagerListener?.onPageSelected(position, urlToLoad)
                }
            },
            expandHandler = { cardId ->
                val expandedComponents = ArrayList(expandView(cardId, data))
                setUpComponents(expandedComponents)
                expandHandler(cardId)
            }
        )
    }

    private var type: Type = Type.LINEAR
    private var layoutManager: LinearLayoutManager = LinearLayoutManager(context)
    private var binding: AndromedaLayoutListBinding =
        AndromedaLayoutListBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        readAttributes(attrs, R.styleable.AndromedaListView) { typedArray ->
            val typeInt = typedArray.getInt(R.styleable.AndromedaListView_list_type, 0)
            val spanCount = typedArray.getInt(R.styleable.AndromedaListView_grid_span, 3)
            setType(Type.values()[typeInt], spanCount)
        }
        with(binding.eRV) {
            layoutManager = this@AndromedaListView.layoutManager
            setController(controller)
            ViewCompat.setNestedScrollingEnabled(this, false)
            addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    when (e.action) {
                        MotionEvent.ACTION_DOWN -> {
                            binding.eRV.parent?.requestDisallowInterceptTouchEvent(true)
                        }
                        MotionEvent.ACTION_MOVE -> {
                        }
                    }
                    return false
                }

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
            })
        }
        Carousel.setDefaultGlobalSnapHelperFactory(null)
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        binding.eRV.setPadding(left, top, right, bottom)
    }

    fun setUpComponents(components: List<ComponentData>) {
        this.data.clear()
        this.data.addAll(components)
        controller.setData(components)
    }

    fun setBackHandler(backHandler: NavBackHandler) {
        this.backHandler = backHandler
    }

    fun setViewComponentNotDrawnHandler(componentNotDrawnHandler: ViewComponentNotDrawnHandler) {
        this.viewComponentNotDrawnHandler = componentNotDrawnHandler
    }

    fun setValidationHandler(validateHandler: ValidateHandler) {
        this.validateHandler = validateHandler
    }

    fun setViewPagerPageListener(viewPagerListener: ViewPagerListener) {
        this.viewPagerListener = viewPagerListener
    }

    fun getComponentController(): ComponentController {
        return this.controller
    }

    fun setExpandHandler(expandHandler: ExpandHandler) {
        this.expandHandler = expandHandler
    }

    fun setSpanCount(spanCount: Int) {
        if (layoutManager is GridLayoutManager) {
            (layoutManager as GridLayoutManager).spanCount = spanCount
        }
    }

    fun setType(type: Type, spanCount: Int = 3) {
        this.type = type
        layoutManager = if (type == Type.GRID)
            GridLayoutManager(context, spanCount)
                .also {
                    it.spanSizeLookup = controller.spanSizeLookup
                }
        else
            LinearLayoutManager(context)
        if (type == Type.GRID)
            controller.spanCount = spanCount
        binding.eRV.layoutManager = layoutManager
        invalidate()
    }

    override fun setBackground(background: Drawable?) {
        binding.root.background = background
    }

    private fun expandView(
        cardId: String,
        data: MutableList<ComponentData>
    ): MutableList<ComponentData> {
        data.forEach { dj ->
            if (dj is ComponentDataWithCollapseAction) {
                if (dj.id == cardId) {
                    dj.isCollapsed = dj.isCollapsed.not()
                    return@forEach
                } else if (dj.children.isNotEmpty()) {
                    dj.children = expandView(cardId, dj.children).toMutableList()
                }
            }
        }
        return data
    }
}
