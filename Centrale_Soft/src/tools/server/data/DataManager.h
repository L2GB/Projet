/*
 * DataManager.h
 *
 *  Created on: 14 oct. 2014
 *      Author: menard
 */

#ifndef SRC_SERVER_GLOBAL_DATAMANAGER_H_
#define SRC_SERVER_GLOBAL_DATAMANAGER_H_

// Internal include
#include "../global/IdClient.h"
#include "../observable/DataObservable.h"
#include "../../thread/Thread.h"

// External include
#include <vector>
#include <thread>

class DataManager : public DataObservable, Thread
{
	public:
		const int TIMEOUT_SELECT = 1;     // 1s
		const int SIZE_MAX_DATA = 1024;

	public:
		DataManager();
		virtual ~DataManager();

		void sendToClient(const std::string _stringToSend, IdClient _idClient);
		std::string readFromClient(IdClient _idClient);
		void addConnection(const unsigned int _fd);
		void closeConnection(IdClient _idClient);
		void closeAllConnections();

	private:
		void run();

	private:
		std::vector<IdClient> m_socketClients;
        fd_set m_rfds;                      // Param for select (reading).
        struct timeval m_tv;                // Time to wait data.
};

#endif /* SRC_SERVER_GLOBAL_DATAMANAGER_H_ */
