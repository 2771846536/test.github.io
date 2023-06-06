package nl.tudelft.jpacman.npc.ghost;

import io.cucumber.java.ht.Le;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

class ClydeTest {

    GhostMapParser mapParser;
    LevelFactory levelFactory;
    GhostFactory ghostFactory;
    BoardFactory boardFactory;
    PacManSprites pacManSprites;
    PlayerFactory playerFactory;
    DefaultPointCalculator pointCalculator;

    /*
    * Used to set the map parser,
    * set the map parsing needs three parameters: difficulty, level, ghost.
    * The LevelFactory object takes the PacManSprites, GhostFactory, DefaultCalculator objects as parameters.
    *  You need to create PacManSprites, DefaultPointCalculator objects first.
    * BoardFactory, GhostFactory, and LevelFactory are created after the characters are displayed.
    * Finally, GhostMapParser
     */

    @BeforeEach
    void setUp(){
        pacManSprites = new PacManSprites(); // 角色显示
        pointCalculator = new DefaultPointCalculator();//积分计算
        boardFactory = new BoardFactory(pacManSprites);//生产游戏场景
        ghostFactory = new GhostFactory(pacManSprites); //生成鬼
        playerFactory = new PlayerFactory(pacManSprites);
        levelFactory = new LevelFactory(pacManSprites,ghostFactory,pointCalculator);//关卡生成
        mapParser = new GhostMapParser(levelFactory,boardFactory,ghostFactory);//地图解析
    }

    @Test
    @DisplayName("player不存在")
    @Order(1)
    void noPlayerTest(){
        List<String> txtMap = Lists.newArrayList(
            "#.###.######",
            "####.#C.####",
            "###.###.####",
            "############"
        );
        Level level= mapParser.parseMap(txtMap); //接收字符串，返回level对象
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class,level.getBoard());
        //act
        Optional<Direction> opt = clyde.nextAiMove();
        //assert
        assertThat(opt.isPresent()).isFalse();
    }

    @Test
    @DisplayName("player存在，但是Clyde无法到达Player")
    @Order(2)
    void noPathTest(){
        //Arrange
        List<String> txtMap = Lists.newArrayList(
            "#####..#####",
            "#P.#########",
            "###.#C######",
            "############"
        );
        Level level = mapParser.parseMap(txtMap);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class,level.getBoard());
        //创建player
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);
        //act
        Optional<Direction> opt = clyde.nextAiMove();
        //assert
        assertThat(opt.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Clyde与Player可达并且距离小于8")
    @Order(3)
    void pathLessThanEightTest(){
        //Arrange
        List<String> txtMap = Lists.newArrayList(
          "############",
          "###P########",
          "###..#######",
          "####.C.#####"
        );
        Level level = mapParser.parseMap(txtMap);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class,level.getBoard());
        //创建player
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);
        //act
        Optional<Direction> opt =clyde.nextAiMove();
        //assert
        assertThat(opt.get()).isEqualTo(Direction.EAST);
    }

    @Test
    @DisplayName("Clyde与Player可达并且距离大于8")
    @Order(4)
    void pathGreaterThanEightTest(){
        //Arrange
        List<String> txtMap = Lists.newArrayList(
          "############",
          "#........###",
          "#..#.###.C##",
          "#P.#########"
        );
        Level level = mapParser.parseMap(txtMap);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class,level.getBoard());
        //创建player
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);
        //act
        Optional<Direction> opt = clyde.nextAiMove();
        //assert
        assertThat(opt.get()).isEqualTo(Direction.WEST);

    }

    @Test
    @DisplayName("Clyde和Player可达并且距离为8")
    @Order(5)
    void pathEqualEightTest(){
        List<String> txtMap = Lists.newArrayList(
            "############",
            "##P.......C#",
            "############"
        );
        Level level = mapParser.parseMap(txtMap);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class,level.getBoard());
        //创建player
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);
        //act
        Optional<Direction> opt = clyde.nextAiMove();
        //assert
        assertThat(opt.get()).isEqualTo(Direction.EAST);
    }



}
