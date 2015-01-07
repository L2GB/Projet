/*
 * NotFoundException.h
 *
 *  Created on: 14 oct. 2014
 *      Author: menard
 */

#ifndef SRC_EXCEPTIONS_NOTFOUNDEXCEPTION_H_
#define SRC_EXCEPTIONS_NOTFOUNDEXCEPTION_H_

// External include
#include <exception>

class NotFoundException : public std::exception
{
	public:
		NotFoundException(std::string _error) : m_message(_error) {}
		virtual ~NotFoundException() _GLIBCXX_USE_NOEXCEPT{}

		virtual const char * what(void) const throw ()
		{
			return m_message.c_str();
		}

	private:
		std::string m_message;
};

#endif /* SRC_EXCEPTIONS_NOTFOUNDEXCEPTION_H_ */
