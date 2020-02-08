package kr.entree.enderwand;

import kr.entree.enderwand.collection.Lists;
import kr.entree.enderwand.command.StringReader;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static kr.entree.enderwand.exception.Exceptions.runCatching;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by JunHyung Lim on 2020-02-07
 */
public class StringReaderTest {
    @Test
    public void simple() {
        String input = "1 plus 2";
        StringReader reader = StringReader.of(input);
        List<String> data = Lists.of("1", "plus", "2");
        for (int i = 0; i < data.size(); i++) {
            int index = i;
            String datum = data.get(index);
            runCatching(() -> assertEquals(datum, reader.read()))
                    .onFailureThrow((ex) -> new IllegalArgumentException("Fail on " + index, ex));
        }
        assertThrows(NoSuchElementException.class, reader::read);
    }

    @Test
    public void spacing() {
        String input = "1 plus 2 ";
        StringReader reader = StringReader.of(input);
        List<String> data = Lists.of("1", "plus", "2", "");
        for (int i = 0; i < data.size(); i++) {
            int index = i;
            String datum = data.get(i);
            runCatching(() -> assertEquals(datum, reader.read()))
                    .onFailureThrow((ex) -> new IllegalArgumentException("Fail on " + index, ex));
        }
        assertThrows(NoSuchElementException.class, reader::read);
    }
}
