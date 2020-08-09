package gt.creeperface.nukkit.scoreboardapi

import cn.nukkit.plugin.PluginBase
import gt.creeperface.nukkit.scoreboardapi.packet.RemoveObjectivePacket
import gt.creeperface.nukkit.scoreboardapi.packet.SetDisplayObjectivePacket
import gt.creeperface.nukkit.scoreboardapi.packet.SetScorePacket
import gt.creeperface.nukkit.scoreboardapi.scoreboard.ObjectiveCriteria
import gt.creeperface.nukkit.scoreboardapi.scoreboard.ObjectiveDisplaySlot
import gt.creeperface.nukkit.scoreboardapi.scoreboard.ObjectiveSortOrder
import gt.creeperface.nukkit.scoreboardapi.scoreboard.SimpleScoreboard
import java.util.*

/**
 * @author CreeperFace
 */
class ScoreboardAPI : PluginBase() {

    override fun onLoad() {
        server.network.registerPacket(RemoveObjectivePacket.NETWORK_ID, RemoveObjectivePacket::class.java)
        server.network.registerPacket(SetDisplayObjectivePacket.NETWORK_ID, SetDisplayObjectivePacket::class.java)
        server.network.registerPacket(SetScorePacket.NETWORK_ID, SetScorePacket::class.java)
    }

//    override fun onEnable() {
//        Test(this)
//    }

    companion object {

        @JvmStatic
        fun builder() = Builder()

        class Builder internal constructor() {

            private var criteria = ObjectiveCriteria("dummy", false)

            private var displaySlot = ObjectiveDisplaySlot.SIDEBAR

            private var sortOrder = ObjectiveSortOrder.ASCENDING

            private var objectiveName = UUID.randomUUID().toString()

            fun setCriteria(criteria: ObjectiveCriteria): Builder {
                this.criteria = criteria

                return this
            }

            fun setDisplaySlot(displaySlot: ObjectiveDisplaySlot): Builder {
                this.displaySlot = displaySlot

                return this
            }

            fun setSortOrder(sortOrder: ObjectiveSortOrder): Builder {
                this.sortOrder = sortOrder

                return this
            }

            fun setObjectiveName(objectiveName: String): Builder {
                this.objectiveName = objectiveName

                return this
            }

            fun build(): SimpleScoreboard {
                return SimpleScoreboard(displaySlot, sortOrder, criteria, objectiveName)
            }
        }
    }
}