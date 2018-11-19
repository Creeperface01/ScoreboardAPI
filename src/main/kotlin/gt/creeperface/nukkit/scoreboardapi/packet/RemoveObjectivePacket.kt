package gt.creeperface.nukkit.scoreboardapi.packet

import cn.nukkit.network.protocol.DataPacket

/**
 * @author CreeperFace
 */
data class RemoveObjectivePacket(var objectiveName: String) : DataPacket() {

    override fun pid() = NETWORK_ID

    override fun encode() {
        reset()
        putString(objectiveName)
    }

    override fun decode() {
        objectiveName = string
    }

    companion object {
        const val NETWORK_ID = 0x6a.toByte()
    }
}