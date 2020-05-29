package opennlp.tools.cmdline;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class testGenerateManualTool {

    @Test
    public void testManualTool() {
        String[] args = {"output.xml"};
        try {
            GenerateManualTool.main(args);
        } catch (java.io.FileNotFoundException e) {
            System.out.print("Output file not found");
        }
        File f = new File("output.xml");
        double bytes = f.length();

        Assert.assertTrue("File is populated", bytes > 0);
    }
}
