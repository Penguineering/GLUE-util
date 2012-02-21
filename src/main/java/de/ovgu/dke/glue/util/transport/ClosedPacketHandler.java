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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.ovgu.dke.glue.api.transport.Packet;
import de.ovgu.dke.glue.api.transport.PacketHandler;
import de.ovgu.dke.glue.api.transport.PacketThread;

/**
 * design-pattern: null-object (behavior) singleton, stateless
 * 
 * Just swallows all the packets (does nothing). Can be used as place holder
 * (for a connection to be established) or to replace the reference to a just
 * closed real handler.
 * 
 * @author Sebastian Stober (sebastian.stober@ovgu.de)
 * 
 */
public class ClosedPacketHandler implements PacketHandler {

	protected static Log log = LogFactory.getLog(ClosedPacketHandler.class);

	private final static ClosedPacketHandler instance = new ClosedPacketHandler();

	public static ClosedPacketHandler instance() {
		return instance;
	}

	private ClosedPacketHandler() {
		// nah!
	}

	@Override
	public void handle(PacketThread packetThread, Packet packet) {
		// ignored
		log.debug("PacketThread closed. Message not sent.");
	}

}
