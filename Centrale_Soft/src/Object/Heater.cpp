/*
 * Heater.cpp
 *
 *  Created on: 4 déc. 2014
 *      Author: CAILLOT Kilian
 */

#include "Heater.h"

Heater::Heater(std::string _name, Week *_planning, std::string _type, int _Tconfort, int _Teco) : Object(_name, _planning, _type), m_Tconfort(_Tconfort), m_Teco(_Teco)
{
}

Heater::~Heater()
{
}

