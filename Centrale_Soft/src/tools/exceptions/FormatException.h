/*
 * FormatException.h
 *
 *  Created on: 12 janv. 2015
 *      Author: menard
 */

#ifndef TOOLS_EXCEPTIONS_FORMATEXCEPTION_H_
#define TOOLS_EXCEPTIONS_FORMATEXCEPTION_H_

// External include
#include <exception>

class FormatException : public std::exception
{
	public:
	FormatException(std::string _error) : m_message(_error) {}
		virtual ~FormatException() _GLIBCXX_USE_NOEXCEPT{}

		virtual const char * what(void) const throw ()
		{
			return m_message.c_str();
		}

	private:
		std::string m_message;
};

#endif /* TOOLS_EXCEPTIONS_FORMATEXCEPTION_H_ */
