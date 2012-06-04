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

import de.ovgu.dke.glue.api.transport.PacketHandler;
import de.ovgu.dke.glue.api.transport.PacketHandlerFactory;

/**
 * Packet handler factory for a single-instance packet handler
 * 
 * <p>
 * Please note that this packet handler must be thread safe!
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 * 
 */
public class SingletonPacketHandlerFactory implements PacketHandlerFactory {
	/**
	 * <p>
	 * Get an instance of a singleton packet handler factory with the provided
	 * packet handler instance.
	 * </p>
	 * <p>
	 * Please note that this packet handler must be thread safe!
	 * </p>
	 * 
	 * @param handler
	 *            Packet handler instance for this factory
	 * @return Instance of the factory which will return the handler instance.
	 */
	public static SingletonPacketHandlerFactory valueOf(
			final PacketHandler handler) {
		return new SingletonPacketHandlerFactory(handler);
	}

	private final PacketHandler handler;

	/**
	 * Use <code>valueOf</code> to get an instance!
	 */
	private SingletonPacketHandlerFactory(final PacketHandler handler) {
		this.handler = handler;
	}

	@Override
	public PacketHandler createPacketHandler() throws InstantiationException {
		return handler;
	}
}
