package de.ovgu.dke.glue.util.transport;

import java.net.URI;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.jcip.annotations.ThreadSafe;
import de.ovgu.dke.glue.api.endpoint.Endpoint;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.transport.Connection;
import de.ovgu.dke.glue.api.transport.PacketHandlerFactory;
import de.ovgu.dke.glue.api.transport.TransportException;
import de.ovgu.dke.glue.api.transport.TransportFactory;

/**
 * Utility class for transport implementations containing support structures for
 * endpoints.
 * 
 * <p>
 * This class is thread safe.
 * </p>
 * 
 * @author Stefan Haun (stefan.haun@ovgu.de)
 * 
 */
@ThreadSafe
public class EndpointAdapter implements Endpoint {
	public static Endpoint forValues(String id, String schema,
			PacketHandlerFactory packetHandlerFactory,
			SerializationProvider serializationProvider) {
		return new EndpointAdapter(id, schema, packetHandlerFactory,
				serializationProvider);
	}

	private final String id;
	private final String schema;
	private final PacketHandlerFactory packetHandlerFactory;
	private final SerializationProvider serializationProvider;

	private final Set<TransportFactory> transportFactories;

	private EndpointAdapter(String id, String schema,
			PacketHandlerFactory packetHandlerFactory,
			SerializationProvider serializationProvider) {
		if (id == null)
			throw new NullPointerException("ID parameter must not be null!");
		if (schema == null)
			throw new NullPointerException("Schema parameter must not be null!");
		if (packetHandlerFactory == null)
			throw new NullPointerException(
					"Packet Handler Factory parameter must not be null!");
		if (serializationProvider == null)
			throw new NullPointerException(
					"Serialization Provider parameter must not be null!");

		this.id = id;
		this.schema = schema;
		this.packetHandlerFactory = packetHandlerFactory;
		this.serializationProvider = serializationProvider;

		// TODO best solution for thread-safe set?
		this.transportFactories = Collections
				.newSetFromMap(new ConcurrentHashMap<TransportFactory, Boolean>());
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getSchema() {
		return this.schema;
	}

	@Override
	public PacketHandlerFactory getPacketHandlerFactory() {
		return this.packetHandlerFactory;
	}

	@Override
	public SerializationProvider getSerializationProvider() {
		return this.serializationProvider;
	}

	@Override
	public Connection openConnection(URI peer) throws TransportException {
		Connection con = null;

		// iterate the transport factories for the connection
		for (TransportFactory factory : transportFactories)
			if (factory.servesPeer(peer, schema)) {
				con = factory.createTransport(peer).getConnection(this);
				break;
			}

		// not found -> Exception
		if (con == null)
			throw new NoSuchElementException(String.format(
					"Registry does not contain a transport factory "
							+ "for peer %s with schema %s!",
					peer.toASCIIString(), schema));

		return con;
	}

	/**
	 * Register a transport factory to this end-point, allowing to use the
	 * provided transport as channels for the dispatcher.
	 * 
	 * @param factory
	 *            The transport factory to be added to the end-point.
	 * @throws NullPointerException
	 *             if the factory argument is <code>null</code>.
	 */
	@Override
	public void registerTransportFactory(TransportFactory factory) {
		transportFactories.add(factory);
	}

	/**
	 * Remove a transport factory from this endpoint. Only removes the factory;
	 * existing connections are left open.
	 * 
	 * @param factory
	 *            The factory to be removed from the endpoint.
	 * @throws NullPointerException
	 *             if the factory argument is <code>null</code>.
	 */
	public void removeTransportFactory(TransportFactory factory) {
		transportFactories.remove(factory);
	}
}
