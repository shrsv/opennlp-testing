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

package opennlp.tools.postag;

import opennlp.tools.ml.model.Event;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.ObjectStreamUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the {@link POSSampleEventStream} class.
 */
public class POSSampleEventStreamTest {

    /**
     * Tests that the outcomes for a single sentence match the
     * expected outcomes.
     */
    @Test
    public void testOutcomesForSingleSentence() throws Exception {
        String sentence = "That_DT sounds_VBZ good_JJ ._.";

        POSSample sample = POSSample.parse(sentence);

        try (ObjectStream<Event> eventStream = new POSSampleEventStream(
                ObjectStreamUtils.createObjectStream(sample))) {
            Assert.assertEquals("DT", eventStream.read().getOutcome());
            Assert.assertEquals("VBZ", eventStream.read().getOutcome());
            Assert.assertEquals("JJ", eventStream.read().getOutcome());
            Assert.assertEquals(".", eventStream.read().getOutcome());
            Assert.assertNull(eventStream.read());
        }
    }
}
