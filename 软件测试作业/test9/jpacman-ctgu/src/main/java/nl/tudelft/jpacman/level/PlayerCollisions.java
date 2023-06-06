package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;

/**
 * A simple implementation of a collision map for the JPacman player.
 * <p>
 * It uses a number of instanceof checks to implement the multiple dispatch for the 
 * collisionmap. For more realistic collision maps, this approach will not scale,
 * and the recommended approach is to use a {@link CollisionInteractionMap}.
 *
 * @author Arie van Deursen, 2014
 *
 */

public class PlayerCollisions implements CollisionMap {//处理与玩家有关的碰撞事件

    private PointCalculator pointCalculator;

    /**
     * Create a simple player-based collision map, informing the
     * point calculator about points to be added.
     *
     * @param pointCalculator
     *             Strategy for calculating points.
     */

    //传入一个pointCalculator参数用于计算分数
    public PlayerCollisions(PointCalculator pointCalculator) {
        this.pointCalculator = pointCalculator;
    }

    @Override
    public void collide(Unit mover, Unit collidedOn) {//处理碰撞事件的方法，根据碰撞者和被碰撞者的类型调用相应的处理方法
        if (mover instanceof Player) {
            playerColliding((Player) mover, collidedOn);
        }
        else if (mover instanceof Ghost) {
            ghostColliding((Ghost) mover, collidedOn);
        }
        else if (mover instanceof Pellet) {
            pelletColliding((Pellet) mover, collidedOn);
        }
    }

    private void playerColliding(Player player, Unit collidedOn) { //处理玩家与其它单位碰撞的方法。根据被碰撞者的类型，调用相应处理方法
        if (collidedOn instanceof Ghost) {
            playerVersusGhost(player, (Ghost) collidedOn);
        }
        if (collidedOn instanceof Pellet) {
            playerVersusPellet(player, (Pellet) collidedOn);
        }
    }

    private void ghostColliding(Ghost ghost, Unit collidedOn) { //处理幽灵与其它单位碰撞的方法。如果被碰撞者是玩家，调用方法
        if (collidedOn instanceof Player) {
            playerVersusGhost((Player) collidedOn, ghost);
        }
    }

    private void pelletColliding(Pellet pellet, Unit collidedOn) { //处理豆子与其它单位碰撞的方法。如果被碰撞者是玩家，调用方法
        if (collidedOn instanceof Player) {
            playerVersusPellet((Player) collidedOn, pellet);
        }
    }


    /**
     * Actual case of player bumping into ghost or vice versa.
     *
     * @param player
     *          The player involved in the collision.
     * @param ghost
     *          The ghost involved in the collision.
     */
    public void playerVersusGhost(Player player, Ghost ghost) { //处理玩家幽灵碰撞的实际情况。调用pointCalculator计算分数，设置玩家状态和记录杀死玩家的幽灵
        pointCalculator.collidedWithAGhost(player, ghost);
        player.setAlive(false);
        player.setKiller(ghost);
    }

    /**
     * Actual case of player consuming a pellet.
     *
     * @param player
     *           The player involved in the collision.
     * @param pellet
     *           The pellet involved in the collision.
     */
    public void playerVersusPellet(Player player, Pellet pellet) { //处理玩家吃掉豆子的实际情况。调用pointCalculator计算分数，将豆子从方格中移除
        pointCalculator.consumedAPellet(player, pellet);
        pellet.leaveSquare();
    }

}
