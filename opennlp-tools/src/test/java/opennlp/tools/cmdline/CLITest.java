/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package opennlp.tools.cmdline;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CLITest {

    private final SecurityManager originalSecurityManager = System.getSecurityManager();

    @Before
    public void installNoExitSecurityManager() {
        System.setSecurityManager(new NoExitSecurityManager());
    }

    /**
     * Ensure the main method does not fail to print help message.
     */
    @Test
    public void testMainHelpMessage() {

        try {
            CLI.main(new String[]{});
        } catch (ExitException e) {
            Assert.assertEquals(0, e.status());
        }
    }

    /**
     * Ensure the main method prints error and returns 1.
     */
    @Test
    public void testUnknownToolMessage() {
        try {
            CLI.main(new String[]{"unknown name"});
        } catch (ExitException e) {
            Assert.assertEquals(1, e.status());
        }
    }

    /**
     * Ensure the tool checks the parameter and returns 1.
     */
    @Test
    public void testToolParameterMessage() {
        try {
            CLI.main(new String[]{"DoccatTrainer", "-param", "value"});
        } catch (ExitException e) {
            Assert.assertEquals(1, e.status());
        }
    }

    /**
     * Ensure the main method prints error and returns -1
     */
    @Test
    public void testUnknownFileMessage() {
        try {
            CLI.main(new String[]{"Doccat", "unknown.model"});
        } catch (ExitException e) {
            Assert.assertEquals(-1, e.status());
        }
    }

    /**
     * Ensure all tools do not fail printing help message;
     */
    @Test
    public void testHelpMessageOfTools() {

        for (String toolName : CLI.getToolNames()) {
            System.err.println("-> ToolName" + toolName);
            try {
                CLI.main(new String[]{toolName, "help"});
            } catch (ExitException e) {
                Assert.assertEquals(0, e.status());
            }
        }
    }

    @Test()
    public void testMandatoryArgument() {
        // System.setSecurityManager(null);
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setErr(new PrintStream(myOut));
        try {
            CLI.main(new String[]{"ParserTrainer", "-data"}); // madatory arg value for -data missing!
        } catch (Exception ex) {
            System.out.println("Caught exception");
        }
        final String standardErr = myOut.toString();
        System.out.println(standardErr);
        Boolean res = standardErr.contains("be always be even");
        Assert.assertEquals(true, res);
    }

    @Test()
    public void testNonExistentDataFile() {
        // System.setSecurityManager(null);
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setErr(new PrintStream(myOut));
        try {
            CLI.main(new String[]{"ParserTrainer", "-headRules", "headRulesFile", "-lang",
                    "English", "-model", "modelname", "-data", "sampledatafalse"});
            // the data file doesn't exist!
        } catch (Exception ex) {
            System.out.println("Caught exception");
        }
        final String standardErr = myOut.toString();
        System.out.println(standardErr);
        Boolean res = standardErr.contains("The Data file does not exist!");
        Assert.assertEquals(true, res);
    }

    @Test()
    public void testInvalidParserType() {
        // System.setSecurityManager(null);
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setErr(new PrintStream(myOut));
        try {
            CLI.main(new String[]{"ParserTrainer", "-headRules", "headRulesFile", "-parserType",
                    "SampleModel", "-lang", "English", "-model", "modelname", "-data", "sampledata"});
            // madatory arg value for -data missing!
        } catch (Exception ex) {
            System.out.println("Caught exception");
        }
        final String standardErr = myOut.toString();
        System.out.println(standardErr);
        Boolean res = standardErr.contains("is invalid");
        Assert.assertEquals(true, res);
    }

    @Test()
    public void testValidParserType() {
        // System.setSecurityManager(null);
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setErr(new PrintStream(myOut));
        try {
            CLI.main(new String[]{"ParserTrainer", "-headRules", "headRulesFile", "-parserType",
                    "TREEINSERT", "-lang", "English", "-model", "", "-data", "sampledata"});
            // a valid set of arguments; should be accepted
        } catch (Exception ex) {
            System.out.println("Caught exception");
        }
        final String standardErr = myOut.toString();
        Boolean res = standardErr.contains("is invalid");
        Assert.assertEquals(false, res);
    }

    @Test
    public void testAdditionalParams() {
        // System.setSecurityManager(null);
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setErr(new PrintStream(myOut));
        try {
            CLI.main(new String[]{"ParserTrainer", "-headRules", "headRulesFile", "-parserType", "TREEINSERT",
                    "-lang", "English", "-model", "", "-data", "sampledata", "-fakearg", "fakevalue"});
            // fake/unrecognizable arguments added
        } catch (Exception ex) {
            System.out.println("Caught exception");
        }
        final String standardErr = myOut.toString();
        Boolean res = standardErr.contains("Unrecognized parameters");
        Assert.assertEquals(true, res);
    }

    @After
    public void restoreSecurityManager() {
        System.setSecurityManager(originalSecurityManager);
    }

    private static class ExitException extends SecurityException {
        private final int status;

        public ExitException(int status) {
            this.status = status;
        }

        int status() {
            return status;
        }
    }

    /**
     * A <code>SecurityManager</code> which prevents System.exit anything else is allowed.
     */
    private static class NoExitSecurityManager extends SecurityManager {

        @Override
        public void checkPermission(Permission perm) {
        }

        @Override
        public void checkPermission(Permission perm, Object context) {
        }

        @Override
        public void checkExit(int status) {
            super.checkExit(status);

            throw new ExitException(status);
        }
    }

}

