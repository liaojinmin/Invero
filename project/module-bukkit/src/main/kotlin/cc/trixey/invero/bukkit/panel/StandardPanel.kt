package cc.trixey.invero.bukkit.panel

import cc.trixey.invero.bukkit.BukkitPanel
import cc.trixey.invero.common.*
import cc.trixey.invero.common.event.WindowClickEvent

/**
 * @author Arasple
 * @since 2022/12/22 20:32
 */
class StandardPanel(
    parent: PanelContainer,
    weight: PanelWeight,
    scale: Scale,
    locate: Pos
) : BukkitPanel(parent, weight, scale, locate), ElementalPanel {

    private val elements: Elements = Elements()

    override fun getElements(): Elements {
        return elements
    }

    override fun handleClick(pos: Pos, e: WindowClickEvent) {
        getElements().findElement(pos)?.let {
            if (it is Clickable) {
                it.passClickEvent(e)
            }
        }
    }

}