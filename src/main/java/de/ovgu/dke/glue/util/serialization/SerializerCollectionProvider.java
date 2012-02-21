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
	private final Serializer[] serializers;

	// those will be filled when needed
	private List<String> formats = null;
	private List<String> schemas = null;

	/**
	 * Initialize the collection from an array of serializers.
	 * 
	 * @param serializers
	 *            The serializers in this collection.
	 */
	public SerializerCollectionProvider(final Serializer[] serializers) {
		this.serializers = serializers;
	}

	/**
	 * Get the serializers in this collection.
	 * 
	 * @return An array of SerializerS
	 */
	public Serializer[] getSerializers() {
		return serializers;
	}

	@Override
	public List<String> availableFormats() {
		if (formats == null) {
			formats = new ArrayList<String>();

			for (int i = 0; i < serializers.length; i++)
				formats.add(serializers[i].getFormat());

			formats = Collections.unmodifiableList(formats);
		}
		return formats;
	}

	@Override
	public List<String> getSchemas(String format) {
		if (schemas == null) {
			schemas = new ArrayList<String>();

			for (int i = 0; i < serializers.length; i++)
				schemas.add(serializers[i].getSchema());

			schemas = Collections.unmodifiableList(schemas);
		}
		return schemas;
	}

	@Override
	public Serializer getSerializer(String format, String schema)
			throws SerializationException {
		for (int i = 0; i < serializers.length; i++) {
			final Serializer ser = serializers[i];
			if (ser.getFormat().equals(format)
					&& ser.getSchema().equals(schema))
				return ser;
		}

		throw new SerializationException(
				"Provider does not contain serializer for format " + format
						+ " and schema " + schema + "!");
	}

}