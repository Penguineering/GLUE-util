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
