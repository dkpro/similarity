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
package org.dkpro.similarity.algorithms.lexical.uima.ngrams;

import java.io.IOException;
import java.util.Map;

import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.dkpro.similarity.algorithms.lexical.ngrams.CharacterNGramMeasure;
import org.dkpro.similarity.uima.resource.TextSimilarityResourceBase;


public class CharacterNGramResource
	extends TextSimilarityResourceBase
{
	public static final String PARAM_N = "N";
	@ConfigurationParameter(name=PARAM_N, mandatory=true)
	private int n;
	
	public static final String PARAM_IDF_VALUES_FILE = "IdfValuesFile";
	@ConfigurationParameter(name=PARAM_IDF_VALUES_FILE, mandatory=true)
	private String idfValuesFile;
	
	@SuppressWarnings("unchecked")
    @Override
    public boolean initialize(ResourceSpecifier aSpecifier, Map aAdditionalParams)
        throws ResourceInitializationException
    {
        if (!super.initialize(aSpecifier, aAdditionalParams)) {
            return false;
        }

        this.mode = TextSimilarityResourceMode.text;
        
        try {
            measure = new CharacterNGramMeasure(n, idfValuesFile);
        }
        catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
        
        return true;
    }
}
