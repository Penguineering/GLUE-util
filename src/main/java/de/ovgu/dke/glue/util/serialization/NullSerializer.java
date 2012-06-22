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

import de.ovgu.dke.glue.api.serialization.SerializationException;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;

/**
 * This serializer just returns the original object instance.
 * 
 * <p>
 * WARNING: The format may be arbitrarily selected, which can result in severe
 * runtime errors in a transport implementation. USE WITH CAUTION! In most cases
 * this class is not what you need.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 */
public class NullSerializer implements Serializer {
	/**
	 * Create a null serializer with the provided format.
	 * 
	 * @param format
	 *            The format, defaults to {@link SerializationProvider.JAVA} if
	 *            {@code null}
	 * @return a NullSerializer for the selected format
	 */
	public static NullSerializer of(final String format) {
		return new NullSerializer(format == null ? SerializationProvider.JAVA
				: format);
	}

	private final String format;

	private NullSerializer(final String format) {
		this.format = format;
	}

	@Override
	public String getFormat() {
		return format;
	}

	@Override
	public Object serialize(Object o) throws SerializationException {
		return o;
	}

	@Override
	public Object deserialize(Object o) throws SerializationException {
		return o;
	}

}
