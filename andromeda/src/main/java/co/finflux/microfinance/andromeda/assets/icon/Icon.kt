package co.app.food.andromeda.assets.icon

import co.app.food.andromeda.R

enum class Icon {
    ALERTS {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_bell
        }
    },
    NAVIGATION_BACK {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_navigation_back
        }
    },
    NAVIGATION_CANCEL {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_navigation_cancel
        }
    },
    SHARE {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_share
        }
    },
    SWITCH_THEME {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_switch_theme
        }
    },
    NAVIGATION_OPTIONS {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_navigation_options
        }
    },
    NAVIGATION_OVERFLOW {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_navigation_overflow
        }
    },
    SEARCH {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_search
        }
    },
    LOCK {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_lock
        }
    },
    PASSWORD {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_password
        }
    },
    ARROW_RIGHT {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_right_arow
        }
    },
    MORE {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_more
        }
    },
    ADD {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_add
        }
    },
    COLLECTIONS {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_collections
        }
    },
    TODAY {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_today
        }
    },
    TIME_DAY_SUNRISE {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_time_sunrise
        }
    },
    PROFILE {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_profile
        }
    },
    PASSWORD_VISIBILITY_ON {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_visibility_on
        }
    },
    PASSWORD_VISIBILITY_OFF {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_visibility_off
        }
    },
    NAVIGATION_24_CANCEL {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_navigation_cancel
        }
    },
    GRID {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_grid
        }
    },
    PROGRESS_DASHBOARD {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_progress
        }
    },
    DASHBOARD_MAP_ARROW {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_dashboard_map_arrow
        }
    },
    REFRESH {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_refresh
        }
    },
    FILTER {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_filter
        }
    },
    UNDONE_TASK {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_dashboard_undone_task
        }
    },
    PRESENT {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_present
        }
    },
    ABSENT {
        override fun getDrawableResId(): Int {
            return R.drawable.andromeda_icon_absent
        }
    };

    abstract fun getDrawableResId(): Int
}
