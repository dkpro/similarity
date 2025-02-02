/*******************************************************************************
 * Copyright 2012
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.dkpro.similarity.algorithms.style;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.junit.Assert.assertEquals;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasBuilder;
import org.apache.uima.jcas.JCas;
import org.dkpro.core.tokit.BreakIteratorSegmenter;
import org.dkpro.similarity.algorithms.api.JCasTextSimilarityMeasure;
import org.junit.Test;

public class TypeTokenRatioComparatorTest
{
    static final double epsilon = 0.001;

    @Test
    public void run()
        throws Exception
    {
        JCasTextSimilarityMeasure comparator = new TypeTokenRatioComparator();

        AnalysisEngine ae = AnalysisEngineFactory.createEngine(
        		createEngineDescription(BreakIteratorSegmenter.class)
        );

        // First document
        JCasBuilder cb1 = new JCasBuilder(ae.newJCas());
        cb1.add("The quick brown fox jumps over the lazy dog.");
        cb1.close();

        // Second document
        JCasBuilder cb2 = new JCasBuilder(ae.newJCas());
        cb2.add("The quick brown fox jumps over another brown fox.");
        cb2.close();
        
        JCas jcas1 = cb1.getJCas();
        jcas1.setDocumentLanguage("en");
        JCas jcas2 = cb2.getJCas();
        jcas2.setDocumentLanguage("en");
        
        ae.process(jcas1);
        ae.process(jcas2);

        assertEquals(0.9, comparator.getSimilarity(jcas1, jcas2), epsilon);
    }
}
