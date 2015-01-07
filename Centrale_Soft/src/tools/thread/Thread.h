/*
 * Thread.h
 *
 *  Created on: 16 oct. 2014
 *      Author: menard
 */

#ifndef SRC_THREAD_THREAD_H_
#define SRC_THREAD_THREAD_H_

// External include
#include <pthread.h>

class Thread
{
	public:
		Thread() { m_mutex = PTHREAD_MUTEX_INITIALIZER; }
		virtual ~Thread() {}
		virtual void run() = 0;

	protected:
		bool isRunning() { return m_isRunning; }
		void start();
		void stop();
		void mutex_lock();
		void mutex_unlock();

	private:
		pthread_t m_thread;
		pthread_mutex_t m_mutex;
		bool m_isRunning;
};

#endif /* SRC_THREAD_THREAD_H_ */
