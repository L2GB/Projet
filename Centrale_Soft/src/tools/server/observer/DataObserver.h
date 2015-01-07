/*
 * DataObserver.h
 *
 *  Created on: 14 oct. 2014
 *      Author: menard
 */

#ifndef SRC_SERVER_OBSERVER_DATAOBSERVER_H_
#define SRC_SERVER_OBSERVER_DATAOBSERVER_H_

// Internal include
#include "../global/IdClient.h"

// External include
#include <string>

class DataObserver
{
	public:
		DataObserver() {}
		virtual ~DataObserver() {}

	public:
		virtual void receiveOrder(const std::string _dataReceive, IdClient _idClient) = 0;

	private:
		DataObserver(DataObserver const &);
		DataObserver& operator=(DataObserver const &);
};

#endif /* SRC_SERVER_OBSERVER_DATAOBSERVER_H_ */
