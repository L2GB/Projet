/*
 * Object.cpp
 *
 *  Created on: 4 déc. 2014
 *      Author: mil
 */

#include "Object.h"

Object::Object(std::string _name, Week *_planning, std::string _type) : m_name(_name), m_planning(_planning), m_type(_type)
{
}

Object::~Object()
{
}
