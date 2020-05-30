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

package opennlp.tools.util.featuregen;

import opennlp.tools.util.InvalidFormatException;
import org.w3c.dom.Element;

import java.util.Map;

public class DocumentBeginFeatureGeneratorFactory
        extends GeneratorFactory.AbstractXmlFeatureGeneratorFactory
        implements GeneratorFactory.XmlFeatureGeneratorFactory {

    public DocumentBeginFeatureGeneratorFactory() {
        super();
    }

    @Deprecated // TODO: (OPENNLP-1174) just remove when back-compat is no longer needed
    static void register(Map<String, GeneratorFactory.XmlFeatureGeneratorFactory> factoryMap) {
        factoryMap.put("docbegin", new DocumentBeginFeatureGeneratorFactory());
    }

    @Deprecated // TODO: (OPENNLP-1174) just remove when back-compat is no longer needed
    public AdaptiveFeatureGenerator create(Element generatorElement,
                                           FeatureGeneratorResourceProvider resourceManager) {
        return new DocumentBeginFeatureGenerator();
    }

    @Override
    public AdaptiveFeatureGenerator create() throws InvalidFormatException {
        return new DocumentBeginFeatureGenerator();
    }
}
