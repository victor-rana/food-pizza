package co.app.food.andromeda

infix fun <T> Boolean.then(param: T): T? = if (this) param else null

fun <E> MutableList<E>.replace(collapsedChildren: List<E>) {
    this.removeAll(collapsedChildren)
    this.addAll(collapsedChildren)
}
