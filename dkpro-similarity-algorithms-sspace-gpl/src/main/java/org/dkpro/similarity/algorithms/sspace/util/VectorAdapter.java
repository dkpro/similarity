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
package org.dkpro.similarity.algorithms.sspace.util;

import edu.ucla.sspace.vector.DoubleVector;
import no.uib.cipr.matrix.Vector;

/**
 * Adapt SSpace {@link edu.ucla.sspace.vector.Vector} to {@link Vector}.
 *
 * @author Richard Eckart de Castilho
 *
 * @param <V> conrete type of {@link Vector}
 */
public class VectorAdapter<V extends Vector> implements DoubleVector
{
	private V wrapped;

	public VectorAdapter(V aVector)
	{
		wrapped = aVector;
	}

	@Override
	public int length()
	{
		return wrapped.size();
	}

	@Override
	public void set(int aParamInt, Number aParamNumber)
	{
		wrapped.set(aParamInt, aParamNumber.doubleValue());
	}

	@Override
	public double add(int aParamInt, double aParamDouble)
	{
		wrapped.add(aParamInt, aParamDouble);
		return wrapped.get(aParamInt);
	}

	@Override
	public double get(int aParamInt)
	{
		return wrapped.get(aParamInt);
	}

	@Override
	public Double getValue(int aParamInt)
	{
		return wrapped.get(aParamInt);
	}

	@Override
	public void set(int aParamInt, double aParamDouble)
	{
		wrapped.set(aParamInt, aParamDouble);
	}

	@Override
	public double[] toArray()
	{
		throw new UnsupportedOperationException();
	}

	public static <V extends Vector > DoubleVector create(V aVector)
	{
		return new VectorAdapter<V>(aVector);
	}

    @Override
    public double magnitude()
    {
        throw new UnsupportedOperationException();
    }
}
