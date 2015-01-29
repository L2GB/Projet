/*
 * Object.cpp
 *
 *  Created on: 4 déc. 2014
 *      Author: mil
 */

#include "Object.h"

Object::Object(std::string _name, Week *_planning, std::string _type, ZWaveController *_zwaveController) : m_name(_name), m_planning(_planning), m_type(_type), m_zwaveController(_zwaveController)
{
	start();
}

Object::~Object()
{
	stop();
}

void Object::getCurrentTime()
{
    time_t t = time(0);   // get time now
    m_time = localtime( & t );
   /* std::cout << (m_time->tm_year + 1900) << '-'
         << (m_time->tm_mon + 1) << '-'
         <<  m_time->tm_mday << ", "
		 <<  m_time->tm_hour << ':'
		 <<  m_time->tm_min
         << std::endl;*/
}

//std::string Object::guessType(){
//
//}


