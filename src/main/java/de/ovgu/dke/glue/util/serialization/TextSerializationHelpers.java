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

import java.util.Properties;

import de.ovgu.dke.glue.api.serialization.SerializationException;

/**
 * Helper Methods for text serialization.
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 * 
 */
public class TextSerializationHelpers {

	/**
	 * Instance control: this class is static only!
	 */
	private TextSerializationHelpers() {
		super();
		// nothing to be done here
	}

	/**
	 * <p>
	 * Convert a properties object into a text representation.
	 * </p>
	 * <p>
	 * The key-value pairs are encoded into "pairs" of lines:
	 * <p>
	 * <ul>
	 * <li>The first line consists of the number of data lines (N), a space and
	 * the name of the parameter.</li>
	 * <li>The following N lines contain the actual data content, i.e. line
	 * breaks in the data will result in additional data lines.</li>
	 * </ul>
	 * 
	 * @param props
	 *            a properties object containing the parameters
	 * @return a @code{String} containing the text representation (without final
	 *         newline)
	 * @throws NullPointerException
	 *             if the props parameter is @code{null}
	 * @throws ClassCastException
	 *             if props contains keys with are not of type @code{String}
	 */
	public static String encodeProperties(final Properties props) {
		if (props == null)
			throw new NullPointerException("Properties object may not be null!");

		final StringBuilder text = new StringBuilder();

		for (final Object key : props.keySet()) {
			// newline if not empty
			if (text.length() > 0)
				text.append("\n");

			final String value = props.getProperty((String) key);

			// parameter format:
			// line 1: <Number of Data Lines><Space><Name>
			final int lines = value.split("\n").length;

			text.append(lines);
			text.append(" ");
			text.append(key);
			text.append("\n");

			// line 2…N: Data Lines
			text.append(value);

		}

		return text.toString();
	}

	/**
	 * Decode the text representation of a properties object.
	 * 
	 * @param text
	 *            A text representation as it would be created by
	 * @code{encodeProperties
	 * @return the respective @code{Properties} instance
	 * @throws SerializationException
	 *             if there is a parse error
	 * @throws NullPointerException
	 *             if the text parameter is null
	 */
	public static Properties decodeProperties(final String text)
			throws SerializationException {
		if (text == null)
			throw new NullPointerException("Text parameter may not be null!");
		// split in lines
		final String[] lines = text.split("\n");

		// now we get the parameters
		final Properties props = new Properties();
		int c = 0; // line counter
		StringBuffer buf = new StringBuffer(); // data goes here
		while (c < lines.length) {
			// first line: data lines and parameter name
			final String _meta = lines[c];

			int idx = _meta.indexOf(' ');
			if (idx < 1)
				throw new SerializationException(
						"Parameter line must be of format "
								+ "<Number of Data Lines>"
								+ "<Space><Parameter Name> in line " + (c + 1)
								+ "!");
			// get the number of parameter lines
			final int pl;
			try {
				pl = Integer.parseInt(_meta.substring(0, idx));
			} catch (NumberFormatException e) {
				throw new SerializationException(
						"<Number of Data Lines> is not a valid integer number in line "
								+ (c + 1) + ": " + e.getMessage(), e);
			}
			// check if we have enough lines left
			if (lines.length <= c + pl)
				throw new SerializationException(
						"Parameter states more data lines than are left in message, in line "
								+ (c + 1) + "!");
			// get parameter name
			if (idx == _meta.length() - 1)
				throw new SerializationException(
						"Parameter name expected in line " + (c + 1) + "!");
			final String param = _meta.substring(idx + 1);

			c++;

			// get the value
			buf.delete(0, buf.length());
			for (int i = 0; i < pl; i++) {
				if (i > 0)
					buf.append("\n");
				buf.append(lines[c + i]);
			}

			// add to props
			props.put(param, buf.toString());

			c += pl;
		}

		return props;
	}

}
