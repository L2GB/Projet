/*
 * Transmitter.cpp
 *
 *  Created on: 4 déc. 2014
 *      Author: menard
 */

// Internal include
#include "Transmitter.h"

Transmitter::Transmitter()
{
	initializeMapping();
	m_racc.attach(this);
	m_objectManager.attach(this);
}

void Transmitter::initializeMapping()
{
	// TODO Complete the list
	m_type["TYPE1"] = TYPE1;
	m_type["TYPE2"] = TYPE2;
}

void Transmitter::executeOrder(const std::string _order, json_t *_data, IdClient _idClient)
{
	// TODO call Request Object to execute order

	std::cout << "Going to execute the order" << std::endl;
	switch (m_type[_order])
	{
		case TYPE1:
			std::cout << "IM THE BOSS" << std::endl;
			break;
		case TYPE2:
			break;
		default:
			break;
	}
}
