package kr.entree.enderwand;

import kr.entree.enderwand.collection.Lists;
import kr.entree.enderwand.command.StringReader;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by JunHyung Lim on 2020-02-07
 */
public class StringReaderTest {
    @Test
    public void simple() {
        StringReader reader = StringReader.of("1 plus 2");
        List<String> data = Lists.of("1", "plus", "2");
        for (int i = 0; i < 3; i++) {
            assertEquals(data.get(i), reader.read());
        }
        assertThrows(NoSuchElementException.class, reader::read);
    }
}
