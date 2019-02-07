package gt.creeperface.nukkit.scoreboardapi.scoreboard

/**
 * @author CreeperFace
 */
open class SimpleScoreboard(displaySlot: ObjectiveDisplaySlot, sortOrder: ObjectiveSortOrder, criteria: ObjectiveCriteria, objectiveName: String) : FakeScoreboard() {

    private val obj: Objective

    init {
        val objective = Objective(objectiveName, criteria)
        val displayObjective = DisplayObjective(objective, sortOrder, displaySlot)

        this.objective = displayObjective
        this.obj = objective
    }

    fun setDisplayName(displayName: String) {
        obj.displayName = displayName
    }

    fun setScore(id: Long, name: String, score: Int) {
        obj.setScore(id, name, score)
    }

    fun getScore(id: Long) = obj.getScore(id)

    fun getAllScores() = obj.getAllScores()

    fun resetScore(id: Long) = obj.resetScore(id)

    fun resetAllScores() = obj.resetAllScores()

    fun resetChanges() = obj.resetChanges()

    fun clearCache() = obj.clearCache()
}