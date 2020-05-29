//package opennlp.tools.cmdline;
//
//
//import opennlp.tools.chunker.ChunkerFactory;
//import opennlp.tools.chunker.ChunkerME;
//import opennlp.tools.chunker.ChunkerModel;
//import opennlp.tools.cmdline.chunker.ChunkerTrainerTool;
//import opennlp.tools.util.TrainingParameters;
//import opennlp.tools.util.model.ModelUtil;
//import org.junit.Assert;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//
//@Ignore
//public class CmdLineUtilTest {
//    @Test(expected = TerminateToolException.class)
//    public void testCIFDoesntExist(){
//        File existingFile = new File("sampledata2");
//        CmdLineUtil.checkInputFile("sampledata2", existingFile);
//    }
//
//    @Test
//    public void testCIFValidFile(){
//        File existingFile = new File("sampledata");
//        CmdLineUtil.checkInputFile("sampledata", existingFile);
//    }
//
//    @Test(expected = TerminateToolException.class)
//    public void testCIFIsADirectory(){
//        File directory = new File("src");
//        CmdLineUtil.checkInputFile("src", directory);
//    }
//
//    @Test(expected =  TerminateToolException.class)
//    public void testCIFNoPermissions(){
//        File fstab = new File("unreadablefile");
//        CmdLineUtil.checkInputFile("unreadablefile", fstab);
//    }
//
//    @Test(expected = TerminateToolException.class)
//    public void testOutputDir(){
//        File directory = new File("src");
//        CmdLineUtil.checkOutputFile("src", directory);
//    }
//
//    @Test(expected = TerminateToolException.class)
//    public void testOutputNoPermission(){
//        File fstab = new File("unreadablefile");
//        CmdLineUtil.checkOutputFile("unreadablefile", fstab);
//    }
//
//    @Test(expected = TerminateToolException.class)
//    public void testOutputInvalidParent(){
//        File fstab = new File("nonexistentplace/newfile");
//        CmdLineUtil.checkOutputFile("nonexistent", fstab);
//    }
//
//    @Test(expected = TerminateToolException.class)
//    public void testopenInFile(){
//        File invalidfile = new File("sampledata2");
//        CmdLineUtil.openInFile(invalidfile);
//    }
//
//    @Test(expected = TerminateToolException.class)
//    public void testcreateInputStreamFactory(){
//        File invalidfile = new File("sampledata2");
//        CmdLineUtil.openInFile(invalidfile);
//    }
//
//
//    @Test
//    public void testGetParameterIndex() {
//        String param="-name";
//        String[] args = {"-name", "b", "c"};
//        Assert.assertEquals(0, CmdLineUtil.getParameterIndex("-name", args));
//
//    }
//
//    @Test
//    public void testGetParameterIndex2() {
//        String param="-name";
//        String[] args = {"name", "b", "c"};
//        Assert.assertEquals(-1, CmdLineUtil.getParameterIndex("-name", args));
//
//    }
//
//    @Test
//    public void testGetParameter1() {
//        String param="-name";
//        String[] args = {"-name", "b", "c"};
//        Assert.assertEquals("b", CmdLineUtil.getParameter(param, args));
//    }
//
//    @Test
//    public void testGetParameter2(){
//        String param="-noname";
//        String[] args = {"-name", "b", "c"};
//        Assert.assertEquals(null, CmdLineUtil.getParameter(param, args));
//    }
//
//    @Test
//    public void testGetIntParameter(){
//        String param="-number";
//        String[] args = {"-name", "abc"};
//        Assert.assertEquals(null, CmdLineUtil.getIntParameter(param, args));
//    }
//
//    @Test
//    public void testHandleDoubleParameter(){
//        String param="-number";
//        String[] args = {"-name", "abc"};
//        Assert.assertEquals(null, CmdLineUtil.getDoubleParameter(param, args));
//    }
//
//    @Test(expected = TerminateToolException.class)
//    public void testCheckLanugageCode(){
//        String randomlang = "random101";
//        CmdLineUtil.checkLanguageCode(randomlang);
//    }
//
//    @Test
//    public void testContainsParam(){
//        String contains = "-invalid";
//        String[] args = {"-name", "a", "-ab", "b", "-c"};
//        Assert.assertEquals(false, CmdLineUtil.containsParam(contains, args));
//    }
//
//    @Test(expected = TerminateToolException.class)
//    public void testhandleStdinIoError(){
//        try {
//            BufferedWriter bw = new BufferedWriter(new FileWriter("/"));
//        } catch (IOException e) {
//            CmdLineUtil.handleStdinIoError(e);
//        }
//    }
//
//    @Test(expected = TerminateToolException.class)
//    public void testhandleCreateObjectStreamError(){
//        try {
//            BufferedWriter bw = new BufferedWriter(new FileWriter("/"));
//        } catch (IOException e) {
//            CmdLineUtil.handleCreateObjectStreamError(e);
//        }
//    }
//
//    @Test
//    public void testloadTrainingParameters(){
//        CmdLineUtil.loadTrainingParameters("sampledata", true);
//    }
//
//    @Test(expected = TerminateToolException.class)
//    public void testWriteModel(){
//
//        ChunkerTrainerTool c = new ChunkerTrainerTool();
//        String[] args = {};
//        c.run(null, new String[]{"ParserTrainer", "-headRules", "headRulesFile", "-parserType",
//                "TREEINSERT", "-lang", "English", "-model", "", "-data", "sampledata"});
//    }
//
//
//}
