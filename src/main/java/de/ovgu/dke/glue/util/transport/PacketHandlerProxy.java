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
import de.ovgu.dke.glue.api.transport.PacketHandler;
import de.ovgu.dke.glue.api.transport.PacketThread;

/**
 * 
 * @author Sebastian Stober (sebastian.stober@ovgu.de)
 *
 */
// TODO: Javadoc
public class PacketHandlerProxy implements PacketHandler {

	private PacketHandler handler;

	public PacketHandlerProxy() {
		this(ClosedPacketHandler.instance());
	}

	public PacketHandlerProxy(PacketHandler handler) {
		super();
		this.handler = handler;
	}

	public void setHandler(PacketHandler handler) {
		this.handler = handler;
	}

	public PacketHandler getHandler() {
		return handler;
	}

	@Override
	public void handle(PacketThread packetThread, Packet packet) {
		handler.handle(packetThread, packet);
	}

}
