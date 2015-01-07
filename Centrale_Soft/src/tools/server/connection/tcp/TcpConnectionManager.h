/*
 * TcpConnectionManager.h
 *
 *  Created on: 21 oct. 2014
 *      Author: menard
 */

#ifndef SERVER_CONNECTION_TCP_TCPCONNECTIONMANAGER_H_
#define SERVER_CONNECTION_TCP_TCPCONNECTIONMANAGER_H_

// External include
#include <sys/types.h>

// Internal include
#include "../ConnectionManager.h"

class TcpConnectionManager : public ConnectionManager<TCP>
{
	public:
		TcpConnectionManager(const int _port) : m_port(_port) {}
		virtual ~TcpConnectionManager();

	private:
		void initSocket();
		void removeServer();

	private:
		int m_port;
};

#endif /* SERVER_CONNECTION_TCP_TCPCONNECTIONMANAGER_H_ */
