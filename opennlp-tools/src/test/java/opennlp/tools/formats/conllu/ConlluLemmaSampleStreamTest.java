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

package opennlp.tools.formats.conllu;


import opennlp.tools.formats.ResourceAsStreamFactory;
import opennlp.tools.lemmatizer.LemmaSample;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.ObjectStream;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ConlluLemmaSampleStreamTest {


    @Test
    public void testParseSpanishS300() throws IOException {
        InputStreamFactory streamFactory =
                new ResourceAsStreamFactory(ConlluStreamTest.class, "es-ud-sample.conllu");

        try (ObjectStream<LemmaSample> stream = new ConlluLemmaSampleStream(
                new ConlluStream(streamFactory), ConlluTagset.U)) {

            LemmaSample predicted = stream.read();
            System.out.println(predicted);
            Assert.assertEquals("digám+tú+él", predicted.getLemmas()[0]);
            Assert.assertEquals("la", predicted.getTokens()[3]);
            Assert.assertEquals("el", predicted.getLemmas()[3]);
        }
    }
}
