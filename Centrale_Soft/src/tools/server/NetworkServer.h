/*
 * NetworkServer.h
 *
 *  Created on: 16 oct. 2014
 *      Author: menard
 */

#ifndef SRC_SERVER_NETWORKSERVER_H_
#define SRC_SERVER_NETWORKSERVER_H_

// Internal include
#include "observer/DataObserver.h"
#include "global/IdClient.h"
#include "connection/unix/UnixConnectionManager.h"
#include "connection/tcp/TcpConnectionManager.h"

// External include
#include <memory>

template <typename T>
class NetworkServer
{
	public:
		NetworkServer(const std::string _pathSocket);
		NetworkServer(const int _port);
		virtual ~NetworkServer() {}

		void attachInterface(DataObserver *_dataObserver);
		void detachInterface(DataObserver *_dataObserver);
		void writeData(std::string _dataToSend, IdClient _idClient);

	private:
		//std::shared_ptr<ConnectionManager<T> > m_connection;
		ConnectionManager<T> *m_connection;
};

#endif /* SRC_SERVER_NETWORKSERVER_H_ */
