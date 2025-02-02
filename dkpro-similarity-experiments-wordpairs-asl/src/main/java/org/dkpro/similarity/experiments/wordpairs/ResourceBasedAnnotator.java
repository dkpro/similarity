/*******************************************************************************
 * Copyright 2013
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
 ******************************************************************************/
package org.dkpro.similarity.experiments.wordpairs;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Level;
import org.dkpro.similarity.algorithms.api.SimilarityException;
import org.dkpro.similarity.algorithms.api.TermSimilarityMeasure;
import org.dkpro.similarity.algorithms.api.TextSimilarityMeasure;
import org.dkpro.similarity.type.SemanticRelatedness;
import org.dkpro.similarity.type.WordPair;

public class ResourceBasedAnnotator 
    extends JCasAnnotator_ImplBase
{

    public final static String SR_RESOURCE = "SemanticRelatednessResource";
    @ExternalResource(key = SR_RESOURCE)
    protected TextSimilarityMeasure measure;
    
    @Override
	public void process(JCas jcas)
        throws AnalysisEngineProcessException
    {

        double semRelValue = TermSimilarityMeasure.NOT_FOUND;

        String term1 = null;
        String term2 = null;

        int i = 0;
        for (WordPair wp : JCasUtil.select(jcas, WordPair.class)) {
            i++;
            if (i % 10 == 0) {
                getContext().getLogger().log(Level.CONFIG, measure.getName() + " processing word pair " + i);
            }

            term1 = wp.getWord1();
            term2 = wp.getWord2();

            // are the terms initialized?
            if (term1 == null || term2 == null) {
                throw new AnalysisEngineProcessException(new Throwable("Could not initialize terms."));
            }

            // compute relatedness
            try {
                semRelValue = measure.getSimilarity(term1, term2);
            } catch (SimilarityException e) {
                throw new AnalysisEngineProcessException(e);
            }

            // write a SR annotation (features: measure type & value)
            SemanticRelatedness semRelAnnotation = new SemanticRelatedness(jcas);
            semRelAnnotation.setMeasureType(measure.getName());
            semRelAnnotation.setMeasureName(measure.getName());
            semRelAnnotation.setRelatednessValue(semRelValue);
            semRelAnnotation.setTerm1(term1);
            semRelAnnotation.setTerm2(term2);
            semRelAnnotation.setWordPair(wp);
            semRelAnnotation.addToIndexes();
        }
    }
}