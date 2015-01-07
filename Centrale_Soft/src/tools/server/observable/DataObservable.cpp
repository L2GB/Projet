/*
 * DataObservable.cpp
 *
 *  Created on: 14 oct. 2014
 *      Author: menard
 */

// Internal include
#include "DataObservable.h"


void DataObservable::attach(DataObserver *_interface)
{
	m_list.insert(_interface);
}

void DataObservable::detach(DataObserver *_interface)
{
	m_list.erase(_interface);
}
void DataObservable::sendData(const std::string _dataToSend, const IdClient _idClient)
{
	for (std::set<DataObserver*>::const_iterator it = m_list.begin() ; it != m_list.end() ; ++it)
	{
		(*it)->receiveOrder(_dataToSend, _idClient);
	}
}

