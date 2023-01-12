package cc.trixey.invero.common

import cc.trixey.invero.common.event.*
import cc.trixey.invero.common.panel.PanelContainer

/**
 * Invero
 * cc.trixey.invero.common.Window
 *
 * @author Arasple
 * @since 2022/12/20 20:04
 */
interface Window : PanelContainer, Gridable {

    /**
     * Viewers of this window
     *
     * Support for mutiple viewers is only for audience usage.
     * For standard user-specific GUI, you are expected to create individual windows
     */
    val viewers: Set<Viewer>

    /**
     * Title of this window
     */
    var title: String

    /**
     * for Minecraft inventories:
     *
     * Size = container size + 36(player inventory)
     */
    val size: Int

    /**
     * Proxy inventory
     */
    val inventory: ProxyInventory

    /**
     * Using player inventory mode
     */
    val storageMode: StorageMode

    /**
     * Open this window for a viewer
     */
    fun open(viewer: Viewer)

    /**
     * Close this window
     */
    fun close(viewer: Viewer)

    /**
     * Render panels
     */
    fun render()

    /**
     * Handle relevant event
     */
    fun handleDrag(e: WindowDragEvent)

    fun handleItemsMove(e: WindowItemsMoveEvent)

    fun handleItemsCollect(e: WindowItemsCollectEvent)

    fun handleClick(e: WindowClickEvent)

    fun handleOpen(e: WindowOpenEvent)

    fun handleClose(e: WindowCloseEvent)

    fun <T> forViewersInstance(block: (it: T) -> Unit) = viewers
        .filter { it.isAvailable() }
        .forEach { block(it.getInstance()) }

    @Suppress("UNCHECKED_CAST")
    fun <T : Viewer> forViewers(block: (it: T) -> Unit) = viewers
        .filter { it.isAvailable() }
        .forEach {
            block(it as T)
        }

    fun hasViewer() = viewers.isNotEmpty()

    fun noViewer() = viewers.isEmpty()

}