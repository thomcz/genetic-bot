package de.genetic.bot.simulation

import java.awt.Point

class Zombie (val position : Point) {
    val stepSize = 50
    val damage = 30
    var isDead = false

    fun move(newPosition : Point) {
        val dx = position.x - this.position.x
        val dy = position.y - this.position.y
        val dist = this.position.distance(position)
        val moveFactor = stepSize / dist
        if (moveFactor >= 1)
            this.position.location = position
        else {
            val newPosition = Point((this.position.x + dx * moveFactor) as Int, (this.position.y + dy * moveFactor) as Int)
            this.position.location = newPosition
        }
    }

    fun kill() {
        isDead = true
    }

    fun hit(player: Player) {
        player.damage(damage)
    }

    override fun toString(): String {
        return String.format("%d %d\n", position.x, position.y)
    }

}