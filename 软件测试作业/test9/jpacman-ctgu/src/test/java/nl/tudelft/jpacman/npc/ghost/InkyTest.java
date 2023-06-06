package nl.tudelft.jpacman.npc.ghost;

import io.cucumber.java.ht.Le;
import io.cucumber.java.sl.In;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.assertj.core.api.InstanceOfAssertFactories.map;

class InkyTest {
    GhostMapParser mapParser;
    LevelFactory levelFactory;
    GhostFactory ghostFactory;
    BoardFactory boardFactory;
    PacManSprites pacManSprites;
    PlayerFactory playerFactory;
    DefaultPointCalculator pointCalculator;

    /*
    * Used to set the map parser
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
    @DisplayName("Blinky 不存在")
    @Order(1)
    void noBlinkyTest(){
        List<String> txtMap = Lists.newArrayList(
          "############",
          "###.PI######",
          "############"
        );
        Level level = mapParser.parseMap(txtMap);
        //棋盘中找到inky对象、blinky对象
        Inky inky = Navigation.findUnitInBoard(Inky.class,level.getBoard());
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class,level.getBoard());
        //创建player
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);
        assertThat(blinky).isNull();
        //act
        Optional<Direction> opt = inky.nextAiMove();
        //assert
        assertThat(opt.isPresent()).isFalse();
    }

    @Test
    @DisplayName("player不存在")
    @Order(2)
    void noPlayTest(){
        List<String> txtMap = Lists.newArrayList(
          "############",
          "##B...I#####",
          "############"
        );
        Level level = mapParser.parseMap(txtMap);
        //棋盘中找到inky对象
        Inky inky = Navigation.findUnitInBoard(Inky.class,level.getBoard());
        assertThat(inky).isNotNull();
        assertThat(level.isAnyPlayerAlive()).isFalse();//没有player对象
        //act
        Optional<Direction> opt = inky.nextAiMove();
        //assert
        assertThat(opt.isPresent()).isFalse();

    }

    @Test
    @DisplayName("inky对象与目的地之间无路径")
    @Order(3)
    void inkyNoPathTest(){
        List<String> txtMap = Lists.newArrayList(
            "##############",
            "#.I....#.....B",
            "############P#"
        );
        Level level = mapParser.parseMap(txtMap);
        //找到inky与blinky对象
        Inky inky = Navigation.findUnitInBoard(Inky.class,level.getBoard());
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class,level.getBoard());
        assertThat(inky).isNotNull();
        assertThat(blinky).isNotNull();
        //创建Player
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);
        //act
        Optional<Direction> opt = inky.nextAiMove();
        //assert
        assertThat(opt.isPresent()).isFalse();

    }

    @Test
    @DisplayName("inky对象与目的地之间有路径")
    @Order(4)
    void inkyPathTest(){
        List<String> txtMap = Lists.newArrayList(
            "##############",
            "#.I..........B",
            "##########    ",
            "############P#"
        );
        Level level = mapParser.parseMap(txtMap);
        //找到inky和Blinky对象
        Inky inky = Navigation.findUnitInBoard(Inky.class,level.getBoard());
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class,level.getBoard());
        assertThat(inky).isNotNull();
        assertThat(blinky).isNotNull();
        //创建player
        Player player = playerFactory.createPacMan();
        player.setDirection(Direction.WEST);
        level.registerPlayer(player);
        //act
        Optional<Direction> opt = inky.nextAiMove();
        //assert
        assertThat(opt.get()).isEqualTo(Direction.EAST);
    }

    @Test
    @DisplayName("blinky对象和player对象前2格空格之间无可达路径")
    @Order(5)
    void noPathTest(){
        List<String> txtMap = Lists.newArrayList(
            "##############",
            "#.I...#......B",
            "##########  P#"
        );
        Level level = mapParser.parseMap(txtMap);
        //找到inky与blinky对象
        Inky inky = Navigation.findUnitInBoard(Inky.class,level.getBoard());
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class,level.getBoard());
        assertThat(inky).isNotNull();
        assertThat(blinky).isNotNull();
        //创建player
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);
        //act
        Optional<Direction> opt = inky.nextAiMove();
        //assert
        assertThat(opt.isPresent()).isFalse();
    }


}
