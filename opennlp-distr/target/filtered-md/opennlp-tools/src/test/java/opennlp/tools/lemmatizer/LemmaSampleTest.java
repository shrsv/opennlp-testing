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

package opennlp.tools.lemmatizer;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class LemmaSampleTest {

    private static String[] createSentence() {
        return new String[]{"Forecasts", "for", "the", "trade", "figures",
                "range", "widely", "."};
    }

    private static String[] createTags() {

        return new String[]{"NNS", "IN", "DT", "NN", "NNS", "VBP", "RB", "."};
    }

    private static String[] createLemmas() {
        return new String[]{"Forecast", "for", "the", "trade", "figure", "range",
                "widely", "."};
    }

    public static LemmaSample createGoldSample() {
        return new LemmaSample(createSentence(), createTags(), createLemmas());
    }

    public static LemmaSample createPredSample() {
        String[] lemmas = createLemmas();
        lemmas[5] = "figure";
        return new LemmaSample(createSentence(), createTags(), lemmas);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParameterValidation() {
        new LemmaSample(new String[]{""}, new String[]{""},
                new String[]{"test", "one element to much"});
    }

    @Test
    public void testLemmaSampleSerDe() throws IOException {
        LemmaSample lemmaSample = createGoldSample();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(byteArrayOutputStream);
        out.writeObject(lemmaSample);
        out.flush();
        byte[] bytes = byteArrayOutputStream.toByteArray();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInput objectInput = new ObjectInputStream(byteArrayInputStream);

        LemmaSample deSerializedLemmaSample = null;
        try {
            deSerializedLemmaSample = (LemmaSample) objectInput.readObject();
        } catch (ClassNotFoundException e) {
            // do nothing
        }

        Assert.assertNotNull(deSerializedLemmaSample);
        Assert.assertArrayEquals(lemmaSample.getLemmas(), deSerializedLemmaSample.getLemmas());
        Assert.assertArrayEquals(lemmaSample.getTokens(), deSerializedLemmaSample.getTokens());
        Assert.assertArrayEquals(lemmaSample.getTags(), deSerializedLemmaSample.getTags());
    }

    @Test
    public void testRetrievingContent() {
        LemmaSample sample = new LemmaSample(createSentence(), createTags(), createLemmas());

        Assert.assertArrayEquals(createSentence(), sample.getTokens());
        Assert.assertArrayEquals(createTags(), sample.getTags());
        Assert.assertArrayEquals(createLemmas(), sample.getLemmas());
    }

    @Test
    public void testToString() throws IOException {

        LemmaSample sample = new LemmaSample(createSentence(), createTags(),
                createLemmas());
        String[] sentence = createSentence();
        String[] tags = createTags();
        String[] lemmas = createLemmas();

        StringReader sr = new StringReader(sample.toString());
        BufferedReader reader = new BufferedReader(sr);
        for (int i = 0; i < sentence.length; i++) {
            String line = reader.readLine();
            String[] parts = line.split("\t");
            Assert.assertEquals(3, parts.length);
            Assert.assertEquals(sentence[i], parts[0]);
            Assert.assertEquals(tags[i], parts[1]);
            Assert.assertEquals(lemmas[i], parts[2]);
        }
    }

    @Test
    public void testEquals() {
        Assert.assertFalse(createGoldSample() == createGoldSample());
        Assert.assertTrue(createGoldSample().equals(createGoldSample()));
        Assert.assertFalse(createPredSample().equals(createGoldSample()));
        Assert.assertFalse(createPredSample().equals(new Object()));
    }

}
