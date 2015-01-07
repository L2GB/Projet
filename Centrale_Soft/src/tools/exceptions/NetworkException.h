/*
 * NetworkException.h
 *
 *  Created on: 15 oct. 2014
 *      Author: menard
 */

#ifndef SRC_EXCEPTIONS_NETWORKEXCEPTION_H_
#define SRC_EXCEPTIONS_NETWORKEXCEPTION_H_

// External include
#include <exception>

class NetworkException : public std::exception
{
	public:
		NetworkException(std::string _error) : m_message(_error) {}
		virtual ~NetworkException() _GLIBCXX_USE_NOEXCEPT{}

		virtual const char * what(void) const throw ()
		{
			return m_message.c_str();
		}

	private:
		std::string m_message;
};

#endif /* SRC_EXCEPTIONS_NETWORKEXCEPTION_H_ */
