/*
 * IdClient.cpp
 *
 *  Created on: 14 oct. 2014
 *      Author: menard
 */

// Internal include
#include "IdClient.h"

// External include
#include <ctime>
#include <cstdlib>
#include <iostream>

IdClient::IdClient(unsigned int _fd) : m_fd(_fd)
{
	m_randomId = generateRandomId();
}

unsigned int IdClient::generateRandomId()
{
  /* initialize random seed: */
  srand (time(NULL));

  return rand() % 10000;
}

void IdClient::printClient()
{
	std::cout << "Client n°" << m_randomId << " on the fd : " << m_fd << std::endl;
}

bool operator==(IdClient const& a, IdClient const& b)
{
    return a.estEgal(b);
}

bool IdClient::estEgal(IdClient const& b) const
{
    if (m_fd == b.m_fd && m_randomId == b.m_randomId)
        return true;
    else
        return false;
}
