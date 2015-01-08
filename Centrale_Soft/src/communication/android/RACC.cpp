/*
 * RACC.cpp
 *
 *  Created on: 4 déc. 2014
 *      Author: menard
 */

// Internal include
#include "RACC.h"
#include "../../tools/json/jansson.h"

// External include
#include <iostream>
#include <stdexcept>

RACC::RACC(const int _port) : m_tcpServer(_port)
{
	m_tcpServer.attachInterface(this);
}

void RACC::receiveOrder(const std::string _dataReceive, IdClient _idClient)
{
	std::cout << "Coucou from RACC" << std::endl;
	std::cout << "Data receive :" << std::endl;
	std::cout << _dataReceive << std::endl;

	json_t *root;
	json_t *request;
	json_t *type;
	json_t *data;

	std::string type_string;

	// Parsing data
	root = json_loads(_dataReceive.c_str(), 0, NULL);
	std::cout << "Data parsé :" << std::endl;
	if(json_is_object(root))
	{
		try
		{
			// the string is a json object.
			request = json_object_get(root, "request");
			type = json_object_get(request, "type");

			type_string = json_string_value(type);
			std::cout << "TYPE :" << type_string << std::endl;

			data = json_object_get(request, "data");
			m_transmission->executeOrder(type_string, data, _idClient);
		}
		catch(std::logic_error &e)
		{
			std::cout << "WTF?!" << std::endl;
		}

	}
}
