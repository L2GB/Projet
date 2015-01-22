/*
 * TimeSlot.cpp
 *
 *  Created on: 19 janv. 2015
 *      Author: menard
 */

// Internal include
#include "TimeSlot.h"

TimeSlot::TimeSlot(bool _permission, struct tm _start, struct tm _end) : m_permission(_permission), m_start(_start), m_end(_end)
{

}

TimeSlot::~TimeSlot()
{
}

