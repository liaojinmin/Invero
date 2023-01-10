package cc.trixey.invero.common

import cc.trixey.invero.common.event.WindowClickEvent
import cc.trixey.invero.common.panel.PanelContainer
import cc.trixey.invero.common.panel.PanelWeight

/**
 * @author Arasple
 * @since 2022/12/20 20:43
 */
interface Panel : Gridable, Clickable {

    /**
     * The parent of this panel
     *
     * e.g.
     * Window
     * Netesed or PanelGroup
     */
    val parent: PanelContainer

    /**
     * The window that hold this panel
     */
    val window: Window

    /**
     * The weight of this panel
     */
    val weight: PanelWeight

    /**
     * The location of this panel relative to its parent
     */
    val locate: Pos

    /**
     * Scale area related to its locate
     */
    val area: Set<Pos>

    /**
     * Render this panel
     */
    fun render()

    /**
     * Wipe this panel
     */
    fun wipe() = wipe(area)

    fun wipe(wiping: Collection<Pos>) {
        if (parent.isPanel()) {
            val parentScale = parent.scale
            val parentLocate = (parent as Panel).locate

            wiping
                .map { it.convertToParent(scale, parentScale, parentLocate) }
                .let { return parent.cast<Panel>().wipe(it) }
        }
        window.let { window ->
            val slots = wiping.map { it.convertToSlot(window.scale) }
            window.inventory.clear(slots)
        }
    }

    fun isElementValid(element: Element): Boolean

    fun rerender() = wipe().also { render() }

    fun handleClick(pos: Pos, e: WindowClickEvent)

}