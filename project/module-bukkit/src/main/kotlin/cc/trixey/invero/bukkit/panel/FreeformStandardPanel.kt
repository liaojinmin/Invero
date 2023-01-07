package cc.trixey.invero.bukkit.panel

import cc.trixey.invero.bukkit.BukkitPanel
import cc.trixey.invero.common.*
import cc.trixey.invero.common.event.WindowClickEvent

/**
 * @author Arasple
 * @since 2022/12/29 13:48
 *
 * Element 的位置应使用 （x,y) 准确定义
 * FreeformPanel 应作为 Window 的子代，尽量不要嵌套其他父级 Panel
 */
class FreeformStandardPanel(
    parent: PanelContainer,
    weight: PanelWeight,
    override val scale: Scale,
    locate: Pos
) : BukkitPanel(parent, weight, scale, locate), ElementalPanel, FreeformPanel {

    private val elements: Elements = Elements()

    override var viewport: Pos = Pos.NIL
        set(value) {
            field = value
            rerender()
        }

    override fun getElements(): Elements {
        return elements
    }

    override fun handleClick(pos: Pos, e: WindowClickEvent) {
        getElements().findElement(pos + viewport)?.let {
            if (it is Clickable) {
                it.passClickEvent(e)
            }
        }
    }

    override fun reset() {
        viewport = Pos.NIL
    }

    override fun shift(x: Int, y: Int) {
        viewport += (x to y)

        // debug title
        window.title = viewport.toString()
    }

}