package opennlp.tools.util;

import opennlp.tools.tokenize.WhitespaceTokenizer;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ParagraphStreamMockTest {
    @Test
    public void testSimpleReading() throws IOException {
        ParagraphStream paraStream = Mockito.mock(ParagraphStream.class);
        // following corresponds to: 1 2 " " " " 4 5 " "
        when(paraStream.read()).thenReturn("1\n2\n", "4\n5\n", null);

        String the_para;
        List<String> para_list = new ArrayList<String>();
        while ((the_para = paraStream.read()) != null)
            para_list.add(the_para);

        Assert.assertEquals("1\n2\n", para_list.get(0));
        Assert.assertEquals("4\n5\n", para_list.get(1));

        verify(paraStream, times(3)).read();
        verify(paraStream, never()).reset();
        verifyNoMoreInteractions(paraStream);
    }
}
