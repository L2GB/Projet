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
	// TODO idem with BDCC
}

Type Transmitter::getType(std::string _type)
{
	Type type;

	//TODO return the type

	return type;
}

void Transmitter::executeOrder(const std::string _order, json_t *_data, IdClient _idClient)
{
	Type type = getType(_order);
	// TODO call Request Object to execute order
	switch (type)
	{
		default:
			break;
	}
}
