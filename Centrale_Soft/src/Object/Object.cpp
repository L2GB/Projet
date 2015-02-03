/*
 * Object.cpp
 *
 *  Created on: 4 déc. 2014
 *      Author: mil
 */

#include "Object.h"

Object::Object(ZWaveController *_zwaveController, int _deviceId, int _instanceNum, const std::string _name) :  m_zwaveController(_zwaveController), m_deviceId(_deviceId), m_instanceNum(_instanceNum), m_name(_name)
{
}

Object::Object(ZWaveController *_zwaveController, int _deviceId, int _instanceNum, const std::string _name, Week *_planning, bool _connected, bool _unknown) : m_zwaveController(_zwaveController), m_deviceId(_deviceId), m_instanceNum(_instanceNum), m_name(_name), m_planning(_planning), m_connected(_connected), m_unknown(_unknown)
{

}

Object::~Object()
{
	stop();
}

void Object::launch()
{
	start();
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

void Object::automatic_initName_setting(){

	//this->m_zwaveController->zNetwork_get_device_name(this->m_deviceId, this->m_initName);
}


