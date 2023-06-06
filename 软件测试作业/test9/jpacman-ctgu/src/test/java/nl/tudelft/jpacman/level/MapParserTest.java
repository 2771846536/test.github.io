package nl.tudelft.jpacman.level;
import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;

import static  org.mockito.Mockito.*;

/*
MapParser中parseMap
parMap(文件路径字符串)->parMap(输入流对象)->parMap(字符串列表)->parMap(二维字符数组)-创建级别 返回Level对象
 */
class MapParserTest {
    public MapParser mapParser;
    private final LevelFactory levelCreator = mock(LevelFactory.class);
    private final BoardFactory boardFactory = mock(BoardFactory.class);

    @BeforeEach
    void setup() {
        mapParser = new MapParser(levelCreator, boardFactory);
        assertThat(mapParser.getBoardCreator()).isEqualTo(boardFactory); //mapParser中的boardFactory是否等于传进去的那个boardFactory
        Level level = mock(Level.class);
        when(levelCreator.createLevel(any(),any(),any())).thenReturn((level));
        //Ground类和Wall类都继承的Square类
        when(boardFactory.createGround()).thenReturn(mock(Square.class));//可占据格子
        when(boardFactory.createWall()).thenReturn(mock(Square.class));//墙
        when(levelCreator.createGhost()).thenReturn(mock(Ghost.class));//鬼
        when(levelCreator.createPellet()).thenReturn(mock(Pellet.class));//豆子
    }

    @Test
    @DisplayName("读取存在且正常的文件")
    @Order(1)
    void normalExistFileTest() throws IOException {
        String filename = "/simplemap.txt";
        mapParser.parseMap(filename);
        verify(levelCreator,times(1)).createLevel(any(),any(),any());//验证等级是否创建正确
        //simplemap文件中有4个格子，2个墙，4个格子中一个鬼，一个player，一个豆子
        verify(boardFactory, times(4)).createGround();//格子
        verify(boardFactory, times(2)).createWall();//墙
        verify(levelCreator, times(1)).createGhost();//鬼
        verify(levelCreator,times(1)).createPellet();//豆子
    }

    @Test
    @DisplayName("没有文件")
    @Order(2)
    void noFileTest() {
        String filename=null;
        assertThatThrownBy(() -> {
            mapParser.parseMap((String) filename);
        }).isInstanceOf((NullPointerException.class));
    }

    @Test
    @DisplayName("读取不存在的文件")
    @Order(3)
    void noExistFileTest() {
        String filename = "/notExistFile.txt";
        assertThatThrownBy(() -> {
            mapParser.parseMap(filename);
        }).isInstanceOf(PacmanConfigurationException.class).hasMessage("Could not get resource for: " + filename);
    }


    @Test
    @DisplayName("读取无法识别的文件")
    @Order(4)
    void unrecognizedMapTest() {
        String filename = "/unrecognizedcharMap.txt";
        assertThatThrownBy(() -> {
            mapParser.parseMap(filename);
        }).isInstanceOf(PacmanConfigurationException.class)
            .hasMessage("Invalid character at 0,0: A");
    }

    @Test
    @DisplayName("读取的地图文件width不等")
    @Order(5)
    void NoRectangularFile(){
        String file="/widthnoequalmap.txt";
        assertThatThrownBy(()->{
            mapParser.parseMap(file);
        }).isInstanceOf(PacmanConfigurationException.class).hasMessage("Input text lines are not of equal width.");
    }
    @Test
    @DisplayName("读取的地图文件没有内容")
    @Order(6)
    void NoContentFile(){
        String file="/nocontentmap.txt";
        assertThatThrownBy(()->{
            mapParser.parseMap(file);
        }).isInstanceOf(PacmanConfigurationException.class).hasMessage("Input text must consist of at least 1 row.");
    }
    @Test
    @DisplayName("读取的地图文件存在空行")
    @Order(7)
    void HaveEmptyRowsFile(){
        String file="/hasemptyrowmap.txt";
        assertThatThrownBy(()->{
            mapParser.parseMap(file);
        }).isInstanceOf(PacmanConfigurationException.class).hasMessage("Input text lines are not of equal width.");
    }

    @Test
    @DisplayName("读取的文件只有player")
    @Order(8)
    void onlyPlayerTest() throws IOException {
        String filename = "/simplemapOfPlayer.txt";
        mapParser.parseMap(filename);
        verify(levelCreator,times(1)).createLevel(any(),any(),any());//验证等级是否创建正确
        verify(boardFactory, times(3)).createGround();
        verify(boardFactory, times(3)).createWall();
        verify(levelCreator, times(0)).createGhost();
        verify(levelCreator,times(1)).createPellet();
    }

}
