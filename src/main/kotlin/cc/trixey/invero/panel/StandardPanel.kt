package cc.trixey.invero.panel

import cc.trixey.invero.common.*

/**
 * @author Arasple
 * @since 2022/12/22 20:32
 */
class StandardPanel(
    parent: PanelContainer,
    weight: PanelWeight,
    scale: Pair<Int, Int>,
    locate: Pos
) : PanelInstance(parent, weight, scale, locate), ElementalPanel {

    private val elements: ElementMap = ElementMap()

    override fun getElemap(): ElementMap {
        return elements
    }

}