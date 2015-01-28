/*
 * ConnectionManager.h
 *
 *  Created on: 14 oct. 2014
 *      Author: menard
 */

#ifndef SRC_SERVER_CONNECTIONMANAGER_H_
#define SRC_SERVER_CONNECTIONMANAGER_H_

// Internal include
#include "../observer/DataObserver.h"
#include "../data/DataManager.h"
#include "../../thread/Thread.h"

// External include
#include <netinet/in.h>
#include <sys/un.h>

typedef sockaddr_un UNIX;
typedef sockaddr_in TCP;

template <typename T>
class ConnectionManager : public Thread
{
	protected:
		const int SOCKET_ERR = -1;
		const int NB_MAX_CLIENTS = 10;
		const int TIMEOUT_SELECT = 2;     // 1s

	public:
		ConnectionManager();
		virtual ~ConnectionManager();

		void attachInterface(DataObserver *_dataObserver);
		void detachInterface(DataObserver *_dataObserver);
		void writeData(std::string _dataTosend, IdClient _idClient);

	private:
		void createSocket();
		void bindSocket();
		void acceptClient();
		void run();

	protected:
		virtual void initSocket() = 0;
		virtual void removeServer() = 0;

	protected:
		int m_socket;
		int m_domain;
		int m_type;
		T m_server;
		DataManager m_dataManager;

	private:
        fd_set m_rfds;                      // Param for select (reading).
        struct timeval m_tv;                // Time to wait data.
};

#endif /* SRC_SERVER_CONNECTIONMANAGER_H_ */
