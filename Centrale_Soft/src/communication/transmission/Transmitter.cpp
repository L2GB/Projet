/*
 * Transmitter.cpp
 *
 *  Created on: 4 déc. 2014
 *      Author: menard
 */

// Internal include
#include "Transmitter.h"
#include "../../tools/exceptions/FormatException.h"
#include "../../tools/exceptions/NotFoundException.h"

// Extrenal include
#include <iostream>



Transmitter::Transmitter()
{
	initializeMapping();
	m_racc.attach(this);
	m_objectManager.attach(this);
}

void Transmitter::initializeMapping()
{
	// TODO Complete the list
	m_type["GET_PROFIL_JOUR"] = GET_PROFIL_JOUR;
	m_type["SET_PROFIL_JOUR"] = SET_PROFIL_JOUR;
	m_type["RM_PROFIL_JOUR"] = RM_PROFIL_JOUR;
	m_type["GET_PROFIL_SEMAINE"] = GET_PROFIL_SEMAINE;
	m_type["SET_PROFIL_SEMAINE"] = SET_PROFIL_SEMAINE;
	m_type["RM_PROFIL_SEMAINE"] = RM_PROFIL_SEMAINE;
	m_type["GET_OBJETS"] = GET_OBJETS;
	m_type["SET_OBJET"] = SET_OBJET;
	m_type["RM_OBJET"] = RM_OBJET;
	m_type["GET_PIECES"] = GET_PIECES;
	m_type["SET_PIECE"] = SET_PIECE;
	m_type["RM_PIECE"] = RM_PIECE;
}

void Transmitter::executeOrder(const std::string _order, json_t *_data, IdClient _idClient)
{
	// TODO call Request Object to execute order
	std::string response;
	std::cout << "Going to execute the order" << std::endl;
	switch (m_type[_order])
	{
		case GET_PROFIL_JOUR:
			try
			{
				response = LocalFileManager::getDays();
				std::cout << "Jours" << std::endl;
				std::cout << response << std::endl;
				m_racc.sendData(response);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case SET_PROFIL_JOUR:
			try
			{
				LocalFileManager::setDay(_data);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			catch(FormatException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		case RM_PROFIL_JOUR:
			try
			{
				LocalFileManager::rmDay(_data);
			}
			catch(NotFoundException &e)
			{
				std::cout << e.what() << std::endl;
			}
			catch(FormatException &e)
			{
				std::cout << e.what() << std::endl;
			}
			break;
		default:
			throw FormatException("Request type not identified");
			break;
	}
}
