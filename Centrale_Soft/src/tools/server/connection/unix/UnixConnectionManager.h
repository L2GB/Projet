/*
 * UnixConnectionManager.h
 *
 *  Created on: 15 oct. 2014
 *      Author: menard
 */

#ifndef SRC_SERVER_CONNECTION_UNIX_UNIXCONNECTIONMANAGER_H_
#define SRC_SERVER_CONNECTION_UNIX_UNIXCONNECTIONMANAGER_H_

// External include
#include <sys/types.h>
#include <sys/un.h>

// Internal include
#include "../ConnectionManager.h"

class UnixConnectionManager : public ConnectionManager<UNIX>
{
	public:
		UnixConnectionManager(const std::string _pathSocket) : m_pathSocket(_pathSocket) {}
		virtual ~UnixConnectionManager();

	private:
		void initSocket();
		void removeServer();

	private:
		std::string m_pathSocket;
};

#endif /* SRC_SERVER_CONNECTION_UNIX_UNIXCONNECTIONMANAGER_H_ */
