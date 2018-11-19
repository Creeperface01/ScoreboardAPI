package gt.creeperface.nukkit.scoreboardapi.packet

import cn.nukkit.network.protocol.DataPacket
import gt.creeperface.nukkit.scoreboardapi.packet.data.ScoreInfo

/**
 * @author CreeperFace
 */
data class SetScorePacket(val action: Action, val infos: List<ScoreInfo>) : DataPacket() {

    override fun pid() = NETWORK_ID

    override fun encode() {
        reset()
        putByte(action.ordinal.toByte())

        putUnsignedVarInt(infos.size.toLong())
        infos.forEach {
            putVarLong(it.scoreId)
            putString(it.objective)
            putLInt(it.score)
            putByte(Type.FAKE.ordinal.toByte()) //always fake for now
            putString(it.name)
        }
    }

    override fun decode() {

    }

    enum class Action {
        SET,
        REMOVE
    }

    enum class Type {
        INVALID,
        PLAYER,
        ENTITY,
        FAKE
    }

    companion object {
        const val NETWORK_ID = 0x6c.toByte()
    }
}