/*
 * IdClient.h
 *
 *  Created on: 14 oct. 2014
 *      Author: menard
 */

#ifndef SRC_SERVER_GLOBAL_IDCLIENT_H_
#define SRC_SERVER_GLOBAL_IDCLIENT_H_

class IdClient
{
	public:
		IdClient(unsigned int _fd);
		virtual ~IdClient() {}

		unsigned int getFd() { return m_fd; }

		bool estEgal(IdClient const& b) const;

		void printClient();

	private:
		unsigned int generateRandomId();

	private:
		unsigned int m_fd;
		unsigned int m_randomId;

	private:
		//IdClient(IdClient const &);
		//IdClient& operator=(IdClient const &);
};

bool operator==(IdClient const& a, IdClient const& b);

#endif /* SRC_SERVER_GLOBAL_IDCLIENT_H_ */
