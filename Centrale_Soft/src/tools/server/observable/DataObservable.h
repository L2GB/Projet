/*
 * DataObservable.h
 *
 *  Created on: 14 oct. 2014
 *      Author: menard
 */

#ifndef SRC_SERVER_OBSERVABLE_DATAOBSERVABLE_H_
#define SRC_SERVER_OBSERVABLE_DATAOBSERVABLE_H_

// Internal include
#include "../observer/DataObserver.h"
#include "../global/IdClient.h"

// External include
#include <set>
#include <string>

class DataObservable {
	public:
		DataObservable() {}
		virtual ~DataObservable() {}

		void attach(DataObserver *_interface);
		void detach(DataObserver *_interface);
		void sendData(const std::string _dataToSend, const IdClient _idClient);

	private:
		std::set<DataObserver*> m_list;
};

#endif /* SRC_SERVER_OBSERVABLE_DATAOBSERVABLE_H_ */
