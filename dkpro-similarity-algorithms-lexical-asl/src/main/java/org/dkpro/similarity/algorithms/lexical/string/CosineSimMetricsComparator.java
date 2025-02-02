/**
 * Copyright 2012-2016
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package org.dkpro.similarity.algorithms.lexical.string;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.dkpro.similarity.algorithms.api.SimilarityException;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;

/**
 * Cosine similarity as implemented by the 
 * {@link SimMetricsComparator_ImplBase SimMetrics} library.
 */
public class CosineSimMetricsComparator
	extends SimMetricsComparator_ImplBase
{
    private final StringMetric similarityMeasure;
    
    public CosineSimMetricsComparator() {
        this.similarityMeasure = StringMetrics.cosineSimilarity();
    }

    @Override
    public double getSimilarity(String s1, String s2)
        throws SimilarityException
    {
        return similarityMeasure.compare(s1, s2);
    }

    @Override
    public double getSimilarity(Collection<String> stringList1, Collection<String> stringList2)
        throws SimilarityException
    {
		if (stringList1.size() == 0 || stringList2.size() == 0) {
			return 0.0;
        }

        String concatenatedString1 = StringUtils.join(stringList1, " ");
        String concatenatedString2 = StringUtils.join(stringList2, " ");

        if (concatenatedString1.length() == 0 || concatenatedString2.length() == 0) {
            return 0.0;
        }

        double similarity = similarityMeasure.compare(concatenatedString1, concatenatedString2);

        return similarity;
    }
}