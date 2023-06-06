package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*; //*

class PlayerCollisionsTest {//这里碰撞对象分为玩家，幽灵，豆子
    @Mock
    private PointCalculator pointCalculator;

    private PlayerCollisions playerCollisions;
    private Player player;
    private Ghost ghost;
    private Pellet pellet;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);//为@Mock注释的字段创建相应的模拟对象
        playerCollisions = new PlayerCollisions(pointCalculator);
        player = mock(Player.class);
        ghost = mock(Ghost.class);
        pellet = mock(Pellet.class);
    }

    /**
     * Test case for when the player collides with a ghost.
     */
    @Test
    @DisplayName("玩家与幽灵碰撞")
    @Order(1)
    public void testPlayerCollidingWithGhost(){
        playerCollisions.collide(player,ghost);
        //验证pointCalculator.collidedWithAGhost()方法被调用
        verify(pointCalculator).collidedWithAGhost(player,ghost);

        //验证player.setAlive()被调用
        verify(player).setAlive(false);

        //验证player.setKiller()被调用
        verify(player).setKiller(ghost);

        //验证其他方法未被调用
        verifyNoMoreInteractions(pointCalculator,player,ghost,pellet);
    }

    /**
     * Test case for when the player collides with a pellet.
     */
    @Test
    @DisplayName("玩家与豆子碰撞")
    @Order(2)
    public void testPlayerCollidingWithPellet(){
        playerCollisions.collide(player,pellet);

        //验证consumeAPellect()方法被调用
        verify(pointCalculator).consumedAPellet(player,pellet);

        //验证leaveSquare()被调用
        verify(pellet).leaveSquare();

        //验证其它方法未被调用
        verifyNoMoreInteractions(pointCalculator,player,ghost,pellet);
    }

    /**
     * Test case for when a ghost collides with a player.
     */
    @Test
    @DisplayName("幽灵与玩家碰撞")
    @Order(3)
    public void testGhostCollidingWithPlayer(){
        playerCollisions.collide(ghost,player);

        //验证playerVersusGhost()被调用
        verify(pointCalculator).collidedWithAGhost(player,ghost);
        player.setAlive(false);
        player.setKiller(ghost);

    }

    /**
     * Test case for when a pellet collides with a player.
     */
    @Test
    @DisplayName("豆子与玩家碰撞")
    @Order(4)
    public void testPelletCollidingWithPlayer(){
        playerCollisions.collide(pellet,player);

        //验证playerVersusPellect()被调用
        // verify(playerCollisions).playerVersusPellet(player,pellet);playerCollisions不是模拟，验证里面的方法是否被调用
        verify(pointCalculator).consumedAPellet(player,pellet);
        verify(pellet).leaveSquare();

        //验证其它方法未被调用
        verifyNoMoreInteractions(pointCalculator,player,ghost,pellet);
    }

    @Test
    @DisplayName("Invalid collisions")
    @Order(5)
    void collideWithoutResult(){
        //Ghost collides with Ghost
        playerCollisions.collide(ghost,ghost);

        //Ghost collides with Pellet
        playerCollisions.collide(ghost,pellet);

        //pellect collides with Ghost
        playerCollisions.collide(pellet,ghost);

        verify(player,times(0)).setAlive(false);
        verify(player,times(0)).setKiller(ghost);
        verify(pellet,times(0)).leaveSquare();
    }
}
