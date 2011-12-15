package de.ovgu.dke.glue.util.transport;

import java.util.concurrent.ExecutorService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.ovgu.dke.glue.api.transport.Packet;
import de.ovgu.dke.glue.api.transport.PacketHandler;
import de.ovgu.dke.glue.api.transport.PacketHandlerFactory;
import de.ovgu.dke.glue.api.transport.PacketThread;

/**
 * 
 * @author Sebastian Stober (sebastian.stober@ovgu.de)
 *
 */
// TODO: Javadoc
// TODO: implement reportimg
public class AsyncPackageHandlerFactory implements PacketHandlerFactory {
	
	protected static Log log = LogFactory.getLog(AsyncPackageHandlerFactory.class);

	final PacketHandlerFactory packetHandlerFactory;
	final ExecutorService executor;
	
	public AsyncPackageHandlerFactory(
			PacketHandlerFactory packetHandlerFactory, ExecutorService executor) {
		super();
		this.packetHandlerFactory = packetHandlerFactory;
		this.executor = executor;
	}

	public class AsyncPackageHandlerWrapper implements PacketHandler {

		final PacketHandler wrapped;
	
		public AsyncPackageHandlerWrapper(PacketHandler wrapped) {
			super();
			this.wrapped = wrapped;
		}

		@Override
		public void handle(final PacketThread packetThread, final Packet packet)
				 {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						wrapped.handle(packetThread, packet);
					} catch (Throwable e) {
						// FIXME: reporting
						log.error(e.getMessage(), e);
					}					
			}});			
		}
		
		public PacketHandler unwrap() {
			return wrapped;
		}
		
	}
	
	@Override
	public PacketHandler createPacketHandler() throws InstantiationException {
		return new AsyncPackageHandlerWrapper(
				packetHandlerFactory.createPacketHandler());
	}
	
}
