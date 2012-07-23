/*
 * Copyright 2012 Stefan Haun, Thomas Low, Sebastian Stober, Andreas Nürnberger
 * 
 *      Data and Knowledge Engineering Group, 
 * 		Faculty of Computer Science,
 *		Otto-von-Guericke University,
 *		Magdeburg, Germany
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.ovgu.dke.glue.util.serialization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.jcip.annotations.Immutable;
import de.ovgu.dke.glue.api.serialization.SerializationException;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;

/**
 * A collection of different serializers.
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 * 
 */
@Immutable
public class SerializerCollectionProvider implements SerializationProvider {
	/**
	 * Get a serializer collection provider from a list of serializers.
	 * 
	 * @param serializers
	 *            List of serializers; may not be {@code null}
	 * @throws NullPointerException
	 *             if the serializers parameter is {@code null}
	 */
	public static SerializationProvider of(final List<Serializer> serializers) {
		return new SerializerCollectionProvider(serializers);
	}

	private final List<Serializer> serializers;

	// those will be filled when needed
	private List<String> formats = null;

	/**
	 * Create a serializer collection provider from a list of serializers
	 * 
	 * @param serializers
	 *            List of serializers; may not be {@code null}
	 * @throws NullPointerException
	 *             if the serializers parameter is {@code null}
	 */
	private SerializerCollectionProvider(final List<Serializer> serializers) {
		if (serializers == null)
			throw new NullPointerException("Serializer list may not be null!");

		this.serializers = new ArrayList<Serializer>(serializers);
	}

	@Override
	public List<String> availableFormats() {
		if (formats == null)
			synchronized (this) {
				formats = new ArrayList<String>();

				for (final Serializer s : serializers) {
					if (s == null)
						continue;

					final String format = s.getFormat();
					if (!formats.contains(format))
						formats.add(format);
				}

				formats = Collections.unmodifiableList(formats);
			}
		return formats;
	}

	@Override
	public Serializer getSerializer(String format)
			throws SerializationException {
		if (format == null)
			throw new NullPointerException("Format parameter may not be null!");

		for (final Serializer ser : serializers)
			if (ser != null && ser.getFormat().equals(format))
				return ser;

		// TODO solve this via return type!
		throw new SerializationException(
				"Provider does not contain serializer for format " + format
						+ "!");
	}
}
