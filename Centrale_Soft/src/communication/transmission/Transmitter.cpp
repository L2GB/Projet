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

}

Transmitter::~Transmitter()
{

}

std::string Transmitter::executeOrder(const json_t _json)
{

}

void Transmitter::extractOrder(std::string _stringToParse)
{
	json_t *root;
	root = json_loads(_stringToParse.c_str(), 0, NULL);

	if(!json_is_object(root))
	{

	}
}

