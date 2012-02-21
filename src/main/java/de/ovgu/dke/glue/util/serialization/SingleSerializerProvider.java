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

import java.util.Collections;
import java.util.List;

import de.ovgu.dke.glue.api.serialization.SerializationException;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;

/**
 * Provider for a single serializer.
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 *
 */
public class SingleSerializerProvider implements SerializationProvider {
	private final Serializer serializer;

	/**
	 * Create the provider.
	 * @param serializer The serializer which will be provided.
	 */
	public SingleSerializerProvider(final Serializer serializer) {
		if (serializer == null)
			throw new NullPointerException("Serializer may not be null!");
		this.serializer = serializer;
	}

	@Override
	public List<String> availableFormats() {
		return Collections.singletonList(serializer.getFormat());
	}

	@Override
	public List<String> getSchemas(String format) {
		return Collections.singletonList(serializer.getSchema());
	}

	@Override
	public Serializer getSerializer(String format, String schema)
			throws SerializationException {
		if (serializer.getFormat().equals(format)
				&& serializer.getSchema().equals(schema))
			return serializer;

		throw new SerializationException(
				"Provider does not contain serializer for format " + format
						+ " and schema " + schema + "!");
	}
}
