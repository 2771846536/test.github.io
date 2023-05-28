package nl.tudelft.jpacman.level;
import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.*;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


class MapParserTest {
    public MapParser mapParser;
    private final LevelFactory levelCreator = mock(LevelFactory.class);
    private final BoardFactory boardFactory = mock(BoardFactory.class);

    @BeforeEach
    void setup() {
        mapParser = new MapParser(levelCreator, boardFactory);
        when(boardFactory.createGround()).thenReturn(mock(Square.class));//墙
        when(boardFactory.createWall()).thenReturn(mock(Square.class));//地面
        when(levelCreator.createGhost()).thenReturn(mock(Ghost.class));//鬼
        when(levelCreator.createPellet()).thenReturn(mock(Pellet.class));//豆子
    }

    @Test
    @Order(1)
    @DisplayName("正常文件")
    void normalFile() throws IOException {
        String filename = "/simplemap.txt";
        mapParser.parseMap(filename);

        verify(boardFactory, times(4)).createGround();
        verify(boardFactory, times(2)).createWall();
        verify(levelCreator, times(1)).createGhost();
    }

    @Test
    @Order(2)
    @DisplayName("无文件名")
    void noFile() {
        assertThatThrownBy(() -> {
            mapParser.parseMap((String) null);
        }).isInstanceOf((NullPointerException.class));
    }

    @Test
    @Order(3)
    @DisplayName("不存在文件")
    void notExistFile() {
        String filename = "/notExistFile.txt";
        assertThatThrownBy(() -> {
            mapParser.parseMap(filename);
        }).isInstanceOf(PacmanConfigurationException.class).hasMessage("Could not get resource for: " + filename);
    }


    @Test
    @Order(4)
    @DisplayName("不可辨别文件")
    void unrecognizedMap() {
        String filename = "/unrecognizedcharMap.txt";
        assertThatThrownBy(() -> {
            mapParser.parseMap(filename);
        }).isInstanceOf(PacmanConfigurationException.class)
            .hasMessage("Invalid character at 0,0: A");
    }

    @Test
    @Order(5)
    @DisplayName("只有player")
    void onlyPlayer() throws IOException {
        String filename = "/simplemapOfPlayer.txt";
        mapParser.parseMap(filename);
        verify(boardFactory, times(3)).createGround();
        verify(boardFactory, times(3)).createWall();
        verify(levelCreator, times(0)).createGhost();
    }
}
