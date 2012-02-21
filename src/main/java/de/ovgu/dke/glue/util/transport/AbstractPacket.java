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
package de.ovgu.dke.glue.util.transport;

import de.ovgu.dke.glue.api.transport.Packet;

/**
 * Abstract implementation of the Packet interface containing pay-load and
 * priority. Can be used as base for any packet implementation.
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 * 
 */
public abstract class AbstractPacket implements Packet {
	private final Object payload;
	private final Packet.Priority priority;

	public AbstractPacket(final Object payload, final Packet.Priority priority) {
		this.payload = payload;
		this.priority = priority;
	}

	@Override
	public Object getPayload() {
		return payload;
	}

	@Override
	public Priority getPriority() {
		return priority;
	}

	@Override
	public Object getAttribute(String key) {
		return null;
	}

}
