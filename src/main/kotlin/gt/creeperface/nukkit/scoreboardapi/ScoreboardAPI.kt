package gt.creeperface.nukkit.scoreboardapi

import cn.nukkit.event.Listener
import cn.nukkit.plugin.PluginBase
import gt.creeperface.nukkit.scoreboardapi.packet.RemoveObjectivePacket
import gt.creeperface.nukkit.scoreboardapi.packet.SetDisplayObjectivePacket
import gt.creeperface.nukkit.scoreboardapi.packet.SetScorePacket

/**
 * @author CreeperFace
 */
class ScoreboardAPI : PluginBase(), Listener {

    override fun onLoad() {
        server.network.registerPacket(RemoveObjectivePacket.NETWORK_ID, RemoveObjectivePacket::class.java)
        server.network.registerPacket(SetDisplayObjectivePacket.NETWORK_ID, SetDisplayObjectivePacket::class.java)
        server.network.registerPacket(SetScorePacket.NETWORK_ID, SetScorePacket::class.java)
    }
}