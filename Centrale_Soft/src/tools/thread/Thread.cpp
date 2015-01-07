/*
 * Thread.cpp
 *
 *  Created on: 16 oct. 2014
 *      Author: menard
 */

// Internal include
#include "Thread.h"

// External include
#include <iostream>

void *routine(void *args)
{
    Thread* t = static_cast<Thread*>(args);
    t->run();
	return NULL;
}

void Thread::start()
{
	m_isRunning = true;
	pthread_create(&m_thread, NULL, routine, this);
}

void Thread::stop()
{
	m_isRunning = false;
	pthread_join(m_thread, NULL);
}

void Thread::mutex_lock()
{
	pthread_mutex_lock(&m_mutex);
}

void Thread::mutex_unlock()
{
	pthread_mutex_unlock(&m_mutex);
}
